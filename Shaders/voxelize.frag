// simple fragment shader

// 'time' contains seconds since the program was linked.
uniform float time;
varying vec3 gNormal;

void main()
{
	gl_FragColor = gNormal.z * gl_Color;
}
