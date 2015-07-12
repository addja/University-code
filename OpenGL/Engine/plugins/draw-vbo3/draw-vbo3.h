#ifndef _DRAW_VBO3_H
#define _DRAW_VBO3_H

#include <vector>
#include "basicplugin.h"
#include "glwidget.h"
#include <cstdio>
#include <cmath>

using namespace std;

class DrawVBO3 : public QObject, public BasicPlugin
{
     Q_OBJECT
     Q_INTERFACES(BasicPlugin)

 public:
    ~DrawVBO3();
 
    void onPluginLoad();
    void onObjectAdd();
    bool drawScene();
    void postFrame();
   
 private:
    void addVBO(unsigned int currentObject);
    float length(Vector v);
    float dot(Vector a, Vector b);
    float angleBetween(Vector a, Vector b);

    // We will create a VBO for each object in the scene
    vector<GLuint> coordBuffers;  // ID of vertex coordinates buffer 
    vector<GLuint> normalBuffers; // ID of normal components buffer 
    vector<GLuint> indexBuffers;  // ID of index buffer 
    vector<GLuint> numIndices;    // Size (number of indices) in each index buffer

    //ints to store info
    unsigned int nObj, nVerts, nFaces, VBsize, IBsize;
 };
 
 #endif
