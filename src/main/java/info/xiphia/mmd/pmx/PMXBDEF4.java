package info.xiphia.mmd.pmx;

/**
 * Created by xiphia on 2014/11/02.
 */
public class PMXBDEF4 implements PMXWeightTransformMethod {
    public static final byte WEIGHT_TRANSFORM_METHOD = 2;
    private Number bone1Index;
    private Number bone2Index;
    private Number bone3Index;
    private Number bone4Index;
    private float bone1Weight;
    private float bone2Weight;
    private float bone3Weight;
    private float bone4Weight;

    public PMXBDEF4(Number b1index, Number b2index, Number b3index, Number b4index, float b1weight, float b2weight, float b3weight, float b4weight) {
        bone1Index = b1index;
        bone2Index = b2index;
        bone3Index = b3index;
        bone4Index = b4index;
        bone1Weight = b1weight;
        bone2Weight = b2weight;
        bone3Weight = b3weight;
        bone4Weight = b4weight;
    }

    public Number getBone1Index() {
        return bone1Index;
    }

    public Number getBone2Index() {
        return bone2Index;
    }

    public Number getBone3Index() {
        return bone3Index;
    }


    public Number getBone4Index() {
        return bone4Index;
    }

    public float getBone1Weight() {
        return bone1Weight;
    }

    public float getBone2Weight() {
        return bone2Weight;
    }

    public float getBone3Weight() {
        return bone3Weight;
    }

    public float getBone4Weight() {
        return bone4Weight;
    }

    @Override
    public byte getWeightTransformMethod() {
        return WEIGHT_TRANSFORM_METHOD;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Type: BDEF4\n");
        builder.append("Bone1Index: ").append(bone1Index).append("\n");
        builder.append("Bone2Index: ").append(bone2Index).append("\n");
        builder.append("Bone3Index: ").append(bone3Index).append("\n");
        builder.append("Bone4Index: ").append(bone4Index).append("\n");
        builder.append("Bone1Weight: ").append(bone1Weight).append("\n");
        builder.append("Bone2Weight: ").append(bone2Weight).append("\n");
        builder.append("Bone3Weight: ").append(bone3Weight).append("\n");
        builder.append("Bone4Weight: ").append(bone4Weight).append("\n");

        return  builder.toString();
    }
}
