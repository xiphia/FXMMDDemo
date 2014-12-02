package info.xiphia.mmd.pmx;

/**
 * Created by xiphia on 2014/11/01.
 */
public class PMXHeader {
    private float versionNumber;
    private byte encodingMethod;
    private byte additionalUV;
    private byte vertexIndexSize;
    private byte textureIndexSize;
    private byte materialIndexSize;
    private byte boneIndexSize;
    private byte morphIndexSize;
    private byte rigidBodyIndexSize;

    public PMXHeader(float version, byte[] data) {
        versionNumber = version;
        if(data.length != 8) {
            throw new IllegalArgumentException();
        }
        encodingMethod = data[0];
        additionalUV = data[1];
        vertexIndexSize = data[2];
        textureIndexSize = data[3];
        materialIndexSize = data[4];
        boneIndexSize = data[5];
        morphIndexSize = data[6];
        rigidBodyIndexSize = data[7];
    }

    public float getVersionNumber() {
        return versionNumber;
    }

    public byte getEncodingMethod() {
        return encodingMethod;
    }

    public String getEncodingMethodAsString() {
        switch(encodingMethod) {
            case 0:
                return "UTF-16LE";
            case 1:
                return "UTF-8";
            default:
                return "Unknown";
        }
    }

    public byte getAdditionalUV() {
        return additionalUV;
    }

    public byte getVertexIndexSize() {
        return vertexIndexSize;
    }

    public byte getTextureIndexSize() {
        return textureIndexSize;
    }

    public byte getMaterialIndexSize() {
        return materialIndexSize;
    }

    public byte getBoneIndexSize() {
        return boneIndexSize;
    }

    public byte getMorphIndexSize() {
        return morphIndexSize;
    }

    public byte getRigidBodyIndexSize() {
        return rigidBodyIndexSize;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PMXVersion: ").append(versionNumber).append("\n");
        builder.append("Encoding Method: ");
        switch(encodingMethod) {
            case 0:
                builder.append("UTF-16LE");
                break;
            case 1:
                builder.append("UTF-8");
                break;
            default:
                builder.append("Unknown");
        }
        builder.append("[").append(encodingMethod).append("]").append("\n");
        builder.append("Additional UV: ").append(additionalUV).append("\n");
        builder.append("Vertex Index Size: ").append(vertexIndexSize).append("\n");
        builder.append("Texture Index Size: ").append(textureIndexSize).append("\n");
        builder.append("Material Index Size: ").append(materialIndexSize).append("\n");
        builder.append("Bone Index Size: ").append(boneIndexSize).append("\n");
        builder.append("Morph Index Size: ").append(morphIndexSize).append("\n");
        builder.append("Rigid-Body Index Size: ").append(rigidBodyIndexSize).append("\n");

        return builder.toString();
    }
}
