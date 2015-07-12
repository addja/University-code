#ifndef _ALPHABLENDING_H  
#define _ALPHABLENDING_H

#include "basicplugin.h"

class AlphaBlending : public QObject, public BasicPlugin
 {
     Q_OBJECT
     Q_INTERFACES(BasicPlugin)

 public:
    void preFrame();
    void postFrame();
 };
 
 #endif
 
 
