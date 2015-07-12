#ifndef _DRAW_VBO_H
#define _DRAW_VBO_H

#include <vector>
#include "basicplugin.h"

using namespace std;

class DrawVBO : public QObject, public BasicPlugin
{
     Q_OBJECT
     Q_INTERFACES(BasicPlugin)

 public:
 	~DrawVBO();
 
    void onPluginLoad();
    void onObjectAdd();
    bool drawScene();
   
 private:
    void addVBO(unsigned int currentObject);

    // We will create a VBO for each object in the scene
    vector<GLuint> coordBuffers;  // ID of vertex coordinates buffer 
    vector<GLuint> normalBuffers; // ID of normal components buffer 
    vector<GLuint> indexBuffers;  // ID of index buffer 
    vector<GLuint> numIndices;    // Size (number of indices) in each index buffer
 };
 
 #endif
 
 
