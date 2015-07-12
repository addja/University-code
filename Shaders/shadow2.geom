// simple geometry shader

// these lines enable the geometry shader support.
#version 120
#extension GL_EXT_geometry_shader4 : enable

void main( void )
{
	for( int i = 0 ; i < gl_VerticesIn ; i++ )
	{
		gl_FrontColor  = gl_FrontColorIn[ i ];
		gl_Position    = gl_ModelViewProjectionMatrix * gl_PositionIn  [ i ];
		gl_TexCoord[0] = gl_TexCoordIn  [ i ][ 0 ];
		EmitVertex();
	}
	EndPrimitive();

	for( int i = 0 ; i < gl_VerticesIn ; i++ )
	{
		gl_FrontColor  = vec4(0.0,0.0,0.0,0.0);
		vec4 v = vec4(gl_PositionIn[i].x,-2.0,gl_PositionIn[i].z,gl_PositionIn[i].w);
		gl_Position    = gl_ModelViewProjectionMatrix* v;
		EmitVertex();
	}
	EndPrimitive();

	if (gl_PrimitiveIDIn == 0) {
		// one triangle
		gl_Position    = gl_ModelViewProjectionMatrix*vec4(4.0,-2.1,-4.0,1.0);
		gl_FrontColor  = vec4(1.0,0.0,1.0,1.0);
		EmitVertex();
		gl_Position    = gl_ModelViewProjectionMatrix*vec4(4.0,-2.1,4.0,1.0);
		EmitVertex();
		gl_Position    = gl_ModelViewProjectionMatrix*vec4(-4.0,-2.1,4.0,1.0);
		EmitVertex();
		EndPrimitive();

		// another triangle
		gl_Position    = gl_ModelViewProjectionMatrix*vec4(4.0,-2.1,-4.0,1.0);
		gl_FrontColor  = vec4(1.0,0.0,1.0,1.0);
		EmitVertex();
		gl_Position    = gl_ModelViewProjectionMatrix*vec4(-4.0,-2.1,-4.0,1.0);
		EmitVertex();
		gl_Position    = gl_ModelViewProjectionMatrix*vec4(-4.0,-2.1,4.0,1.0);
		EmitVertex();
		EndPrimitive();
	}
}
