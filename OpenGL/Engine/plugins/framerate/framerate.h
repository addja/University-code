#ifndef _FRAMERATE_H  
#define _FRAMERATE_H

#include <QElapsedTimer>
#include "basicplugin.h"

class Framerate : public QObject, public BasicPlugin
 {
     Q_OBJECT
     Q_INTERFACES(BasicPlugin)

 public:
    void preFrame();
    void postFrame();

 private:
    QElapsedTimer timer;
 };
 
 #endif
 
 
