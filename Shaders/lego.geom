// these lines enable the geometry shader support.
#version 120
#extension GL_EXT_geometry_shader4 : enable

uniform float step;
varying out vec3 gNormal;

float max3 (vec3 v) {
	return max(v.x,max(v.y,v.z));
}

void main( void )
{
	vec3 baricenter = (gl_PositionIn[0].xyz+gl_PositionIn[1].xyz+gl_PositionIn[2].xyz)/3;
	vec3 c = floor(bari/step+vec3(0.5,0.5,0.5)) * step;

	vec4 color = gl_FrontColorIn[0] + gl_FrontColorIn[1] + gl_FrontColorIn[2];
	float mx = max3(color.xyz);
	if (color.x == color.y && color.y == color.z) gl_FrontColor  = vec4(1,1,1,1);
	else if (max3(color.xyz) == color.x) gl_FrontColor  = vec4(1,0,0,1);
	else if (max3(color.xyz) == color.y) gl_FrontColor  = vec4(0,1,0,1);	
	else /*if (max3(color.xyz) == color.z)*/ gl_FrontColor  = vec4(0,0,1,1);	

	float s = step/2;

    //-x
    gNormal = normalize(vec3(-1,0,0));
    gl_Position    = gl_ModelViewProjectionMatrix * vec4(c.x-s,c.y+s,c.z+s,1) ;
    EmitVertex();
    gl_Position    = gl_ModelViewProjectionMatrix * vec4(c.x-s,c.y-s,c.z-s,1) ;
    EmitVertex();
    gl_Position    = gl_ModelViewProjectionMatrix * vec4(c.x-s,c.y-s,c.z+s,1) ;
    EmitVertex();
	EndPrimitive();
    gl_Position    = gl_ModelViewProjectionMatrix * vec4(c.x-s,c.y+s,c.z+s,1) ;
    EmitVertex();
    gl_Position    = gl_ModelViewProjectionMatrix * vec4(c.x-s,c.y-s,c.z-s,1) ;
    EmitVertex();
    gl_Position    = gl_ModelViewProjectionMatrix * vec4(c.x-s,c.y+s,c.z-s,1) ;
    EmitVertex();
	EndPrimitive();

    //-y
    gNormal = normalize(vec3(0,-1,0));
    gl_Position    = gl_ModelViewProjectionMatrix * vec4(c.x+s,c.y-s,c.z+s,1) ;
    EmitVertex();
    gl_Position    = gl_ModelViewProjectionMatrix * vec4(c.x-s,c.y-s,c.z-s,1) ;
    EmitVertex();
    gl_Position    = gl_ModelViewProjectionMatrix * vec4(c.x-s,c.y-s,c.z+s,1) ;
    EmitVertex();
    EndPrimitive();
    gl_Position    = gl_ModelViewProjectionMatrix * vec4(c.x+s,c.y-s,c.z+s,1) ;
    EmitVertex();
    gl_Position    = gl_ModelViewProjectionMatrix * vec4(c.x-s,c.y-s,c.z-s,1) ;
    EmitVertex();
    gl_Position    = gl_ModelViewProjectionMatrix * vec4(c.x+s,c.y-s,c.z-s,1) ;
    EmitVertex();
    EndPrimitive();

    //-z
    gNormal = normalize(vec3(0,0,-1));
    gl_Position    = gl_ModelViewProjectionMatrix * vec4(c.x-s,c.y-s,c.z-s,1) ;
    EmitVertex();
    gl_Position    = gl_ModelViewProjectionMatrix * vec4(c.x+s,c.y+s,c.z-s,1) ;
    EmitVertex();
    gl_Position    = gl_ModelViewProjectionMatrix * vec4(c.x-s,c.y+s,c.z-s,1) ;
    EmitVertex();
    EndPrimitive();
    gl_Position    = gl_ModelViewProjectionMatrix * vec4(c.x-s,c.y-s,c.z-s,1) ;
    EmitVertex();
    gl_Position    = gl_ModelViewProjectionMatrix * vec4(c.x+s,c.y+s,c.z-s,1) ;
    EmitVertex();
    gl_Position    = gl_ModelViewProjectionMatrix * vec4(c.x+s,c.y-s,c.z-s,1) ;
    EmitVertex();
    EndPrimitive();
    
    //+x
    gNormal = normalize(vec3(1,0,0));
    gl_Position    = gl_ModelViewProjectionMatrix * vec4(c.x+s,c.y+s,c.z+s,1) ;
    EmitVertex();
    gl_Position    = gl_ModelViewProjectionMatrix * vec4(c.x+s,c.y-s,c.z-s,1) ;
    EmitVertex();
    gl_Position    = gl_ModelViewProjectionMatrix * vec4(c.x+s,c.y-s,c.z+s,1) ;
    EmitVertex();
    EndPrimitive();
    gl_Position    = gl_ModelViewProjectionMatrix * vec4(c.x+s,c.y+s,c.z+s,1) ;
    EmitVertex();
    gl_Position    = gl_ModelViewProjectionMatrix * vec4(c.x+s,c.y-s,c.z-s,1) ;
    EmitVertex();
    gl_Position    = gl_ModelViewProjectionMatrix * vec4(c.x+s,c.y+s,c.z-s,1) ;
    EmitVertex();
    EndPrimitive();

    //+z
    gNormal = normalize(vec3(0,0,1));
    gl_Position    = gl_ModelViewProjectionMatrix * vec4(c.x-s,c.y-s,c.z+s,1) ;
    EmitVertex();
    gl_Position    = gl_ModelViewProjectionMatrix * vec4(c.x+s,c.y+s,c.z+s,1) ;
    EmitVertex();
    gl_Position    = gl_ModelViewProjectionMatrix * vec4(c.x-s,c.y+s,c.z+s,1) ;
    EmitVertex();
    EndPrimitive(); 
    gl_Position    = gl_ModelViewProjectionMatrix * vec4(c.x-s,c.y-s,c.z+s,1) ;
    EmitVertex();
    gl_Position    = gl_ModelViewProjectionMatrix * vec4(c.x+s,c.y+s,c.z+s,1) ;
    EmitVertex();
    gl_Position    = gl_ModelViewProjectionMatrix * vec4(c.x+s,c.y-s,c.z+s,1) ;
    EmitVertex();
    EndPrimitive(); 
    
    //+y
    gNormal = normalize(vec3(0,1,0));
	gl_TexCoord[0] = vec4(0,0,0,0);
    gl_Position    = gl_ModelViewProjectionMatrix * vec4(c.x+s,c.y+s,c.z+s,1) ;
    EmitVertex();
	gl_TexCoord[0] = vec4(1,1,0,0);
    gl_Position    = gl_ModelViewProjectionMatrix * vec4(c.x-s,c.y+s,c.z-s,1) ;
    EmitVertex();
	gl_TexCoord[0] = vec4(1,0,0,0);
    gl_Position    = gl_ModelViewProjectionMatrix * vec4(c.x-s,c.y+s,c.z+s,1) ;
    EmitVertex();
    EndPrimitive();
	gl_TexCoord[0] = vec4(0,0,0,0);
    gl_Position    = gl_ModelViewProjectionMatrix * vec4(c.x+s,c.y+s,c.z+s,1) ;
    EmitVertex();
	gl_TexCoord[0] = vec4(1,1,0,0);
    gl_Position    = gl_ModelViewProjectionMatrix * vec4(c.x-s,c.y+s,c.z-s,1) ;
    EmitVertex();
	gl_TexCoord[0] = vec4(0,1,0,0);
    gl_Position    = gl_ModelViewProjectionMatrix * vec4(c.x+s,c.y+s,c.z-s,1) ;
    EmitVertex();
    EndPrimitive();
}
