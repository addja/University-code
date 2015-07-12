// simple fragment shader

// 'time' contains seconds since the program was linked.
uniform float time;
varying float x;

void main()
{
	if ((x+1.0) > time) discard;
	else gl_FragColor = vec4(0.0,0.0,1.0,0.0);
}
