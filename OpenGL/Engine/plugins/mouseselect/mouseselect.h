#ifndef _MOUSESELECT_H
#define _MOUSESELECT_H

#include "basicplugin.h"

class MouseSelect : public QObject, public BasicPlugin {
    Q_OBJECT
    Q_INTERFACES(BasicPlugin)

    public:
        void postFrame();
 
    private:
    
};
 
#endif
 
 
