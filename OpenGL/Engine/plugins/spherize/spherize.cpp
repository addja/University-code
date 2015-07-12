#include "spherize.h"

void Spherize::onPluginLoad()
{
    
    // Carregar shader, compile & link 
    QString vs_src = "void main(){gl_Position = gl_ModelViewProjectionMatrix * vec4(normalize(gl_Vertex.xyz),1);gl_FrontColor = gl_Color * (gl_NormalMatrix * gl_Normal)[2];}";
    vs = new QGLShader(QGLShader::Vertex, this);
    vs->compileSourceCode(vs_src);
    program = new QGLShaderProgram(this);
    program->addShader(vs);
    program->link();
}

void Spherize::preFrame() 
{
    // bind shader and define uniforms
    program->bind();
}

void Spherize::postFrame() 
{
    // unbind shader
    program->release();
}

Q_EXPORT_PLUGIN2(spherize, Spherize)   // plugin name, plugin class

