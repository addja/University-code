#include "show-help.h"
#include "glwidget.h"

void ShowHelp::postFrame() 
{ 
	glColor3f(0.0, 0.0, 0.0);
	int x = 5;
	int y = 15;
	glwidget()->renderText(x,y, QString("L - Load object     A - Add plugin"));
}





Q_EXPORT_PLUGIN2(show-help, ShowHelp)   // plugin name, plugin class
