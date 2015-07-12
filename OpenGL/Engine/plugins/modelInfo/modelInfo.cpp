#include "modelInfo.h"

void ModelInfo::postFrame() {
	int vertices, nPoligons, nTriangles;
	vertices = nPoligons = nTriangles = 0;

	Scene* scn = scene();
    // per cada objecte
    for (unsigned int i=0; i<scn->objects().size(); ++i)    
    {
        const Object& obj = scn->objects()[i];
        // per cada cara
        for(unsigned int c=0; c<obj.faces().size(); c++)
        {
        	++nPoligons;
            const Face& face = obj.faces()[c];

            // per cada vertex
            if (face.numVertices() == 3) ++nTriangles;
            vertices += face.numVertices();
        }
    }

	float per = 0;
	if (nTriangles > 0) {
		per = nPoligons/nTriangles;
	}

	QString qs = "Number of vertices: " + QString::number(vertices) + " - " +
		"Number of poligons: " + QString::number(nPoligons) + " - " +
		"Number of objects: " + QString::number(scn->objects().size()) + " - " +
		"percentage of triangles: " + QString::number(per); 
    glColor3f(0.0, 0.0, 0.0);
	int y = glwidget()->height() - 5;
	int x = max(0,glwidget()->width() - 600);
	glwidget()->renderText(x,y, qs);
}

Q_EXPORT_PLUGIN2(modelInfo, ModelInfo)   // plugin name, plugin class