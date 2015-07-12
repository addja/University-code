#include "keyselect.h"
#include "glwidget.h"

void KeySelect::postFrame() {
    int i =  scene()->selectedObject();
    cout << i << endl; // to debug
    if (i != -1) {
    	Box box = scene()->objects()[i].boundingBox();
    	box.render();
    }
}


void KeySelect::keyPressEvent(QKeyEvent *event) {
    int i =  event->key();
    if (i == 58) i = 0;
    else i = i-48;
    if (i < 0 && i > 9) i = -1;
	scene()->setSelectedObject(i);
	glwidget()->updateGL();
}


Q_EXPORT_PLUGIN2(keyselect, KeySelect)   // plugin name, plugin class