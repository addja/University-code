// Daniel Otero Avalle - Pràctica 4 - IDI

#if defined(__APPLE__)
    #include <OpenGL/OpenGL.h>
    #include <GLUT/GLUT.h>
#else
    #include <GL/gl.h>
    #include <GL/freeglut.h>
#endif
#include <iostream>
#include "model.h"
#include <cmath>

using namespace std;

#define PI 3.14159265

// window size
int maxx = 600;
int maxy = 600;

// Global mouse position
int pnx = 0;
int pny = 0;

// Back scene color
double clback[4] = {0, 0, 0, 0};

// State of the aplication
int state = 0;

// Global rotation angle
double anglex = 0;
double angley = 0;
double anglez = 0;

// Patricio OBJ related variables
double xmax, ymax, zmax;
double xmin, ymin, zmin;
Model m;
double patrpos, patrangle, prevx, prevz, prevangle;
double speedm;
bool avanti;
bool moving = false;

// Global scene sphere
double bigX, bigY, bigZ;
double tinyX, tinyY, tinyZ;
double radius;
double cenX, cenY, cenZ;

// Reshape ratio
double ratio;

// Camera mode 1->Axon / 2->Perspe
int camera;

// axonometric camera
struct axonometric {
    double left;
    double right;
    double bottom;
    double top;
    double znear;
    double zfar;
} axo;

// Perspective camera
struct perspective {
    double angle;
    double znear;
    double zfar;
} pers;

// Camera parameters
double VRP[3];
double dist;
double OBS[3];

// Memory of states
int memory;

// To controle the aparition of walls
bool parets = true;

// first person camera variables
double newznear = 0.1;
bool typepers = true;

// Light parameters
bool light = true;
bool normal = true;
bool shades = true;
bool light0 = false;
bool light1 = true;
bool light2 = false;

float pos[4][3] = {5, 1.5, 5, 
                   5, 1.5, -5,
                   -5, 1.5, -5,
                    -5, 1.5, 5};
int mtok = 0;

void transformations(void) {
    glTranslated(0,0,-dist);
    glRotated(-anglez,0,0,1);
    glRotated(anglex,1,0,0);
    glRotated(-angley, 0, 1, 0);
    glTranslated(-VRP[0],-VRP[1],-VRP[2]);
}

void initLights() {
    float white[4] = { 1, 1, 1, 1 };
    glPushMatrix();
    glLightfv(GL_LIGHT1,GL_AMBIENT,white);
    glLightfv(GL_LIGHT1,GL_DIFFUSE,white);
    glLightfv(GL_LIGHT1,GL_SPECULAR,white);
    glPopMatrix();

    float yellow[4] = {1, 1, 0, 1};
    glLightfv(GL_LIGHT2,GL_AMBIENT,yellow);
    glLightfv(GL_LIGHT2,GL_DIFFUSE,yellow);
    glLightfv(GL_LIGHT2,GL_SPECULAR,yellow);
    float yellowpos[4];
    yellowpos[0] = pos[mtok][0];
    yellowpos[1] = pos[mtok][1];
    yellowpos[2] = pos[mtok][2];
    yellowpos[3] = 0;
    glLightfv(GL_LIGHT2,GL_POSITION,yellowpos);

    glLightfv(GL_LIGHT0,GL_AMBIENT,white);
    glLightfv(GL_LIGHT0,GL_DIFFUSE,white);
    glLightfv(GL_LIGHT0,GL_SPECULAR,white);
    float patmove[4] = { prevx, 0.75, prevz, 1 };
    glLightfv(GL_LIGHT0,GL_POSITION,patmove);
}

void initGl(void) {
    glMatrixMode(GL_MODELVIEW);
    glClearColor(clback[0],clback[1],clback[2],clback[3]);
    glEnable (GL_NORMALIZE);
    glLoadIdentity();
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    glEnable(GL_DEPTH_TEST);
    glPolygonMode (GL_FRONT_AND_BACK, GL_FILL);

    initLights();
    if (light) {
        glEnable(GL_LIGHTING);
        if (light1) glEnable(GL_LIGHT1);
    }
    else glDisable(GL_LIGHTING);
}

void calcModel(void) {
    m.load("Patricio.obj");
    for (int i = 0; i < (int)m.vertices().size(); i += 3) {    // Calcula capsa minima contenidora
        if (i == 0) {
            xmax = xmin = m.vertices()[i];
            ymax = ymin = m.vertices()[i+1];
            zmax = zmin = m.vertices()[i+2];
        }
        else {
            if (m.vertices()[i] > xmax) xmax = m.vertices()[i];
            if (m.vertices()[i] < xmin) xmin = m.vertices()[i];
            if (m.vertices()[i+1] > ymax) ymax = m.vertices()[i+1];
            if (m.vertices()[i+1] < ymin) ymin = m.vertices()[i+1];
            if (m.vertices()[i+2] > zmax) zmax = m.vertices()[i+2];
            if (m.vertices()[i+2] < zmin) zmin = m.vertices()[i+2];
        }
    }

    // variables to move patricio petit
    patrpos = prevx = prevz = 0;
    patrangle = prevangle = 270;
    speedm = 1;
}

void drawModel(void) {
    glPushMatrix();
    double factor = 1/(ymax - ymin);
    glScaled(factor,factor,factor);
    glTranslated(-(double)(xmin+xmax)/2,-ymin,-(double)(zmax+zmin)/2);

    int index = -1;

    for (int i = 0; i < (int)m.faces().size(); ++i) {
        const Face &f = m.faces()[i];

        if (f.mat != index) {
            glColor4fv(Materials[f.mat].diffuse);

            glMaterialfv(GL_FRONT_AND_BACK,GL_AMBIENT,Materials[f.mat].ambient);
            glMaterialfv(GL_FRONT_AND_BACK,GL_DIFFUSE,Materials[f.mat].diffuse);
            glMaterialfv(GL_FRONT_AND_BACK,GL_SPECULAR,Materials[f.mat].specular);
            glMaterialf(GL_FRONT_AND_BACK,GL_SHININESS,Materials[f.mat].shininess);
            index = f.mat;
        }

        glBegin(GL_POLYGON);
            if (!normal) glNormal3dv(f.normalC);
            for (int j = 0; j < 3; ++j) {
                if (normal) glNormal3dv(&m.normals() [f.n[j]]); 
                glVertex3dv(&m.vertices() [f.v[j]]);
            }
        glEnd();
    }
    glPopMatrix();
}


void drawVertex(double x, double y, double z) {
    glVertex3f(x,y,z);
    if (x > bigX) bigX = x;
    else if (x < tinyX) tinyX = x;
    if (y > bigY) bigY = y;
    else if (y < tinyY) tinyY = y;
    if (z > bigZ) bigZ = z;
    else if (z < tinyZ) tinyZ = z;
}

void drawFloor() {
    glPushMatrix();
    glBegin(GL_QUADS);
        glNormal3f(0,1,0);
        glColor3f(0.6,0,0);
        float white[4] = { 1, 1, 1, 1 };
        float blue[4] = { 0, 0, 0.2, 0 };
        float blue1[4] = { 0, 0, 0.6, 0 };
        glMaterialfv(GL_FRONT_AND_BACK,GL_AMBIENT,blue);
        glMaterialfv(GL_FRONT_AND_BACK,GL_DIFFUSE,blue1);
        glMaterialfv(GL_FRONT_AND_BACK,GL_SPECULAR,white);
        glMaterialf(GL_FRONT_AND_BACK,GL_SHININESS,100);
        drawVertex(5, 0, 5);
        drawVertex(-5, 0, 5);
        drawVertex(-5, 0, -5);
        drawVertex(5, 0, -5);
    glEnd();
    glPopMatrix();
}

void drawSnowman(void) {
    glTranslated(0,0.4,0);
    glPushMatrix();
    glColor3f(1,1,1);
    float white[4] = { 0.2, 0.2, 0.2, 1 };
    float white1[4] = { 0.6, 0.6, 0.6, 1 };
    float white2[4] = { 1, 1, 1, 1 };
    glMaterialfv(GL_FRONT_AND_BACK,GL_AMBIENT,white);
    glMaterialfv(GL_FRONT_AND_BACK,GL_DIFFUSE,white1);
    glMaterialfv(GL_FRONT_AND_BACK,GL_SPECULAR,white2);
    glMaterialf(GL_FRONT_AND_BACK,GL_SHININESS,100);
    glutSolidSphere(0.4,20,20);
    glPopMatrix();
    glPushMatrix();
    glTranslated(0,0.6,0);
    glutSolidSphere(0.2,20,20);
    glPopMatrix();
    glPushMatrix();
    glTranslated(0.1,0.6,0);
    glRotated(90,0,1,0);
    glColor3f(1,0.6,0);
    float orange[4] = { 0.4, 0.2, 0, 1 };
    float orange1[4] = { 0.8, 0.4, 0, 1 };
    glMaterialfv(GL_FRONT_AND_BACK,GL_AMBIENT,orange);
    glMaterialfv(GL_FRONT_AND_BACK,GL_DIFFUSE,orange1);
    glMaterialfv(GL_FRONT_AND_BACK,GL_SPECULAR,white2);
    glMaterialf(GL_FRONT_AND_BACK,GL_SHININESS,100);
    glutSolidCone(0.1,0.2,20,20);
    glPopMatrix();
}

void drawSnowmans(void) {
    glPushMatrix();
    glTranslated(2.5,0,-2.5);
    drawSnowman();
    glPopMatrix();

    glPushMatrix();
    glTranslated(-2.5,0,2.5);
    drawSnowman();
    glPopMatrix();

    glPushMatrix();
    glTranslated(-2.5,0,-2.5);
    drawSnowman();
    glPopMatrix();
}

void drawPatricioStatic(void) {
    glPushMatrix();
    glTranslated(2.5,0,2.5);
    glScaled(1.5,1.5,1.5);
    drawModel();
    glPopMatrix();
}

void drawPatricioDinamic(void) {
    glPushMatrix();
    if (patrangle == prevangle && moving) {
        if (avanti) {
            prevx += patrpos*sin(patrangle * PI / 180.0)/100 * speedm;
            prevz += patrpos*cos(patrangle * PI / 180.0)/100 * speedm;
        }
        else {
            prevx -= patrpos*sin(patrangle * PI / 180.0)/100 * speedm;
            prevz -= patrpos*cos(patrangle * PI / 180.0)/100 * speedm;
        }
        if (prevx > 5) {
            prevx = 5;
        }
        else if (prevx < -5) {
            prevx = -5;
        }
        if (prevz > 5) {
            prevz = 5;
        }
        else if (prevz < -5) {
            prevz = -5;
        }
        if (patrpos > 10) patrpos = 10;
    }
    else prevangle = patrangle;
    glTranslated(prevx,0,prevz);
    glRotated(patrangle,0,1,0);

    drawModel();
    glPopMatrix();

    moving = false;
}

void ImPatricio(void) {
    double OBSx = prevx;
    double OBSz = prevz;
    double OBSy = ((ymax-ymin)/2)/(ymax - ymin);
    double VRPx = OBSx + sin(patrangle * PI / 180.0);
    double VRPz = OBSz + cos(patrangle * PI / 180.0);
    double VRPy = ((ymax-ymin)/2)/(ymax - ymin);

    if (patrangle == prevangle && moving) {
        if (avanti) {
            prevx += patrpos*sin(patrangle * PI / 180.0)/100 * speedm;
            prevz += patrpos*cos(patrangle * PI / 180.0)/100 * speedm;
        }
        else {
            prevx += -patrpos*sin(patrangle * PI / 180.0)/100 * speedm;
            prevz += -patrpos*cos(patrangle * PI / 180.0)/100 * speedm;
        }
        if (prevx > 5) {
            prevx = 5;
            patrpos = prevx / sin(patrangle * PI / 180.0);
        }
        else if (prevx < -5) {
            prevx = -5;
            patrpos = prevx / sin(patrangle * PI / 180.0);
        }
        if (prevz > 5) {
            prevz = 5;
            patrpos = prevz / cos(patrangle * PI / 180.0);
        }
        else if (prevz < -5) {
            prevz = -5;
            patrpos = prevz / cos(patrangle * PI / 180.0);
        }
    }
    else prevangle = patrangle;

    moving = false;

    glLoadIdentity();
    gluLookAt(OBSx,OBSy,OBSz,VRPx,VRPy,VRPz,0,1,0);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    gluPerspective(60,ratio,0.1,20);
    glMatrixMode(GL_MODELVIEW);

}

void drawWalls(void) {
    glColor3f(0,1,0);
    float green[4] = { 0, 0.2, 0, 1 };
    float green1[4] = { 0, 0.6, 0, 1 };
    float mate [4] = { 0, 0, 0, 1};
    glMaterialfv(GL_FRONT_AND_BACK,GL_AMBIENT,green);
    glMaterialfv(GL_FRONT_AND_BACK,GL_DIFFUSE,green1);
    glMaterialfv(GL_FRONT_AND_BACK,GL_SPECULAR,mate);
    glMaterialf(GL_FRONT_AND_BACK,GL_SHININESS,0);
    glPushMatrix();
    glTranslated(0,0.75,-4.9);
    glScaled(10,1.5,0.2);
    glutSolidCube(1);
    glPopMatrix();

    glPushMatrix();
    glTranslated(1.5,0.75,2.5);
    glScaled(0.2,1.5,5);
    glutSolidCube(1);
    glPopMatrix();
}


void drawScene() {
    drawFloor();
    drawSnowmans();
    drawPatricioStatic();
    if (parets) drawWalls();
    if (typepers) drawPatricioDinamic();
}

void calcSceneSphere(void) {
    // updates the bounding sphere
    radius = sqrt(pow(bigX-tinyX,2)+pow(bigY-tinyY,2)+pow(bigZ-tinyZ,2))/2;
    radius = 1.5 / (sin (atan (1.5/radius)));
    cenX = (bigX+tinyX)/2;
    cenY = (bigY+tinyY)/2;
    cenZ = (bigZ+tinyZ)/2;

    VRP[0] = cenX; VRP[1] = cenY; VRP[2] = cenZ;
    dist = 2*radius;
    OBS[0] = VRP[0]+dist; OBS[1] = VRP[1]+dist; OBS[2] = VRP[2]+dist;
}

void calcReshapeRatio(void) {
    ratio = maxx/maxy;
    camera = 2; // sets default camera as perspective
    anglex = 30;
    angley = 0;

    // first provisional bounding sphere
    bigZ = sqrt(pow(5,2)+pow(5,2)); 
    bigX = bigY = 0;
    tinyZ = -bigZ; 
    tinyX = tinyY = 0;
}

void initCamera(void) {
    // Setting camera attributes
    axo.left = axo.bottom = -radius;
    axo.right = axo.top = radius;
    axo.znear = radius;
    axo.zfar = 3*radius;

    pers.znear = radius;
    pers.zfar = 3*radius;
    pers.angle = asin (radius/dist) * 180.0 / PI;
}


void motion(int x, int y) {
    // cout << "les x = " << x << " i les y = " << y << endl; // to debug
    if (state == 1) {
        double mem = ((x-pnx)/(double)maxx)*360;;
        angley -= mem;
        mem = ((y-pny)/(double)maxy)*360;
        anglex += mem;
    }
    else if (state == 2) {
        double mem = ((y-pny)/(double)maxy);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        if (camera == 1) {
            axo.left += mem;
            axo.right -= mem;
            axo.bottom += mem;
            axo.top -= mem;
            glOrtho(axo.left, axo.right, axo.bottom, axo.top, axo.znear, axo.zfar);
        }
        else {
            pers.angle -= mem*180/PI;
            gluPerspective(2*pers.angle,ratio,pers.znear,pers.zfar);
        }
        glMatrixMode(GL_MODELVIEW);
    }
    pnx = x;
    pny = y;
    glutPostRedisplay();
}

void mouse(int b, int p, int x, int y) {
    // if (state == 0) cout << "boto " << b << ", premut " << p << ", x = " << x << ", y = " << y << endl; // to debug
    pnx = x;
    pny = y;
    if (b == 2 && p == 0 && typepers) {
        memory = state;
        state = 2;
    }
    else if (b == 2 && p == 1 && typepers) state = memory;
    if (b == 0 && p == 0 && typepers) {
        memory = state;
        state = 1;
    }
    else if (b == 0 && p == 1 && typepers) state = memory;
}

void keyboard(unsigned char c, int x, int y) {
    // cout << "tecla = " << c << ", x = " << x << ", y = "<< y << endl; //to debug
    if (c == 'h') { 
        cout << "Help menu:." << endl << 
        "--Press h for help." << endl <<
        "--Press right mouse button and move it around the screen to rotate the scene." << endl <<
        "--Press w s to move Patricio petit forward and backward." << endl <<
        "--Press a d to change the direction of Patricio petit." << endl <<
        "--Press z x to increase/decrease the speed of Patricio petit movement." << endl <<
        "--Press p to change beetwen camera modes." << endl <<
        "--Press v to draw the walls on the scene." << endl <<
        "--Press r to reset scene." << endl <<
        "--Press c to switch beetwen first or thid person camera." << endl <<
        "--Press i to turn on or of the light." << endl <<
        "--Press n to change beetwen vertex normals or face normals." << endl <<
        "--Press l to change beetwen flat shading or smooth shading." << endl <<
        "--Press left mouse button and move up-down to zoom in or out the scene." << endl <<
        "--Press 1 to start/stop white camera light." << endl <<
        "--Press 2 to start/stop Patricio's follower white light." << endl <<
        "--Press 0 to start/stop yellow scene light." << endl <<
        "--Press m to change yellow scene light position (it jumps between corners of the floor)." << endl <<
        "--Press ESC to exit the application." << endl;
    }
    else if (c == 'n') {
        if (normal) cout << "face normals" << endl;
        else cout << "vertex normals" << endl;
        normal = !normal;
    }
    else if (c == 'm') {
        if (mtok == 3) mtok = 0;
        else ++mtok;
    }
    else if (c == '1') {
        if (light1) {
            glDisable(GL_LIGHT1);
            light1 = false;
        }
        else {
            glEnable(GL_LIGHT1);
            light1 = true;
        }
    }
    else if (c == '2') {
        if (light2) {
            glDisable(GL_LIGHT2);
            light2 = false;
        }
        else {
            glEnable(GL_LIGHT2);
            light2 = true;
        }
    }
    else if (c == '0') {
        if (light0) {
            glDisable(GL_LIGHT0);
            light0 = false;
        }
        else {
            glEnable(GL_LIGHT0);
            light0 = true;
        }
    }
    else if (c == 'l') {
        if (shades) cout << "flat shading on" << endl;
        else cout << "smooth shading on" << endl;
        shades = !shades;
        if (shades) glShadeModel(GL_FLAT);
        else glShadeModel(GL_SMOOTH);

    }
    else if (c == 'i') {
        if (light) cout << "Cool lights off!!!" << endl;
        else cout << "Cool lights on!!!!" << endl;
        light = !light;
    }
    else if (c == 'c') {
        cout << "Changing camera." << endl;
        typepers = !typepers;
        if (typepers) {
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            gluPerspective(2*pers.angle,ratio,pers.znear,pers.zfar);
            glMatrixMode(GL_MODELVIEW);
        }
    }
    else if (c == 'w') {
        patrpos += 0.1;
        if (patrpos > 10) patrpos = 10;
        avanti = true;
        moving = true;
    }else if (c == 's') {
        patrpos += 0.1;
        if (patrpos > 10) patrpos = 10;
        avanti = false;
        moving = true;
    }else if (c == 'a') {
        patrangle += 3.6;
        if (patrangle > 360) patrangle -= 360;
    }else if (c == 'd') {
        patrangle -= 3.6;
        if (patrangle < 0) patrangle += 360;
    }
    else if (c == 'z') {
        speedm += 1;
    }
    else if (c == 'x') {
        if (speedm > 1) speedm -= 1; 
    }
    else if (c == 'v') {
        parets = !parets;
    }
    else if (c == 'p') {
        cout << "Changing camera mode to ";
        if (camera == 1) {
            cout << " perspectiva" << endl;
            camera = 2;
        }
        else {
            cout << "axonometrica" << endl;
            camera = 1;
        }
    }
    else if (c == 'r') {
        angley = 0; 
        anglex = 30;
        camera = 2;
        prevx = prevz = patrpos = 0;
        patrangle = prevangle = 270;
        speedm = 1;
        typepers = true;
        glDisable(GL_LIGHT0);
        glDisable(GL_LIGHT0);
        glDisable(GL_LIGHT2);
        light = light1 = true;
        light2 = light0 = false;
        cout << "Scene reseted" << endl;
    }
    else if (c == 27) {
        exit(0);
    }
    glutPostRedisplay();
}

void reshape(int w, int h) {
    double newratio = (double)w/(double)h;
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    if (camera == 1) {
        if (newratio > ratio) {
            glOrtho(axo.left*newratio, axo.right*newratio, axo.bottom, axo.top, axo.znear, axo.zfar);
        }
        else {
            glOrtho(axo.left, axo.right, axo.bottom/newratio, axo.top/newratio, axo.znear, axo.zfar);
        }
    }
    else {
        double newangle = pers.angle;
        if(newratio < ratio) {
            newangle = atan (tan (pers.angle * PI /180)/newratio) * 180 / PI;
        }
        gluPerspective(2*newangle,newratio,pers.znear,pers.zfar);
    }
    glMatrixMode(GL_MODELVIEW);
    glViewport(0,0,w,h);
}

void refresh(void) {
    initGl();
    if (typepers) transformations();
    else ImPatricio();
    drawScene();
    glutSwapBuffers();
}

int main(int argc, const char * argv[]) {
    calcModel();
    calcReshapeRatio();
    calcSceneSphere();
    initCamera();
    glutInit(&argc, (char **)argv);
    glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGBA | GLUT_DEPTH);
    glutInitWindowSize(maxx,maxy);
    glutCreateWindow("IDI: Practiques OpenGL");
    glutReshapeFunc(reshape);
    glutDisplayFunc(refresh);
    glutMouseFunc(mouse);
    glutMotionFunc(motion);
    glutKeyboardFunc(keyboard);
    glutMainLoop();
    return 0;
}