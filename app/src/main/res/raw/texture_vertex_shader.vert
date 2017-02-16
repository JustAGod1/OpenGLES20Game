
uniform mat4 u_Matrix;
attribute vec4 a_Position;
uniform vec4 u_Translation;
attribute vec2 a_Texture;
varying vec2 v_Texture;


void main() {
    gl_Position = u_Matrix * (a_Position);
	v_Texture = a_Texture;
}
