#ifndef _SHOWDEGREE_H  
#define _SHOWDEGREE_H

#include "basicplugin.h"

class Showdegree : public QObject, public BasicPlugin {
	Q_OBJECT
	Q_INTERFACES(BasicPlugin)

	public:
    	void postFrame();
    	void onPluginLoad();

    private:
    	float res;

};

#endif
 
 
