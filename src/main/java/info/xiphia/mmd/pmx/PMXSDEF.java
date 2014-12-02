package info.xiphia.mmd.pmx;

import info.xiphia.mmd.Vector3;

/**
 * Created by xiphia on 2014/11/02.
 */
public class PMXSDEF implements PMXWeightTransformMethod {
    public static final byte WEIGHT_TRANSFORM_METHOD = 3;
    private Number bone1Index;
    private Number bone2Index;
    private float bone1Weight;
    private float bone2Weight;
    private Vector3 sdef_c;
    private Vector3 sdef_r0;
    private Vector3 sdef_r1;

    public PMXSDEF(Number b1index, Number b2index, float b1weight, Vector3 sdefc, Vector3 sdefr0, Vector3 sdefr1) {
        bone1Index = b1index;
        bone2Index = b2index;
        bone1Weight = b1weight;
        bone2Weight = 1 - b1weight;
        sdef_c = sdefc;
        sdef_r0 = sdefr0;
        sdef_r1 = sdefr1;
    }

    public Number getBone1Index() {
        return bone1Index;
    }

    public Number getBone2Index() {
        return bone2Index;
    }

    public float getBone1Weight() {
        return bone1Weight;
    }

    public float getBone2Weight() {
        return bone2Weight;
    }

    public Vector3 getSDEF_C() {
        return sdef_c;
    }

    public Vector3 getSDEF_R0() {
        return sdef_r0;
    }

    public Vector3 getSDEF_R1() {
        return sdef_r1;
    }

    @Override
    public byte getWeightTransformMethod() {
        return WEIGHT_TRANSFORM_METHOD;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Type: SDEF\n");
        builder.append("Bone1Index: ").append(bone1Index).append("\n");
        builder.append("Bone2Index: ").append(bone2Index).append("\n");
        builder.append("Bone1Weight: ").append(bone1Weight).append("\n");
        builder.append("Bone2Weight: ").append(bone2Weight).append("\n");
        builder.append("SDEF-C: ").append(sdef_c).append("\n");
        builder.append("SDEF-R0: ").append(sdef_r0).append("\n");
        builder.append("SDEF-R1: ").append(sdef_r1).append("\n");

        return  builder.toString();
    }
}
