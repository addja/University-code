#ifndef CAMERA_H
#define CAMERA_H

#define _USE_MATH_DEFINES 1
#include "GL/glew.h"
#include "point.h"
#include "vector.h"
#include "box.h"

/*!
	\class Camera
	\brief The %Camera class represents a perspective camera.
*/

class CORE_EXPORT Camera
{
public:
	/*!
	Initializes the camera parameters from a bounding box of the scene.
	The camera is placed outside the box, ensuring that the whole box fits
	within the viewing frustum. 
	Then sets the GL_MODELVIEW and GL_PROJECTION matrices.
	*/
    void init(const Box& b);

	/*!
	Sets the GL_MODELVIEW matrix to the viewing transform represented by the camera.
	*/
    void setModelview() const;

	/*!
	Sets the GL_PROJECTION matrix to the projection transform represented by the camera.
	*/
    void setProjection() const;

	/*!
	Returns the camera (eye) position.
	*/
    Point getObs() const;

	/*!
	Sets the camera aspect ratio.
	*/    
    void setAspectRatio(float ar);

	/*!
	Recomputes the near, far clipping planes according to the given bounding box.
	Then sets the GL_PROJECTION matrix.
	*/ 
    void updateClippingPlanes(const Box&);
    
	/*!
	Moves the camera away (inc>0) or towards (inc<0) the current VRP.
	Then sets the GL_MODELVIEW matrix.
	*/
    void incrementDistance(float inc);

	/*!
	Rotates the camera by increasing the rotation angle around X axis (pitch).
	Then sets the GL_MODELVIEW matrix.
	*/
    void incrementAngleX(float inc);

	/*!
	Rotates the camera by increasing the rotation angle around Y axis (yaw).
	Then sets the GL_MODELVIEW matrix.
	*/
    void incrementAngleY(float inc);

	/*!
	Offsets the camera position and VRP.
	Then sets the GL_MODELVIEW matrix.
	*/     
    void pan(const Vector& offset); 

private:
    Point pvrp;  // view reference point
    float pdist; // distance obs-vrp
    float pangleX, pangleY, pangleZ;  //  Euler angles
    float pfovy; // fielf of view, vertical
    float paspectRatio; // aspect ratio
    float pzNear, pzFar; // clipping planes
};

#endif

