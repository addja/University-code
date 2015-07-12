#ifndef _MODELINFO_H  
#define _MODELINFO_H

#include "basicplugin.h"

class ModelInfo : public QObject, public BasicPlugin {
	Q_OBJECT
	Q_INTERFACES(BasicPlugin)

	public:
    	void postFrame();

};

#endif
 
 
