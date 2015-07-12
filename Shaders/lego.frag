// simple fragment shader

// 'time' contains seconds since the program was linked.
uniform float time;
varying vec3 gNormal;
uniform sampler2D tex;

void main()
{
	vec3 N = gl_NormalMatrix * gNormal;
	gl_FragColor = N.z * gl_Color;
	if (gNormal.y > 0 ) gl_FragColor *= texture2D(tex, gl_TexCoord[0].st);
}
