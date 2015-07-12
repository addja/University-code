// simple fragment shader

// 'time' contains seconds since the program was linked.
uniform float time;

void main()
{
	gl_FragColor = gl_Color;
	gl_FragDepth = 1.0-gl_FragCoord.z;
}
