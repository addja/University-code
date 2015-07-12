#include "mouseselect.h"
#include "glwidget.h"

void MouseSelect::postFrame() {
    int i =  scene()->selectedObject();
    cout << i << endl;
    if (i != -1) {
    	Box box = scene()->objects()[i].boundingBox();
    	box.render();
    }
}


Q_EXPORT_PLUGIN2(mouseselect, MouseSelect)   // plugin name, plugin class