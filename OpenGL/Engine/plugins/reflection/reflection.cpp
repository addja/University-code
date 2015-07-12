#include "reflection.h"

const int IMAGE_WIDTH = 1024;
const int IMAGE_HEIGHT = IMAGE_WIDTH;

#define printOpenGLError() printOglError(__FILE__, __LINE__)
#define CHECK() printOglError(__FILE__, __LINE__,__FUNCTION__)
#define DEBUG() cout << __FILE__ << " " << __LINE__ << " " << __FUNCTION__ << endl;

int printOglError(const char file[], int line, const char func[]) 
{
    GLenum glErr;
    int    retCode = 0;

    glErr = glGetError();
    if (glErr != GL_NO_ERROR)
    {
        printf("glError in file %s @ line %d: %s function: %s\n",
                 file, line, gluErrorString(glErr), func);
        retCode = 1;
    }
    return retCode;
}

void Reflection::onPluginLoad()
{
    // Resize to power-of-two viewport
    glwidget()->resize(IMAGE_WIDTH,IMAGE_HEIGHT);

    // Carregar shader & compile
    vs = new QGLShader(QGLShader::Vertex, this);
    vs->compileSourceFile("../../plugins/reflection/reflection.vert");

    fs = new QGLShader(QGLShader::Fragment, this);
    fs->compileSourceFile("../../plugins/reflection//reflection.frag");

    program = new QGLShaderProgram(this);
    program->addShader(vs);
    program->addShader(fs);
    program->link();

    // Setup texture
    glActiveTexture(GL_TEXTURE0);
    glGenTextures( 1, &textureId);
    glBindTexture(GL_TEXTURE_2D, textureId);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR );
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR );
    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, IMAGE_WIDTH, IMAGE_HEIGHT, 0, GL_RGB, GL_FLOAT, NULL);
    glBindTexture(GL_TEXTURE_2D, 0);
}


bool Reflection::paintGL()
{

    float op = scene()->boundingBox().radius()*cos(3.1415/4);
    float radius = scene()->boundingBox().radius();
    radius *= 0.5;
    
    // Pass 1. Draw scene rotated over bounding box axis
    glClearColor(0.2,0.2,0.2,0);
    glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
    camera()->setModelview();
    glTranslatef(0,-radius,0);
    glScalef(1,-1,1);
    camera()->setProjection();
    glEnable(GL_LIGHTING);
    glColor3f(0.6, 0.6, 0.6);
    drawPlugin()->drawScene();

    // Generate texture
    glBindTexture(GL_TEXTURE_2D, textureId);
    glCopyTexSubImage2D(GL_TEXTURE_2D, 0, 0, 0, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
    glGenerateMipmap(GL_TEXTURE_2D);

    // Pass 2. Draw normal scene
    glClearColor(1,1,1,1);
    glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
    camera()->setModelview();
    camera()->setProjection();
    glColor3f(0.5, 0.5, 0.5);
    drawPlugin()->drawScene();

    // Pass 3. Draw reflected texture on a square and position it
    program->bind();
    program->setUniformValue("colorMap", 0);
    program->setUniformValue("SIZE", float(IMAGE_WIDTH));
    glMatrixMode(GL_MODELVIEW);
    glTranslatef(0,-radius,0);
    glRotatef(-90.0f,1.0f,0,0);
    //the projection is set to camera->setProjection()
    glRectf(-2.0,-2.0, 2.0, 2.0);

    program->release();
    glBindTexture(GL_TEXTURE_2D, 0);
  
    

    return true;
}

Q_EXPORT_PLUGIN2(reflection, Reflection)   // plugin name, plugin class
