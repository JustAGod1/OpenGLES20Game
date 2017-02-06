attribute vec4 a_Position;
uniform mat4 u_Matrix;
attribute vec4 a_Color;
varying vec4 v_Color;

void main()
{
    gl_Position = a_Position * u_Matrix;
    v_Color = a_Color;
}