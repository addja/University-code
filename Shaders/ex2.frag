// simple fragment shader

// 'time' contains seconds since the program was linked.
uniform float time;
varying float var;

void main()
{
	gl_FragColor = gl_Color * var;
}
