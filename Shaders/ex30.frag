// simple fragment shader
varying vec3 eyevert;

// 'time' contains seconds since the program was linked.
uniform float time;

void main()
{
	vec3 norm = normalize(cross(dFdx(eyevert),dFdy(eyevert)));
	gl_FragColor = gl_Color*norm.z;
}
