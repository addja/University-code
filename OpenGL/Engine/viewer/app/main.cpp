#include <QApplication>
#include "glwidget.h"

int main(int argc, char ** argv)
{
    QApplication a(argc, argv);
    GLWidget glWidget(NULL);
    QString appPath = a.applicationDirPath();
# if defined(__APPLE__)
    appPath.append("/../../../");
#else
    appPath.append("/");
# endif
    glWidget.setPluginPath(appPath.append("../../plugins/bin/"));
    glWidget.show();
    glWidget.loadDefaultPlugins();
    glWidget.addObject();
    a.connect( &a, SIGNAL( lastWindowClosed() ), &a, SLOT( quit() ) );
    return a.exec();
}


