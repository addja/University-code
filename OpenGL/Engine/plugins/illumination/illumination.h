#ifndef _ILLUMINATION_H
#define _ILLUMINATION_H

#include "basicplugin.h"
#include <QGLShader>
#include <QGLShaderProgram>


class Illumination : public QObject, public BasicPlugin {
    Q_OBJECT
    Q_INTERFACES(BasicPlugin)

	public:
    	void onPluginLoad();
    	void preFrame();
    	void postFrame();
    
	private:
    	QGLShaderProgram* program;
    	QGLShader* vs; 
    	QGLShader* fs; 
};
 
#endif
