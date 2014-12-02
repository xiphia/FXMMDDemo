package info.xiphia.mmd.pmx;

/**
 * Created by xiphia on 2014/11/02.
 */
public class PMXBDEF2 implements PMXWeightTransformMethod {
    public static final byte WEIGHT_TRANSFORM_METHOD = 1;
    private Number bone1Index;
    private Number bone2Index;
    private float bone1Weight;
    private float bone2Weight;

    public PMXBDEF2(Number b1index, Number b2index, float b1weight) {
        bone1Index = b1index;
        bone2Index = b2index;
        bone1Weight = b1weight;
        bone2Weight = 1 - b1weight;
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

    @Override
    public byte getWeightTransformMethod() {
        return WEIGHT_TRANSFORM_METHOD;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Type: BDEF2\n");
        builder.append("Bone1Index: ").append(bone1Index).append("\n");
        builder.append("Bone2Index: ").append(bone2Index).append("\n");
        builder.append("Bone1Weight: ").append(bone1Weight).append("\n");
        builder.append("Bone2Weight: ").append(bone2Weight).append("\n");

        return  builder.toString();
    }
}
