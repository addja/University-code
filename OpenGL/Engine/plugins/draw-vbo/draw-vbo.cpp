#include "draw-vbo.h"
#include "glwidget.h"

DrawVBO::~DrawVBO()
{
	glDeleteBuffers(coordBuffers.size(),  &coordBuffers[0]);
	glDeleteBuffers(normalBuffers.size(), &normalBuffers[0]);
    glDeleteBuffers(indexBuffers.size(),  &indexBuffers[0]);
}


bool DrawVBO::drawScene()
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

void DrawVBO::onPluginLoad()
{
    for(unsigned int i=0; i<scene()->objects().size(); i++)
        addVBO(i);
}

void DrawVBO::onObjectAdd()
{
    addVBO( scene()->objects().size() - 1 );
}

void DrawVBO::addVBO(unsigned int currentObject)
{  
    // Step 1: Create and fill the three arrays (vertex coords, vertex normals, and face indices) 
    // This version: 
    //  - each coord/normal will appear n times (one for each triangle using it)
    //  - indices will follow a trivial sequence: 0,1,2, 3,4,5, ...
    //  - non-triangular faces (convexity is assumed) are triangulated on-the-fly: (v0,v1,v2,v3) -> (v0,v1,v2) (v0,v2,v3)
    vector<float> vertices; // (x,y,z)    Final size: 3*3*number of triangles
    vector<float> normals;  // (nx,ny,nz) Final size: 3*3*number of triangles
	vector<int> indices;    //            Final size: 3*number of triangles  
    unsigned int indexCount = 0; 
	
    const Object& obj = scene()->objects()[currentObject];
	for(unsigned int j=0; j<obj.faces().size(); j++)  // for each face
	{
        const Face& face = obj.faces()[j];
	    Vector N = face.normal();
		for(unsigned int k=1; k<(unsigned int)face.numVertices()-1; k++) // for each triangle
		{
			Point P;
            // first vertex 
            P = obj.vertices()[face.vertexIndex(0)].coord();
			vertices.push_back(P.x()); vertices.push_back(P.y()); vertices.push_back(P.z());
			normals.push_back(N.x()); normals.push_back(N.y()); normals.push_back(N.z());
            indices.push_back(indexCount++);

            // second vertex 
			P = obj.vertices()[face.vertexIndex(k)].coord();
			vertices.push_back(P.x()); vertices.push_back(P.y()); vertices.push_back(P.z());
			normals.push_back(N.x()); normals.push_back(N.y()); normals.push_back(N.z());
            indices.push_back(indexCount++);

            // third vertex 
			P = obj.vertices()[face.vertexIndex(k+1)].coord();
			vertices.push_back(P.x()); vertices.push_back(P.y()); vertices.push_back(P.z());
			normals.push_back(N.x()); normals.push_back(N.y()); normals.push_back(N.z());
		    indices.push_back(indexCount++);
		}
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

    // Step 3: Define VBO data (coords, normals, indices)
	glBindBuffer(GL_ARRAY_BUFFER, coordBuffers[currentObject]);
	glBufferData(GL_ARRAY_BUFFER, sizeof(float)*vertices.size(), &vertices[0], GL_STATIC_DRAW);

	glBindBuffer(GL_ARRAY_BUFFER, normalBuffers[currentObject]);
	glBufferData(GL_ARRAY_BUFFER, sizeof(float)*normals.size(), &normals[0], GL_STATIC_DRAW);

	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffers[currentObject]);
	glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(int)*indices.size(), &indices[0], GL_STATIC_DRAW);
}

Q_EXPORT_PLUGIN2(draw-vbo, DrawVBO)   // plugin name, plugin class

