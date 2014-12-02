package info.xiphia.mmd.pmx;

/**
 * Created by xiphia on 2014/11/02.
 */
public class PMXBDEF1 implements PMXWeightTransformMethod {
    public static final byte WEIGHT_TRANSFORM_METHOD = 0;
    private Number bone1Index;

    public PMXBDEF1(Number b1index) {
        bone1Index = b1index;
    }

    public Number getBone1Index() {
        return bone1Index;
    }

    @Override
    public byte getWeightTransformMethod() {
        return WEIGHT_TRANSFORM_METHOD;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Type: BDEF1\n");
        builder.append("Bone1Index: ").append(bone1Index).append("\n");

        return  builder.toString();
    }
}
