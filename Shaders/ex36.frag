// simple fragment shader

// 'time' contains seconds since the program was linked.
uniform float time;
uniform int nstripes;
uniform vec2 origin;

void main()
{
	float l = length(gl_TexCoord[0].st - origin);
	float n = 1.0/float(nstripes);
	// same thing as doing l*nstripes
	if (mod(l/n,2.0) < 1.0)
		gl_FragColor = vec4(1,0,0,0);
	else gl_FragColor = vec4(1,1,0,0);
}
