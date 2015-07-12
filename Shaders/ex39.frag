// simple fragment shader
varying vec3 eyevert;
uniform sampler2D sampler;

// 'time' contains seconds since the program was linked.
uniform float time;

void main()
{
	vec3 norm = normalize(cross(dFdx(eyevert),dFdy(eyevert)));
	gl_FragColor = texture2D(sampler,norm.x,norm.y)*norm.z;
}
