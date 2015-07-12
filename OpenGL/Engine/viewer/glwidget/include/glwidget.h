#ifndef GLWIDGET_H
#define GLWIDGET_H

#include "GL/glew.h"
#include "scene.h"
#include "camera.h"
//#include "basicplugin.h"
#include <QPluginLoader>
#include <QtOpenGL/qgl.h>
#include <QKeyEvent>
#include <QString>
#include <QFileDialog>
#include <QTimer>
#include <iostream>
#include <vector>

class BasicPlugin;

// see Qt docs, "Creating shared libraries"
#ifdef PLUGINGLWIDGET_LIBRARY
#define PLUGINGLWIDGET_EXPORT Q_DECL_EXPORT
#else
#define PLUGINGLWIDGET_EXPORT Q_DECL_IMPORT
#endif

/*!
    /class GLWidget
    /brief The GLWidget class handles OpenGL rendering through plugins.
*/     

class  GLWidget : public QGLWidget  
{
    Q_OBJECT

public:
    /*!
    Creates a GLWidget.
    */
    PLUGINGLWIDGET_EXPORT GLWidget(QWidget * parent);

    /*!
    Returns a pointer to the 3D scene.
    */
    PLUGINGLWIDGET_EXPORT Scene* scene();

    /*!
    Returns a pointer to the camera. 
    */
    PLUGINGLWIDGET_EXPORT Camera* camera();

    /*!
    Sets the path where plugins will be searched into.
    */
    PLUGINGLWIDGET_EXPORT void setPluginPath(const QString &);

public slots:
    /*!
    Opens a file dialog enabling the user to select one or more OBJ files to add to the scene.
    All loaded plugins get their onObjectAdd() method invoked for each loaded object.
    */
    PLUGINGLWIDGET_EXPORT void addObject();

    /*!
    Adds an OBJ file to the scene.
    All loaded plugins get their onObjectAdd() method invoked. 
    */
    PLUGINGLWIDGET_EXPORT void addObjectFromFile(const QString& filename);

    /*!
    Resets the camera so that the whole scene (including world axes) is visible.
    */
    PLUGINGLWIDGET_EXPORT void resetCamera();

    /*!
    Draws three lines representing world space axes.
    */
    PLUGINGLWIDGET_EXPORT void drawAxes() const;

    /*!
    Returns the bounding box of the scene, enlarged to include also a representation of the world axes.
    */
    PLUGINGLWIDGET_EXPORT Box boundingBoxIncludingAxes() const;

    /*!
    Opens a file dialog enabling the user to select one or more plugins to load.
    After loading each plugin, its onPluginLoad() method is invoked.
    */
    PLUGINGLWIDGET_EXPORT void loadPlugin();

    /*!
    Loads one plugins and calls its onPluginLoad() method.
    */
    PLUGINGLWIDGET_EXPORT void loadPlugins(const QStringList& list);

    /*!
    Loads default plugins.
    */
    PLUGINGLWIDGET_EXPORT void loadDefaultPlugins();

    
private:
    void help(void);
    void showPlugins() const;

    virtual void initializeGL();
    virtual void paintGL( void );
    virtual void resizeGL (int width, int height);

    virtual void keyPressEvent(QKeyEvent *e);
    virtual void mousePressEvent( QMouseEvent *e);
    virtual void mouseReleaseEvent( QMouseEvent *);
    virtual void mouseMoveEvent(QMouseEvent *e);
    virtual void keyReleaseEvent ( QKeyEvent *e);
    virtual void wheelEvent ( QWheelEvent *e);
    
private:
    void resetPluginsToDefaults();
    Scene pscene;  
    Camera pcamera;
    QString pluginPath;
    
    BasicPlugin* drawPlugin;
    vector<QPluginLoader*> plugins;
};

#endif
