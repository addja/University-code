// simple fragment shader

// 'time' contains seconds since the program was linked.
uniform float time;
uniform sampler2D grass;
uniform sampler2D rock;
uniform sampler2D noise;

void main()
{
	vec4 n = texture2D(noise,gl_TexCoord[0].st);
	vec4 g = texture2D(grass,gl_TexCoord[0].st);
	vec4 r = texture2D(rock,gl_TexCoord[0].st);
	gl_FragColor = g*n+(vec4(1.0,1.0,1.0,1.0)-n)*r;
}
