precision mediump float;

uniform sampler2D u_TextureUnit;
uniform vec4 u_Color;
varying vec4 v_Color;

void main()
{

    gl_FragColor = u_Color * v_Color;

}