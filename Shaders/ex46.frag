// simple fragment shader

// 'time' contains seconds since the program was linked.
uniform sampler2D map;
uniform float time;
uniform float a;

void main()
{
	float angle = 2.0*3.1415*time;
	vec3 col = texture2D(map,gl_TexCoord[0].st).xyz;
	float m = max(col.x,col.y);
	m = max(m,col.z);
	mat2 rot = mat2(
		cos(angle),sin(angle),
		-sin(angle),cos(angle)
	);
	vec2 u = rot*vec2(m,m);
	vec2 offset = a/100.0*u;
	gl_FragColor = texture2D(map,gl_TexCoord[0].st+offset);
}
