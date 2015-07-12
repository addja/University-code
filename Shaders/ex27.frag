// simple fragment shader

// 'time' contains seconds since the program was linked.
uniform float time;
varying float num;

void main()
{
	if (num > 0.0) gl_FragColor = gl_Color;
	else discard;
}
