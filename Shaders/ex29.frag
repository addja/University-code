// simple fragment shader

// 'time' contains seconds since the program was linked.
uniform float time;
varying vec4 eyecoord;
varying vec4 norm;
uniform float epsilon;
uniform float light;

void main()
{
	float x = dot(normalize(-eyecoord),normalize(norm));
	if (abs(x) > epsilon) gl_FragColor = gl_Color*light*norm.z;
	else gl_FragColor = vec4(0.7,0.6,0.0,0.0);
}
