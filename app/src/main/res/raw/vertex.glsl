attribute vec4 vPosition;
uniform mat4 vPerspectiveMatrix;
uniform mat4 vViewMatrix;
void main() {
    gl_Position = vPerspectiveMatrix * vViewMatrix * vPosition;
}
