#include "colorselect.h"
#include "glwidget.h"

void ColorSelect::postFrame() {
    int i =  scene()->selectedObject();
    cout << i << endl; // to debug
    if (i != -1) {
    	Box box = scene()->objects()[i].boundingBox();
    	box.render();
    }
}

void ColorSelect::drawSceneMod() {
    Scene* scn = scene();
    // per cada objecte
    glClearColor(0,0,255,0);
    glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
    glDisable(GL_LIGHTING);
    for (unsigned int i=0; i<scn->objects().size(); ++i)    
    {
        glColor3ub(i,0,0);
        const Object& obj = scn->objects()[i];
        // per cada cara
        for(unsigned int c=0; c<obj.faces().size(); c++)
        {
            const Face& face = obj.faces()[c];
            glBegin (GL_POLYGON);
            glNormal3f(face.normal().x(), face.normal().y(), face.normal().z());
            // per cada vertex
            for(int v=0; v<face.numVertices(); v++)
            { 
                const Point& p = obj.vertices()[face.vertexIndex(v)].coord();
                glVertex3f(p.x(), p.y(), p.z());
            }
            glEnd();
        }
    }
    glEnable(GL_LIGHTING);
}

int ColorSelect::objectFromColorSelect(float x, float y) {
	GLint viewport[4]; 
	glGetIntegerv(GL_VIEWPORT, viewport); 
	GLubyte colors[3]; // RGB x 1 pixel
    GLubyte back[3] = {0,0,255};
    glReadBuffer(GL_BACK);
	glReadPixels(x, viewport[3]-y, 1, 1, GL_RGB, GL_UNSIGNED_BYTE, colors);
	if (colors[2] == back[2]) return -1;
	return colors[0];
}

void ColorSelect::mouseReleaseEvent(QMouseEvent *e) {
    drawSceneMod();
    int i = objectFromColorSelect(e->x(), e->y());
    scene()->setSelectedObject(i);
	glwidget()->updateGL();
}

Q_EXPORT_PLUGIN2(colorselect, ColorSelect)   // plugin name, plugin class