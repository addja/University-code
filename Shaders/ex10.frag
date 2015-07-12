// simple fragment shader
uniform sampler2D sampler;

// 'time' contains seconds since the program was linked.
uniform float time;

void main()
{
	gl_FragColor = gl_Color * texture2D(sampler, gl_TexCoord[0].st);
}
