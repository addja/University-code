#include "animatedVertices.h"

void AnimatedVertices::onPluginLoad()
{
    timer = new QTimer();
    timer->start();
    elapsedTimer = new QElapsedTimer();

    // Carregar shader, compile & link 
    QString vs_src = "uniform float time; \
                      uniform float freq; \
                      uniform float amp; \
                      void main(){vec4 v = gl_Vertex + vec4(gl_Normal,0)*amp * sin(freq*time);gl_Position    = gl_ModelViewProjectionMatrix * v;gl_FrontColor  = vec4((gl_NormalMatrix*gl_Normal).z);}";
    vs = new QGLShader(QGLShader::Vertex, this);
    vs->compileSourceCode(vs_src);
    program = new QGLShaderProgram(this);
    program->addShader(vs);
    program->link();
    glwidget()->connect(timer, SIGNAL(timeout()), glwidget(), SLOT(updateGL()));

}

void AnimatedVertices::preFrame() 
{
    // bind shader and define uniforms
    program->bind();

    // set have time updated
    program->setUniformValue("freq", 1.f);
    program->setUniformValue("amp", 0.1f);
    program->setUniformValue("time", float(elapsedTimer->nsecsElapsed()/1.e9));

}

void AnimatedVertices::postFrame() 
{
    // unbind shader
    program->release();

}

Q_EXPORT_PLUGIN2(animatedVertices, AnimatedVertices)   // plugin name, plugin class
