#ifndef _RENDERDEFAULT_H
#define _RENDERDEFAULT_H

#include "basicplugin.h"

 class RenderDefault : public QObject, public BasicPlugin
 {
     Q_OBJECT
     Q_INTERFACES(BasicPlugin)

 public:
     bool paintGL();
 };
 
 #endif
 
 
