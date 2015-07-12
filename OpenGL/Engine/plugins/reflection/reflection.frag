uniform sampler2D colorMap;
uniform float SIZE;

void main()
{
    vec2 st = (gl_FragCoord.xy - vec2(0.5)) / SIZE;
    gl_FragColor = 0.8 * texture2D(colorMap, st);
    
}

