#include "navigatedefault.h"
#include "glwidget.h"

NavigateDefault::NavigateDefault() : pmouseAction(NONE)
{}

void NavigateDefault::mousePressEvent( QMouseEvent *e)
{
    pxClick = e->x();
    pyClick = e->y();

    if (e->button() & Qt::LeftButton && 
        ! (e->modifiers()&(Qt::ShiftModifier|Qt::AltModifier|Qt::ControlModifier)))
    {
        pmouseAction = ROTATE;
    }
    else if (e->button() & Qt::LeftButton &&  e->modifiers() & Qt::ShiftModifier)
    {
        pmouseAction = ZOOM;
    }
    else if (e->button() & Qt::RightButton && ! (e->modifiers()&(Qt::ShiftModifier|Qt::AltModifier|Qt::ControlModifier)))
    {
        pmouseAction = PAN;
    }
}

void NavigateDefault::mouseReleaseEvent( QMouseEvent *)
{
    pmouseAction = NONE;
}

void NavigateDefault::mouseMoveEvent(QMouseEvent *e)
{ 
    float mouseDeltaX = e->x() - pxClick;
    float mouseDeltaY = e->y() - pyClick;

    Box box = glwidget()->boundingBoxIncludingAxes();
    Point center = box.center();
    float radius = box.radius();

    if (pmouseAction == ROTATE)
    {
        camera()->incrementAngleY(-mouseDeltaX);  
        camera()->incrementAngleX(mouseDeltaY);
    }
    else if(pmouseAction == ZOOM) 
    {
        camera()->incrementDistance(mouseDeltaY * 0.01 * radius);
    }
    else if (pmouseAction == PAN)
    {
        double x = -0.3 * mouseDeltaX;
        double y =  0.3 * mouseDeltaY;
        float m[4][4];
        glGetFloatv (GL_MODELVIEW_MATRIX, &m[0][0]);
        Vector s(m[0][0], m[1][0], m[2][0]);
        Vector u(m[0][1], m[1][1], m[2][1]);
        camera()->pan(x * s + y * u);
    }

    // actualizar plans de retallat
    camera()->updateClippingPlanes(box);

    pxClick=e->x();
    pyClick=e->y();

    glwidget()->updateGL();
}

Q_EXPORT_PLUGIN2(navigatedefault, NavigateDefault)   // plugin name, plugin class
