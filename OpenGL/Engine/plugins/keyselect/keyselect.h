#ifndef _KEYSELECT_H
#define _KEYSELECT_H

#include "basicplugin.h"

class KeySelect : public QObject, public BasicPlugin {
    Q_OBJECT
    Q_INTERFACES(BasicPlugin)

    public:
        void keyPressEvent(QKeyEvent *);
        void postFrame();
 
    private:

};
 
#endif
 
 
