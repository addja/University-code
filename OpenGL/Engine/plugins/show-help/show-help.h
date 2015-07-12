#ifndef _SHOWHELP_H
#define _SHOWHELP_H

#include "basicplugin.h"

class ShowHelp : public QObject, public BasicPlugin
 {
     Q_OBJECT
     Q_INTERFACES(BasicPlugin)

 public:
    void postFrame();
    
 private:

 };
 
 #endif
