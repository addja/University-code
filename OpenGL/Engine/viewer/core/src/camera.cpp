#include "camera.h"
#include <cmath>
#include <QMatrix4x4>



void Camera::init(const Box& box)
{
    Point center = box.center();
    float radius = box.radius();

    pvrp = center;
    pdist = 2*radius;
    pfovy = 60; // 2 * asin (radi/2*radi)
    pzNear = radius;
    pzFar = 3*radius;

    pangleX = 0;
    pangleY = 0;
    pangleZ = 0;
    setProjection();
    setModelview();
}

void Camera::setModelview() const
{
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
    glTranslatef(0,0,-pdist);
    glRotatef(-pangleZ,0,0,1);
    glRotatef( pangleX,1,0,0);
    glRotatef(-pangleY,0,1,0);
    glTranslatef(-pvrp.x(),-pvrp.y(),-pvrp.z());
}

void Camera::setProjection() const
{
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    float realFovy = pfovy;
    if (paspectRatio < 1.0) 
      realFovy = 360.0/M_PI*atan(tan(pfovy*M_PI/360)/paspectRatio);
    gluPerspective(realFovy, paspectRatio, pzNear, pzFar);
}

Point Camera::getObs() const
{
    double viewMatrix[16];
    glGetDoublev(GL_MODELVIEW_MATRIX, viewMatrix);

    QMatrix4x4 m = QMatrix4x4(viewMatrix).transposed(); // transposta perquè el constructor de QMatrix4x4 assumeix data en row-major order
    QMatrix4x4 inv = m.inverted(); 
    return QVector3D(inv*QVector4D(0.0, 0.0, 0.0, 1.0));
}

void Camera::setAspectRatio(float ar)
{
    paspectRatio = ar;
    setProjection();
}

void Camera::incrementDistance(float inc)
{
    pdist += inc;
    setModelview();
}

void Camera::incrementAngleX(float inc)
{
    pangleX += inc;
    setModelview();
}

void Camera::incrementAngleY(float inc)
{
    pangleY += inc;
    setModelview();
}

void Camera::pan(const Vector& offset)
{
    pvrp = pvrp + offset;
    setModelview();
}

void Camera::updateClippingPlanes(const Box& box)
{
    Point obs = getObs();
    Vector v = pvrp - obs;
    v.normalize();
    Vector u = box.center()-obs;
    pzNear = Vector::dotProduct(u,v)-box.radius();
    if (pzNear < 0.1f) pzNear = 0.1f;
    pzFar = Vector::dotProduct(u,v)+box.radius();
    setProjection();
}

