#include "effectcrt.h"

void EffectCRT::onPluginLoad()
{
    // Carregar shader, compile & link 
    QString fs_src = "uniform int n; void main() {if (mod((gl_FragCoord.y-0.5), float(n)) > 0.0) discard; gl_FragColor=gl_Color;}";
    fs = new QGLShader(QGLShader::Fragment, this);
    fs->compileSourceCode(fs_src);
    program = new QGLShaderProgram(this);
    program->addShader(fs);
    program->link();
}

void EffectCRT::preFrame() 
{
    // bind shader and define uniforms
    program->bind();
    program->setUniformValue("n", 6);
}

void EffectCRT::postFrame() 
{
    // unbind shader
    program->release();
}

Q_EXPORT_PLUGIN2(effectcrt, EffectCRT)   // plugin name, plugin class
