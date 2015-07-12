// simple fragment shader

// 'time' contains seconds since the program was linked.
uniform float time;

void main()
{
	float s = gl_TexCoord[0].s * 9.0;
	if (s > 0.0 && s < 1.0 || 
		s > 2.0 && s < 3.0 ||
		s > 4.0 && s < 5.0 ||
		s > 6.0 && s < 7.0 ||
		s > 8.00) gl_FragColor = vec4(1.0,1.0,0.0,0.0); 
	else gl_FragColor = vec4(1.0,0.0,0.0,0.0);
}
