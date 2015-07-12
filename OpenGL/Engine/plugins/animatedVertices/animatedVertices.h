#ifndef _ANIMATEDVERTICES_H
#define _ANIMATEDVERTICES_H

#include "basicplugin.h"
#include "glwidget.h"
#include <QGLShader>
#include <QGLShaderProgram>
#include <QElapsedTimer>

class AnimatedVertices : public QObject, public BasicPlugin
 {
     Q_OBJECT
     Q_INTERFACES(BasicPlugin)

 public:
    void onPluginLoad();
    void preFrame();
    void postFrame();
    
 private:
    QGLShaderProgram* program;
    QGLShader* vs;
    QTimer* timer;
    QElapsedTimer* elapsedTimer;
 };
 
 #endif
