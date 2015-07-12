uniform sampler2D glossy;

varying vec3 P;
varying vec3 N;

vec4 sampleTexture(sampler2D sampler, vec2 st, int r)
{
	float H = 512.0;
	float W = 512.0;
	vec4 C = vec4(0.0,0.0,0.0,0.0);
	float c = 1.0/pow(2.0*float(r)+1.0,2.0);
	for (int i = -r; i <= r; ++i) {
		for (int j = -r; j <= r; ++j) {
			C += texture2D(sampler, vec2(st.x+float(i)/W,st.y+float(j)/H));
		}
	}  
	return c*C;
}

vec4 sampleSphereMap(sampler2D sampler, vec3 R)
{
	float z = sqrt((R.z+1.0)/2.0);
	vec2 st = vec2((R.x/(2.0*z)+1.0)/2.0, (R.y/(2.0*z)+1.0)/2.0);
    st.y = -st.y;
	int r = 10;
	return sampleTexture(sampler, st, r);
}

void main()
{
	vec3 obs = vec3(0.0);
	vec3 I = normalize(P-obs);
	vec3 R = reflect(I, N);
	
	gl_FragColor = sampleSphereMap(glossy, R);
}
