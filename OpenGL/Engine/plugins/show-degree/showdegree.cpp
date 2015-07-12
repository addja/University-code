#include "showdegree.h"

void Showdegree::onPluginLoad() {

    res = 0;

    // only one object on the scene (statement)
    const Object& obj = scene()->objects()[0];
    vector<int> v = vector<int>(obj.vertices().size(),0.0);

    // per cada cara
    for (unsigned int c=0; c<obj.faces().size(); c++)
    {
        const Face& face = obj.faces()[c];

        // per cada vertex
        for (uint i = 0; i < (uint)face.numVertices(); i++) {
            ++v[face.vertexIndex(i)];
        }
    }

    for (uint i = 0; i < v.size(); i++) {
        res += v[i];
    }

    res /= v.size();


}

void Showdegree::postFrame() {


    glColor3f(0.0, 0.0, 0.0);
	int x = 10;
	int y = 15; // glutGet(GLUT_WINDOW_HEIGHT);
	glwidget()->renderText(x,y, QString::number(res));
}

Q_EXPORT_PLUGIN2(showdegree, Showdegree)   // plugin name, plugin class