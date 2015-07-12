// simple fragment shader

// 'time' contains seconds since the program was linked.
uniform float time;
varying vec4 pos;
uniform vec3 origin;
uniform vec3 axis;
uniform float slice;

float deter(mat2 m){
	return m[0][0]*m[1][1] - m[1][0]*m[0][1];
}

void main()
{
	vec4 blue = vec4(0,0,1,0);
	vec4 cyan = vec4(0,1,1,0);
	mat2 M1 = mat2(
		pos.y-origin.y, axis.y,
		pos.z-origin.z, axis.z 
	);
	mat2 M2 = mat2(
		pos.z-origin.z, axis.z,
		pos.x-origin.x, axis.x 
	);
	mat2 M3 = mat2(
		pos.x-origin.x, axis.x,
		pos.y-origin.y, axis.y 
	);
	float denom = axis.x*axis.x + axis.y*axis.y + axis.z*axis.z;
	float d = sqrt((pow(deter(M1),2) + pow(deter(M2),2) + pow(deter(M3),2))/denom);
	if (mod(d/slice,2.0) < 1.0)
		gl_FragColor = cyan;
	else gl_FragColor = blue;
}
