// simple fragment shader

// 'time' contains seconds since the program was linked.
uniform float time;
uniform float slice;
uniform sampler2D sampler0;
uniform sampler2D sampler1;
uniform sampler2D sampler2;
uniform sampler2D sampler3;

void main()
{
	float frame = mod(floor(time/slice),4);
	vec4 tex;
	if (frame < 1.0) tex = texture2D(sampler0,gl_TexCoord[0].st);
	else if (frame < 2.0) tex = texture2D(sampler1,gl_TexCoord[0].st);
	else if (frame < 3.0) tex = texture2D(sampler2,gl_TexCoord[0].st);
	else tex = texture2D(sampler3,gl_TexCoord[0].st);
	gl_FragColor = tex;
}