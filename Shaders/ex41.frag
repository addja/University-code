// simple fragment shader

// 'time' contains seconds since the program was linked.
uniform float time;
uniform sampler2D sampler;

void main()
{
	if (fract(time) <= 0.5) {
		gl_FragColor = texture2D(sampler,gl_TexCoord[0].st);
	}
	else {
		if (distance(gl_TexCoord[0].st,vec2(0.393,0.348)) < 0.025) {
			gl_FragColor = texture2D(sampler,vec2(0.057,0.172)+gl_TexCoord[0].st);
		}
		else gl_FragColor = texture2D(sampler,gl_TexCoord[0].st);
	}
}
