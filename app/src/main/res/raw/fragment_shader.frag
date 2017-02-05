precision mediump float;

uniform sampler2D u_TextureUnit;
uniform vec4 v_Color;
varying vec2 v_Texture;

void main()
{
    gl_FragColor = texture2D(u_TextureUnit, v_Texture);
    gl_FragColor = glFragColor * v_Color;
}