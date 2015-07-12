#ifndef _COLORSELECT_H
#define _COLORSELECT_H

#include "basicplugin.h"

class ColorSelect : public QObject, public BasicPlugin {
    Q_OBJECT
    Q_INTERFACES(BasicPlugin)

    public:
        void mouseReleaseEvent(QMouseEvent *);
        void postFrame();
 
    private:
    	void drawSceneMod();
    	int objectFromColorSelect(float x, float y);
};
 
#endif
 
 
