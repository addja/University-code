// simple fragment shader

// 'time' contains seconds since the program was linked.
uniform float time;
uniform sampler2D sampler;	

void main()
{
	float slice = 1.0/30.0;
	float frame = mod(floor(time/slice),48);
	float s_offset = mod(frame,8.0);
	float t_offset;
	if (frame <= 7.0) t_offset = 0.0;
	else if (frame <= 15.0) t_offset = 1.0;
	else if (frame <= 23.0) t_offset = 2.0;
	else if (frame <= 31.0) t_offset = 3.0;
	else if (frame <= 39.0) t_offset = 4.0;
	else if (frame <= 47.0) t_offset = 5.0;
	vec2 tex = vec2(gl_TexCoord[0].st*vec2((1.0/8.0),(1.0/6.0)));
	vec4 c = texture2D(sampler,tex+vec2(s_offset/8.0,t_offset/6.0));
	gl_FragColor = c*c.a;
}