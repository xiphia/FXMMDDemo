package info.xiphia.mmd.pmx;

import info.xiphia.mmd.Vector2;
import info.xiphia.mmd.Vector3;
import info.xiphia.mmd.Vector4;

/**
 * Created by xiphia on 2014/11/02.
 */
public class PMXVertex {
    private Vector3 position;
    private Vector3 normal;
    private Vector2 uv;
    private Vector4[] additionalUV;
    private PMXWeightTransformMethod weightTransformMethod;
    private float edgeScale;

    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getNormal() {
        return normal;
    }

    public Vector2 getUv() {
        return uv;
    }

    public Vector4[] getAdditionalUV() {
        return additionalUV;
    }

    public PMXWeightTransformMethod getWeightTransformMethod() {
        return weightTransformMethod;
    }

    public float getEdgeScale() {
        return edgeScale;
    }

    public PMXVertex(Vector3 pos, Vector3 nv, Vector2 u, Vector4[] additional, PMXWeightTransformMethod wtm, float eScale) {
        position = pos;
        normal = nv;
        uv = u;
        if(additional == null) {
            additionalUV = null;
        } else if(additional.length > 0 && additional.length <= 4) {
            additionalUV = additional;
        } else {
            throw  new IllegalArgumentException();
        }
        weightTransformMethod = wtm;
        edgeScale = eScale;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Position: ").append(position).append("\n");
        builder.append("Norma Vec.: ").append(normal).append("\n");
        builder.append("UV: ").append(uv).append("\n");
        if(additionalUV != null) {
            for(int i = 0; i < additionalUV.length; i++) {
                builder.append("Additional UV ").append(i).append(": ").append(additionalUV[i]).append("\n");
            }
        }
        builder.append(weightTransformMethod);
        builder.append("Edge Scale: ").append(edgeScale).append("\n");
        return builder.toString();
    }
}
