#include "framerate.h"
#include "glwidget.h"

void Framerate::preFrame() {
    timer.start();
}

void Framerate::postFrame() {
    glColor3f(0.0, 0.0, 0.0);
	int x = 10;
	int y = 15;
	glwidget()->renderText(x,y, QString::number(1.e9/timer.nsecsElapsed()));
}

Q_EXPORT_PLUGIN2(framerate, Framerate)   // plugin name, plugin class