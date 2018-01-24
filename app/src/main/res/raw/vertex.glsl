uniform mat4 uPerspectiveMatrix;
uniform mat4 uViewMatrix;
uniform mat4 uModelMatrix;

attribute vec4 vPosition;

void main() {
    gl_Position = uPerspectiveMatrix * uViewMatrix * uModelMatrix * vPosition;
}
