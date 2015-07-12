// simple fragment shader

// 'time' contains seconds since the program was linked.
uniform float time;
uniform int N;

void main()
{
	float i = gl_TexCoord[0].s * float(N);
	float j = gl_TexCoord[0].t * float(N);
	if (fract(i) < 0.1 || fract(j) < 0.1) 
		 gl_FragColor = vec4(1.0,0.0,0.0,0.0);
	else discard;
}