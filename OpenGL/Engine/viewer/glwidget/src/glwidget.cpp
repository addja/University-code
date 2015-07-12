#define _USE_MATH_DEFINES 1
#include <cmath>
#include "glwidget.h"
#include <QMatrix4x4>
#include <GL/glu.h>
#include <QPluginLoader>
#include <QCoreApplication>
#include "basicplugin.h"

const float DRAW_AXES_LENGTH = 2.0;

GLWidget::GLWidget(QWidget * parent): QGLWidget(parent), drawPlugin(0)
{
    setFocusPolicy(Qt::ClickFocus);  // per rebre events de teclat
}

void GLWidget::setPluginPath(const QString & p) {
  pluginPath = p;
}


void GLWidget::initializeGL()
{
    glewInit(); // necessari per cridar funcions de les darreres versions d'OpenGL
    glClearColor(0.8f, 0.8f, 1.0f, 0.0f);
    glEnable(GL_DEPTH_TEST);
    glEnable(GL_LIGHTING); 
    glEnable(GL_LIGHT0);
    glColorMaterial(GL_FRONT_AND_BACK, GL_AMBIENT_AND_DIFFUSE);
    glEnable(GL_COLOR_MATERIAL);
}

void GLWidget::loadDefaultPlugins() {
    QStringList defaultPlugins;
#if defined(__APPLE__)
    defaultPlugins << pluginPath+"libdrawimmediate.dylib";
    defaultPlugins << pluginPath+"librenderdefault.dylib";
    defaultPlugins << pluginPath+"libnavigatedefault.dylib";
#elif defined(__unix__)
    defaultPlugins << pluginPath+"libdrawimmediate.so";
    defaultPlugins << pluginPath+"librenderdefault.so";
    defaultPlugins << pluginPath+"libnavigatedefault.so";
#else  // Windows?
    defaultPlugins << pluginPath+"libdrawimmediate.dll";
    defaultPlugins << pluginPath+"librenderdefault.dll";
    defaultPlugins << pluginPath+"libnavigatedefault.dll";
#endif
    loadPlugins(defaultPlugins);
}

void GLWidget::resetPluginsToDefaults() {
    for (unsigned int i=0; i<plugins.size(); ++i)
        delete plugins[i];
    plugins.clear();
    loadDefaultPlugins();
}

Scene* GLWidget::scene()
{
    return &pscene;
}

Camera* GLWidget::camera()
{
    return &pcamera;
}


void GLWidget::drawAxes() const
{
    float L = DRAW_AXES_LENGTH;
    glDisable(GL_LIGHTING);
    glBegin(GL_LINES);
    glColor3f(1,0,0); glVertex3f(0,0,0); glVertex3f(L,0,0); // X
    glColor3f(0,1,0); glVertex3f(0,0,0); glVertex3f(0,L,0); // Y
    glColor3f(0,0,1); glVertex3f(0,0,0); glVertex3f(0,0,L); // Z
    glEnd();
    glEnable(GL_LIGHTING);

}

Box GLWidget::boundingBoxIncludingAxes() const
{
    float L = DRAW_AXES_LENGTH;
    Box box(Point(0,0,0), Point(L,L,L)); // AABB dels eixos
    if (pscene.objects().size())
        box.expand(pscene.boundingBox());
    return box;
}

void GLWidget::resetCamera()
{
    pcamera.init(boundingBoxIncludingAxes());
    updateGL();
}

void GLWidget::paintGL( void )
{ 
    for (unsigned int i=0; i<plugins.size(); ++i)
        qobject_cast<BasicPlugin*>(plugins[i]->instance())->preFrame();
    
    // call to paintGL() until one returns true
    for (int i=int(plugins.size())-1; i>=0; --i) // notice reverse order
        if (qobject_cast<BasicPlugin*>(plugins[i]->instance())->paintGL()) break;

    for (unsigned int i=0; i<plugins.size(); ++i) 
        qobject_cast<BasicPlugin*>(plugins[i]->instance())->postFrame();

}

void GLWidget::resizeGL (int width, int height)
{
    glViewport (0, 0, width, height);
    pcamera.setAspectRatio( (float)width/(float)height);
}

void GLWidget::loadPlugin()
{
    QStringList list = QFileDialog::getOpenFileNames(NULL, "Select one or more plugins to open", pluginPath, "Plugins (*.dll *.so *.dylib)");
    loadPlugins(list);
}

void GLWidget::loadPlugins(const QStringList& list) {
    QStringList::ConstIterator it = list.constBegin();
    while(it != list.constEnd()) 
    {
        QString name = *it;
        QPluginLoader *loader = new QPluginLoader(name);
        if (not loader->load()) {
        	  qDebug() << "Could not load plugin " << name << "\n";
                qDebug() << loader->errorString() << "\n";

	        }
        if (loader->isLoaded()) 
        {
            qDebug() << "Loaded: " << loader->fileName() << 	endl;
            QObject *plugin = loader->instance();
            if (plugin) 
            {
                plugins.push_back(loader); 
                BasicPlugin *plugin = qobject_cast<BasicPlugin *>(loader->instance());
                // initialize plugin
                if (plugin)
                {
                    plugin->setWidget(this);
                    plugin->onPluginLoad();
                    if (plugin->drawScene()) // overrides drawScene?
                        drawPlugin = plugin;
                }
            }
        }
        else 
        {
            qDebug() << "Load error: " << name << endl;
	        delete loader;
        }
        
        ++it;
    }

    // make sure all plugins know about the latest plugin that overrides drawScene
    for (unsigned int i=0; i<plugins.size(); ++i)
    {
        BasicPlugin *plugin = qobject_cast<BasicPlugin *>(plugins[i]->instance());
        if (plugin)
            plugin->setDrawPlugin(drawPlugin);
        else
        {
            qDebug() << "Error: the plugin must implement the BasicPlugin interface" << endl <<
            " Example: " << endl << 
            "   Q_INTERFACES(BasicPlugin)" << endl;
        }
    }

    resetCamera();
    updateGL();
}

void GLWidget::help( void ){
    cout<<"Tecles definides: \n";
    cout<<"a         Afegeix plugins\n";
    cout<<"l         Afegeix un objecte\n";
    cout<<"f         Pinta en filferros\n";
    cout<<"s         Pinta amb omplert de polígons\n";
    cout<<"h,H       Imprimir aquesta ajuda\n";
}

void GLWidget::showPlugins() const
{
    qDebug() << "Current list of plugins: ";
    for (unsigned int i=0; i<plugins.size(); ++i)
        qDebug() << plugins[i]->fileName() << endl;
}

void GLWidget::keyPressEvent(QKeyEvent *e)
{
    switch( e->key() ) 
    {	    
    case Qt::Key_A: 
        loadPlugin();
        //updateGL();
        break;

    case Qt::Key_D:
        showPlugins();
	    break;

    case Qt::Key_U:
        resetPluginsToDefaults();
	    updateGL();
	break;

    case Qt::Key_L: 
        addObject();
        break;        
        
    case Qt::Key_F: 
        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        updateGL();
        break;

    case Qt::Key_S: 
        glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        updateGL();
        break;

    case Qt::Key_H:
        help();
        break;

    case Qt::Key_Escape: 
        exit(0);
        break;
        
    default: 
        e->ignore(); // el propaguem cap al pare...
    }
    
    for (unsigned int i=0; i<plugins.size(); ++i) 
        qobject_cast<BasicPlugin*>(plugins[i]->instance())->keyPressEvent(e);
    
}


void GLWidget::mousePressEvent( QMouseEvent *e)
{
    for (unsigned int i=0; i<plugins.size(); ++i) 
      qobject_cast<BasicPlugin*>(plugins[i]->instance())->mousePressEvent(e);
}

void GLWidget::mouseReleaseEvent( QMouseEvent *e)
{
    for (unsigned int i=0; i<plugins.size(); ++i) 
      qobject_cast<BasicPlugin*>(plugins[i]->instance())->mouseReleaseEvent(e);
}

void GLWidget::mouseMoveEvent(QMouseEvent *e)
{ 
    for (unsigned int i=0; i<plugins.size(); ++i) 
      qobject_cast<BasicPlugin*>(plugins[i]->instance())->mouseMoveEvent(e);
}

void	 GLWidget::keyReleaseEvent ( QKeyEvent * e)
{
    for (unsigned int i=0; i<plugins.size(); ++i) 
      qobject_cast<BasicPlugin*>(plugins[i]->instance())->keyReleaseEvent(e);
}

void	 GLWidget::wheelEvent ( QWheelEvent *e)
{
    for (unsigned int i=0; i<plugins.size(); ++i) 
      qobject_cast<BasicPlugin*>(plugins[i]->instance())->wheelEvent(e);
}

void GLWidget::addObjectFromFile(const QString& filename)
{
    std::string name = filename.toLocal8Bit().constData();
    Object o(name.c_str());
    o.readObj(name.c_str());
    pscene.addObject(o);
    
    // propagate to all plugins
    for (unsigned int i=0; i<plugins.size(); ++i) 
      qobject_cast<BasicPlugin*>(plugins[i]->instance())->onObjectAdd();
    
    resetCamera();
    updateGL();
}

void GLWidget::addObject()
{
    QStringList files = QFileDialog::getOpenFileNames(NULL, "Select one or more models to open", 
"/assig/grau-g/models", "Models (*.obj)");
    QStringList::Iterator it = files.begin();
    while(it != files.end()) 
    {
        QString    fileName = (*it);
        addObjectFromFile(fileName);
        ++it;
    }
}

