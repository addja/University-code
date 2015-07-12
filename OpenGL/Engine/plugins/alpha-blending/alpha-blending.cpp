#include "alpha-blending.h"
#include "glwidget.h"

void AlphaBlending::preFrame() 
{
    glDisable(GL_DEPTH_TEST);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE);
    glEnable(GL_BLEND);
}

void AlphaBlending::postFrame() 
{
    glEnable(GL_DEPTH_TEST);
    glDisable(GL_BLEND);
}

Q_EXPORT_PLUGIN2(alpha-blending, AlphaBlending)   // plugin name, plugin class
