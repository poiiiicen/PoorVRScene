precision mediump float;

#define NUM_POINT_LIGHTS 4

struct PointLight {
   float valid;
   vec3 position;
   vec3 color;
   float ambientCoeff;
};

struct Material {
   vec3 ambient;
   vec3 diffuse;
   vec3 specular;
   float shininess;
   float useDiffuseMap;
   sampler2D diffuseMap;
};

uniform vec3 uCameraPos;

uniform PointLight uPointLights[NUM_POINT_LIGHTS];

uniform Material uMaterial;

varying vec3 vFragPos;
varying vec3 vFragNormal;
varying vec2 vFragTexCoord;

vec3 materialAmbient;
vec3 materialDiffuse;

vec3 calcPointLight(PointLight light, vec3 cameraDir);

void main() {
    vec3 cameraDir = normalize(uCameraPos - vFragPos);

    if (uMaterial.useDiffuseMap > 0.5) {
        materialAmbient = materialDiffuse = texture2D(uMaterial.diffuseMap, vFragTexCoord).rgb;
    } else {
        materialAmbient = materialDiffuse = uMaterial.ambient;
    }

    vec3 result = vec3(0.0, 0.0, 0.0);
    for (int i = 0; i < NUM_POINT_LIGHTS; i++) {
        if (uPointLights[i].valid > 0.5) {
            result += calcPointLight(uPointLights[i], cameraDir);
        }
    }

    if (uMaterial.shininess < 0.0) gl_FragColor = texture2D(uMaterial.diffuseMap, vFragTexCoord);
    else gl_FragColor = vec4(result, 1.0);
}

vec3 calcPointLight(PointLight light, vec3 cameraDir) {
    vec3 ambient = light.ambientCoeff * materialAmbient * light.color;

    vec3 lightDir = normalize(light.position - vFragPos);
    float diffuseCoeff = clamp(dot(vFragNormal, lightDir), 0.0, 1.0);
    vec3 diffuse = diffuseCoeff * materialDiffuse * light.color;

    vec3 reflectDir = reflect(-lightDir, vFragNormal);
    float specularCoeff = pow(clamp(dot(cameraDir, reflectDir), 0.0, 1.0), uMaterial.shininess);
    vec3 specular = specularCoeff * uMaterial.specular * light.color;

    return ambient + diffuse + specular;
}
