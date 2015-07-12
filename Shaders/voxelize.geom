// these lines enable the geometry shader support.
#version 120
#extension GL_EXT_geometry_shader4 : enable

uniform float step;
varying out vec3 gNormal;

void main( void )
{
	// find center by normalizing center with step, adding 0.5 to round up (nearest
	// int uppwards) and multiply by step in order to directly get the points latter on
    vec3 baricenter = (gl_PositionIn[0].xyz+gl_PositionIn[1].xyz+gl_PositionIn[2].xyz)/3;
	vec3 c = floor(baricenter/step+vec3(0.5,0.5,0.5)) * step;

	float s = step/2;
    gl_FrontColor  = vec4(0.5,0.5,0.5,1.0); 

    //-x, each face of the square are two triangles
    gNormal = gl_NormalMatrix * normalize(vec3(-1,0,0));
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
    gNormal = gl_NormalMatrix * normalize(vec3(0,-1,0));
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
    gNormal = gl_NormalMatrix * normalize(vec3(0,0,-1));
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
    gNormal = gl_NormalMatrix * normalize(vec3(1,0,0));
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
    gNormal = gl_NormalMatrix * normalize(vec3(0,0,1));
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
    gNormal = gl_NormalMatrix * normalize(vec3(0,1,0));
    gl_Position    = gl_ModelViewProjectionMatrix * vec4(c.x+s,c.y+s,c.z+s,1) ;
    EmitVertex();
    gl_Position    = gl_ModelViewProjectionMatrix * vec4(c.x-s,c.y+s,c.z-s,1) ;
    EmitVertex();
    gl_Position    = gl_ModelViewProjectionMatrix * vec4(c.x-s,c.y+s,c.z+s,1) ;
    EmitVertex();
    EndPrimitive();
    gl_Position    = gl_ModelViewProjectionMatrix * vec4(c.x+s,c.y+s,c.z+s,1) ;
    EmitVertex();
    gl_Position    = gl_ModelViewProjectionMatrix * vec4(c.x-s,c.y+s,c.z-s,1) ;
    EmitVertex();
    gl_Position    = gl_ModelViewProjectionMatrix * vec4(c.x+s,c.y+s,c.z-s,1) ;
    EmitVertex();
    EndPrimitive();
}
