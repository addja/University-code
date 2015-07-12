// simple geometry shader

// these lines enable the geometry shader support.
#version 120
#extension GL_EXT_geometry_shader4 : enable

const float speed = 1.2; 
const float angSpeed = 8.0;
uniform float time;
varying in vec3 vNormal[3];

void main( void )
{
	float time1 = mod(time,4.0);

	// translació T
	vec3 n = (vNormal[0] + vNormal[1] + vNormal[2])/3.0;
	vec3 t = speed*time1*n;
	mat4 T = mat4(
		1.0,0.0,0.0,0.0,
		0.0,1.0,0.0,0.0,
		0.0,0.0,1.0,0.0,
		t.x,t.y,t.z,1.0
	);


	// Rotació Rz
	vec3 bar = vec3(0.0,0.0,0.0);
	for (int i = 0; i < gl_VerticesIn; i++) {
		bar += gl_PositionIn[i].xyz; 
	}
	bar /= gl_VerticesIn;

	mat4 trans = mat4(
		1.0,0.0,0.0,0.0,
		0.0,1.0,0.0,0.0,
		0.0,0.0,1.0,0.0,
		-bar.x,-bar.y,-bar.z,1.0
	);

	mat4 trans2 = mat4(
		1.0,0.0,0.0,0.0,
		0.0,1.0,0.0,0.0,
		0.0,0.0,1.0,0.0,
		bar.x,bar.y,bar.z,1.0
	);

	float a = angSpeed*time1;
	mat4 rot = mat4(
		cos(a),sin(a),0.0,0.0,
		-sin(a),cos(a),0.0,0.0,
		0.0,0.0,1.0,0.0,
		0.0,0.0,0.0,1.0
	);

	mat4 R = trans2*rot*trans;

	for( int i = 0 ; i < gl_VerticesIn ; i++ )
	{
		gl_FrontColor  = gl_FrontColorIn[i];
		gl_Position = gl_ModelViewProjectionMatrix
			*T*R*gl_PositionIn[i];
		EmitVertex();
	}
}
