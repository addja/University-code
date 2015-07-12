// simple vertex shader

void main(){float d = sqrt(pow(gl_Vertex.x,2) + pow(gl_Vertex.y,2) + pow(gl_Vertex.z,2));vec3 V = gl_Vertex.xyz / d;gl_Position    = gl_ModelViewProjectionMatrix * vec4(V,1);gl_FrontColor  = gl_Color;gl_TexCoord[0] = gl_MultiTexCoord0;}
