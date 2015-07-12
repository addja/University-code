// simple fragment shader

// 'time' contains seconds since the program was linked.
uniform float time;
uniform int textureSize;
uniform int edgeSize;
uniform float threshold;
uniform sampler2D sampler;

void main()
{
	vec2 left = gl_TexCoord[0].st+float(edgeSize)*vec2(-1.0,0.0)/float(textureSize);
	vec2 right = gl_TexCoord[0].st+float(edgeSize)*vec2(1.0,0.0)/float(textureSize);
	vec2 bottom = gl_TexCoord[0].st+float(edgeSize)*vec2(0.0,-1.0)/float(textureSize);
	vec2 top = gl_TexCoord[0].st+float(edgeSize)*vec2(0.0,1.0)/float(textureSize);
	float gx = length(texture2D(sampler,right)-texture2D(sampler,left));
	float gy = length(texture2D(sampler,top)-texture2D(sampler,bottom));
	float t = length(vec2(gx,gy));
	if (t < threshold) gl_FragColor = texture2D(sampler,gl_TexCoord[0].st);
	else gl_FragColor = vec4(0.0,0.0,0.0,1.0);
}
