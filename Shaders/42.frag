// simple fragment shader

// 'time' contains seconds since the program was linked.
uniform float time;
uniform sampler2D ex;

void main()
{
	gl_FragColor = texture2D(ex,gl_TexCoord[0].st);
}
