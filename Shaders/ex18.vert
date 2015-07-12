// simple vertex shader

void main()
{
	float minY = -1.0;
	float maxY = 1.0;
	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
	float val = 4.0 * ((gl_Position/gl_Position.w).y - minY) / (maxY - minY);
	float prop = fract(val);
	vec4 color;
	if (val < 1.0) color = mix(vec4(1,0,0,0),vec4(1,1,0,0),prop);
	else if (val < 2.0) color = mix(vec4(1,1,0,0),vec4(0,1,0,0),prop);
	else if (val < 3.0) color = mix(vec4(0,1,0,0),vec4(0,1,1,0),prop);
	else if (val < 4.0) color = mix(vec4(0,1,1,0),vec4(0,0,1,0),prop);
	gl_FrontColor  = color;
	gl_TexCoord[0] = gl_MultiTexCoord0;
}
