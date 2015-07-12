// simple geometry shader

// these lines enable the geometry shader support.
#version 120
#extension GL_EXT_geometry_shader4 : enable

uniform float speed;
uniform float time;

float pi = 3.141592;

void main( void )
{
	vec4 center = (gl_PositionIn[0]+gl_PositionIn[1]+gl_PositionIn[2])/3.0;
	float shrinkFactor = abs(sin(pi*time*speed));
	float t = mod(time, 2.0/speed);
	if (t < 1.0/speed) {
		if (gl_PrimitiveIDIn%2 == 0) {
			for( int i = 0 ; i < gl_VerticesIn ; i++ ) {
				gl_FrontColor  = gl_FrontColorIn[ i ];
				gl_Position    = gl_PositionIn  [ i ];
				gl_TexCoord[0] = gl_TexCoordIn  [ i ][ 0 ];
				EmitVertex();
			}
		}
		else {
			for( int i = 0 ; i < gl_VerticesIn ; i++ ) {
				gl_FrontColor  = gl_FrontColorIn[ i ];
				gl_Position    = mix(gl_PositionIn[i], center, shrinkFactor);
				gl_TexCoord[0] = gl_TexCoordIn  [ i ][ 0 ];
				EmitVertex();
			}		
		}
	}
	else {
		if (gl_PrimitiveIDIn%2 == 0) {
			for( int i = 0 ; i < gl_VerticesIn ; i++ ) {
				gl_FrontColor  = gl_FrontColorIn[ i ];
				gl_Position    = mix(gl_PositionIn[i], center, shrinkFactor);
				gl_TexCoord[0] = gl_TexCoordIn  [ i ][ 0 ];
				EmitVertex();
			}	
		}
		else {
			for( int i = 0 ; i < gl_VerticesIn ; i++ ) {
				gl_FrontColor  = gl_FrontColorIn[ i ];
				gl_Position    = gl_PositionIn  [ i ];
				gl_TexCoord[0] = gl_TexCoordIn  [ i ][ 0 ];
				EmitVertex();
			}		
		}		

	}
}