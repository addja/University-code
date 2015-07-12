// simple fragment shader

// 'time' contains seconds since the program was linked.
uniform float time;
uniform bool classic;

void main()
{
	float s = gl_TexCoord[0].s;
	float t = gl_TexCoord[0].t;
	float d = distance(vec3(s,t,0),vec3(0.5,0.5,0.0));
	if (d <= 0.2) gl_FragColor = vec4(1.0,0.0,0.0,0.0);
	else if (classic && mod(atan(0.5-t,0.5-s)/(3.1415/16.0),2.0) < 1.0) {
		gl_FragColor = vec4(1.0,0.0,0.0,0.0);
	}
	else gl_FragColor = vec4(1.0,1.0,1.0,0.0);
	
}
