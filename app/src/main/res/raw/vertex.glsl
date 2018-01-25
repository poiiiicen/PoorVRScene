uniform mat4 uPerspectiveMatrix;
uniform mat4 uViewMatrix;
uniform mat4 uModelMatrix;
uniform mat4 uModelNormalMatrix;

attribute vec4 vPosition;
attribute vec4 vNormal;
attribute vec2 vTexCoord;

varying vec3 vFragPos;
varying vec3 vFragNormal;
varying vec2 vFragTexCoord;

void main() {
    gl_Position = uPerspectiveMatrix * uViewMatrix * uModelMatrix * vPosition;

    vFragPos = (uModelMatrix * vPosition).xyz;
    vFragNormal = normalize(mat3(uModelNormalMatrix) * vNormal.xyz);
    vFragTexCoord = vTexCoord;
}
