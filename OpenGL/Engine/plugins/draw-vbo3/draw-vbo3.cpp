#include "draw-vbo3.h"

#define PI 3.14159265358979323846

DrawVBO3::~DrawVBO3()
{
    glDeleteBuffers(coordBuffers.size(),  &coordBuffers[0]);
    glDeleteBuffers(normalBuffers.size(), &normalBuffers[0]);
    glDeleteBuffers(indexBuffers.size(),  &indexBuffers[0]);
}

float DrawVBO3::length(Vector v) {
    return sqrt(v.x()*v.x() + v.y()*v.y() + v.z()*v.z());
}

float DrawVBO3::dot(Vector a, Vector b) {
    return a.x()*b.x() + a.y()*b.y() + a.z()*b.z();
}

float DrawVBO3::angleBetween(Vector a, Vector b) {
    return acos(dot(a,b) / (length(a) * length(b)));
}

bool DrawVBO3::drawScene()
{
    glColor3f(1.0f, 0.8f, 0.8f);
    for(unsigned int i=0; i<coordBuffers.size(); i++) // for each buffer (that is, for each object)
    {
        glBindBuffer(GL_ARRAY_BUFFER, coordBuffers[i]);
        glVertexPointer(3, GL_FLOAT, 0, (GLvoid*) 0);
        glEnableClientState(GL_VERTEX_ARRAY);

        glBindBuffer(GL_ARRAY_BUFFER, normalBuffers[i]);
        glNormalPointer(GL_FLOAT, 0, (GLvoid*) 0);
        glEnableClientState(GL_NORMAL_ARRAY);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffers[i]);

        glDrawElements(GL_TRIANGLES, numIndices[i], GL_UNSIGNED_INT, (GLvoid*) 0);
    }
    
    glDisableClientState(GL_VERTEX_ARRAY);
    glDisableClientState(GL_NORMAL_ARRAY);
    
    glBindBuffer(GL_ARRAY_BUFFER,0);
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);

    return true;
}

void DrawVBO3::onPluginLoad()
{
    nObj = scene()->objects().size();
    for(unsigned int i=0; i < nObj; i++)
        addVBO(i);

    // save first object info
    const Object& fst_obj = scene()->objects()[0];
    nVerts = fst_obj.vertices().size();
    nFaces = fst_obj.faces().size();
}

void DrawVBO3::onObjectAdd()
{
    addVBO( scene()->objects().size() - 1 );
    ++nObj;
}

void DrawVBO3::addVBO(unsigned int currentObject)
{  
    vector<float> vertices; // (x,y,z)    
    vector<float> normals;  // (nx,ny,nz) 
    vector<int> indices;    //            
        
    const float THRESHOLD = PI/4; // 45º in rad
    const Object& obj = scene()->objects()[currentObject];

    // push vertices
    for (unsigned int i = 0; i < obj.vertices().size(); ++i) {
        Point P = obj.vertices()[i].coord();
        vertices.push_back(P.x()); vertices.push_back(P.y()); vertices.push_back(P.z());
    }
    
    unsigned int indexCount = obj.vertices().size();
    normals.resize(obj.vertices().size() * 3, 0.0);
    
    for(unsigned int j=0; j<obj.faces().size(); j++)  // for each face
    {
        const Face& face = obj.faces()[j];
        Vector N = face.normal();
        vector<int> faceID2VertID (face.numVertices());
        
        // avg normals that need it 
        for(unsigned int k=0; k < (unsigned int)face.numVertices(); k++) {
            unsigned int ind = face.vertexIndex(k);
            if (normals[ind*3] == 0.0 and //first time being treated
                normals[ind*3+1] == 0.0 and
                normals[ind*3+2] == 0.0) {

                normals[ind*3] = N.x(); 
                normals[ind*3+1] = N.y(); 
                normals[ind*3+2] = N.z();
            
                faceID2VertID[k] = ind;
            }
            else {
                float angle = angleBetween(N,Vector(normals[ind*3],normals[ind*3+1],normals[ind*3+2]));
                if (angle <= THRESHOLD) {
                    normals[ind*3] += N.x();
                    normals[ind*3 + 1] += N.y();
                    normals[ind*3 + 2] += N.z();
                    faceID2VertID[k] = ind;
                }
                else { //per-corner vertex is pushed
                    Point P = obj.vertices()[ind].coord();
                    vertices.push_back(P.x()); vertices.push_back(P.y()); vertices.push_back(P.z());
                    normals.push_back(N.x()); normals.push_back(N.y()); normals.push_back(N.z());
                    faceID2VertID[k] = indexCount++;
                }
            }
        }
        
        // triangulation on the fly and index pushing
        for (unsigned int k=1; k<(unsigned int)face.numVertices()-1; k++){
            indices.push_back(faceID2VertID[0]);
            indices.push_back(faceID2VertID[k]);
            indices.push_back(faceID2VertID[k+1]);
        }
    }
        
    // normalize all the normals
    for (unsigned int j=0; j < normals.size(); j += 3) {
        Vector V = Vector(normals[j],normals[j+1],normals[j+2]);
        V.normalize();
        normals[j] = V.x(); normals[j+1] = V.y(); normals[j+2] = V.z();
    }
    
    // Step 2: Create empty buffers (coords, normals, indices)
    GLuint coordBufferID;
    glGenBuffers(1, &coordBufferID);
    coordBuffers.push_back(coordBufferID);

    GLuint normalBufferID;
    glGenBuffers(1, &normalBufferID);
    normalBuffers.push_back(normalBufferID);

    GLuint indexBufferID;
    glGenBuffers(1, &indexBufferID);
    indexBuffers.push_back(indexBufferID);

    numIndices.push_back(indices.size());

    // save size of Buffers 
    if (currentObject == 0) {
        VBsize = vertices.size();
        IBsize = indices.size();
    }

    // Step 3: Define VBO data (coords, normals, indices)
    glBindBuffer(GL_ARRAY_BUFFER, coordBuffers[currentObject]);
    glBufferData(GL_ARRAY_BUFFER, sizeof(float)*vertices.size(), &vertices[0], GL_STATIC_DRAW);

    glBindBuffer(GL_ARRAY_BUFFER, normalBuffers[currentObject]);
    glBufferData(GL_ARRAY_BUFFER, sizeof(float)*normals.size(), &normals[0], GL_STATIC_DRAW);

    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffers[currentObject]);
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(int)*indices.size(), &indices[0], GL_STATIC_DRAW);
}

void DrawVBO3::postFrame() {
    glColor3f(0.0, 0.0, 0.0);
    char buff [200];
    QString dis = "";
    sprintf(buff,"Num Obj: %d   ", nObj);
    dis.append(buff);
    sprintf(buff,"Num Polygons: %d  ", nFaces);
    dis.append(buff);
    sprintf(buff,"Num Vert: %d  ", nVerts);
    dis.append(buff);
    sprintf(buff,"Size of Vertex Buffer: %d   ", VBsize);
    dis.append(buff);
    sprintf(buff,"Size of Index Buffer: %d   ", IBsize);
    dis.append(buff);
    glwidget()->renderText(5,15,dis);
}

Q_EXPORT_PLUGIN2(draw-vbo3, DrawVBO3)   // plugin name, plugin class
