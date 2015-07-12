#include "renderdefault.h"
#include "glwidget.h"

bool RenderDefault::paintGL()
{
    glClearColor(0.8f, 0.8f, 0.8f, 0.0f);
    glClear( GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT );

    glwidget()->drawAxes();

    glColor4f(0.8f, 0.8f, 0.8f, 0.1f); // default color

    if (drawPlugin()) 
        drawPlugin()->drawScene();

    return true;
}

Q_EXPORT_PLUGIN2(renderdefault, RenderDefault)   // plugin name, plugin class
