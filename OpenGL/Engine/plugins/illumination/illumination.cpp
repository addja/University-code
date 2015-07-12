#include "illumination.h"

void Illumination::onPluginLoad()
{
    program = new QGLShaderProgram(this);
    
    // Carregar shader, compile & link 
    QString vs_src = "varying vec3 v;varying vec3 N;void main(){gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;gl_FrontColor  = gl_Color;gl_TexCoord[0] = gl_MultiTexCoord0;v = vec3(gl_ModelViewMatrix * gl_Vertex);N = gl_NormalMatrix * gl_Normal;}";
    vs = new QGLShader(QGLShader::Vertex, this);
    vs->compileSourceCode(vs_src);
    program->addShader(vs);
    
    // Not compiles the fragment
    QString fs_src = "varying vec3 v, N;void main(){vec4 Ma = gl_LightModel.ambient;vec4 Ia = gl_LightSource[0].ambient;vec4 Id = gl_LightSource[0].diffuse;vec4 Is = gl_LightSource[0].specular;vec4 K = gl_FrontMaterial.emission;vec4 Ka = gl_FrontMaterial.ambient;vec4 Kd = gl_FrontMaterial.diffuse;vec4 Ks = gl_FrontMaterial.specular;float s = gl_FrontMaterial.shininess;vec3 L = normalize(gl_LightSource[0].position.xyz - v);vec3 H = normalize(gl_LightSource[0].halfVector.xyz);float var = Ks*Is*pow(max(0.0,dot(N,H)),s);if (var > 0.0) gl_FragColor = K+Ka*(Ma+Ia)+Kd*Id*max(0.0,dot(N,L))+var;else gl_FragColor = K+Ka*(Ma+Ia)+Kd*Id*max(0.0,dot(N,L));}";
    fs = new QGLShader(QGLShader::Fragment, this);
    fs->compileSourceCode(fs_src);
    program->addShader(fs);

    program->link();
}

void Illumination::preFrame() 
{
    // bind shader and define uniforms
    program->bind();
}

void Illumination::postFrame() 
{
    // unbind shader
    program->release();
}

Q_EXPORT_PLUGIN2(illumination, Illumination)   // plugin name, plugin class

