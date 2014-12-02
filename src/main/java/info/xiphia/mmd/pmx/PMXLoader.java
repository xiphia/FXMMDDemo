package info.xiphia.mmd.pmx;

import info.xiphia.mmd.Vector2;
import info.xiphia.mmd.Vector3;
import info.xiphia.mmd.Vector4;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by xiphia on 2014/11/01.
 */
public class PMXLoader {
    public static PMX load(File file) {
        return parse(file);
    }

    public static PMX parse(File file) {
        try {
            DataInputStream dis = new DataInputStream(new FileInputStream(file));
            PMXHeader header;
            PMXModelInformation modelInformation;
            int[] wtmCount = new int[4];

            byte[] magicString = new byte[4];
            if(dis.read(magicString, 0, 4) == 4) {
                if(!new String(magicString, "US-ASCII").equals("PMX ")) {
                    System.out.println("This is not PMX file.");
                    dis.close();
                    return null;
                }
                float versionNumber = ByteBuffer.allocate(4).putFloat(dis.readFloat()).order(ByteOrder.LITTLE_ENDIAN).getFloat(0);

                byte headerDataSize = dis.readByte();
                if(versionNumber >= 2.0) {
                    byte[] headerData = new byte[8];
                    if(dis.read(headerData, 0, 8) == 8) {
                        header = new PMXHeader(versionNumber, headerData);

                        String modelName = readTextBuf(dis, header.getEncodingMethodAsString());
                        String modelNameEn = readTextBuf(dis, header.getEncodingMethodAsString());
                        String comment = readTextBuf(dis, header.getEncodingMethodAsString());
                        String commentEn = readTextBuf(dis, header.getEncodingMethodAsString());
                        modelInformation = new PMXModelInformation(modelName, modelNameEn, comment, commentEn);

                        int vertexesNum = readInt(dis);
                        ArrayList<PMXVertex> vertexes = new ArrayList<PMXVertex>(vertexesNum);
                        for(int i = 0; i < vertexesNum; i++) {
                            Vector3 pos = new Vector3(readFloat3(dis));
                            Vector3 norm = new Vector3(readFloat3(dis));
                            Vector2 uv = new Vector2(readFloat2(dis));
                            Vector4[] auvs = null;
                            PMXWeightTransformMethod wtm;
                            if(header.getAdditionalUV() != 0) {
                                auvs = new Vector4[header.getAdditionalUV()];
                                for(int j = 0; j < header.getAdditionalUV(); i++) {
                                    auvs[i] = new Vector4(readFloat4(dis));
                                }
                            }
                            byte weightTransformMethod = readByte(dis);
                            if(weightTransformMethod == 0) {
                                Number b1index = readNumber(dis, header.getBoneIndexSize());
                                wtm = new PMXBDEF1(b1index);
                            } else if(weightTransformMethod == 1) {
                                Number b1index = readNumber(dis, header.getBoneIndexSize());
                                Number b2index = readNumber(dis, header.getBoneIndexSize());
                                float b1weight = readFloat(dis);
                                wtm = new PMXBDEF2(b1index, b2index, b1weight);
                            } else if(weightTransformMethod == 2) {
                                Number b1index = readNumber(dis, header.getBoneIndexSize());
                                Number b2index = readNumber(dis, header.getBoneIndexSize());
                                Number b3index = readNumber(dis, header.getBoneIndexSize());
                                Number b4index = readNumber(dis, header.getBoneIndexSize());
                                float b1weight = readFloat(dis);
                                float b2weight = readFloat(dis);
                                float b3weight = readFloat(dis);
                                float b4weight = readFloat(dis);
                                wtm = new PMXBDEF4(b1index, b2index, b3index, b4index, b1weight, b2weight, b3weight, b4weight);
                            } else if(weightTransformMethod == 3) {
                                Number b1index = readNumber(dis, header.getBoneIndexSize());
                                Number b2index = readNumber(dis, header.getBoneIndexSize());
                                float b1weight = readFloat(dis);
                                Vector3 sdefc = new Vector3(readFloat3(dis));
                                Vector3 sdefr0 = new Vector3(readFloat3(dis));
                                Vector3 sdefr1 = new Vector3(readFloat3(dis));
                                wtm = new PMXSDEF(b1index, b2index, b1weight, sdefc, sdefr0, sdefr1);
                            } else {
                                System.out.println("invalid WeightTransformMethod: " + weightTransformMethod);
                                dis.close();
                                return null;
                            }
                            float edgeScale = readFloat(dis);
                            vertexes.add(new PMXVertex(pos, norm, uv, auvs, wtm, edgeScale));
                        }

                        int surfacesNum = readInt(dis) / 3;
                        ArrayList<PMXSurface> surfaces = new ArrayList<PMXSurface>(surfacesNum);
                        for(int i = 0; i < surfacesNum; i++) {
                            surfaces.add(new PMXSurface(readSurface(dis, header.getVertexIndexSize())));
                        }

                        int texturesNum = readInt(dis);
                        ArrayList<String> textures = new ArrayList<String>();
                        for(int i = 0; i < texturesNum; i++) {
                            textures.add(readTextBuf(dis, header.getEncodingMethodAsString()));
                        }

                        int materialsNum = readInt(dis);
                        ArrayList<PMXMaterial> materials = new ArrayList<PMXMaterial>();
                        for(int i = 0; i < materialsNum; i++) {
                            String name = readTextBuf(dis, header.getEncodingMethodAsString());
                            String nameEn = readTextBuf(dis, header.getEncodingMethodAsString());
                            Vector4 diffuse = new Vector4(readFloat4(dis));
                            Vector3 specular = new Vector3(readFloat3(dis));
                            float sScale = readFloat(dis);
                            Vector3 ambient = new Vector3(readFloat3(dis));
                            byte drawFlags = readByte(dis);
                            Vector4 edgeColor = new Vector4(readFloat4(dis));
                            float eSize = readFloat(dis);
                            Number normTextureIndex = readNumber(dis, header.getTextureIndexSize());
                            Number spTextureIndex = readNumber(dis, header.getTextureIndexSize());
                            byte spMode = readByte(dis);
                            byte sToonFlag = readByte(dis);
                            Number tTextureIndex;
                            if(sToonFlag == 0) {
                                tTextureIndex = readNumber(dis, header.getTextureIndexSize());
                            } else {
                                tTextureIndex = readNumber(dis, (byte)1);
                            }
                            String m = readTextBuf(dis, header.getEncodingMethodAsString());
                            int surfs = readInt(dis);
                            materials.add(new PMXMaterial(name, nameEn, diffuse, specular, sScale, ambient, drawFlags, edgeColor, eSize, normTextureIndex, spTextureIndex, spMode, sToonFlag, tTextureIndex, m, surfs));
                        }
                        dis.close();
                        return new PMX(file.toPath(), header, modelInformation, vertexes, surfaces, textures, materials);
                    }
                }
            }

            dis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte readByte(DataInputStream dis) throws IOException {
        return dis.readByte();
    }

    private static short readShort(DataInputStream dis) throws IOException {
        return ByteBuffer.allocate(2).putShort(dis.readShort()).order(ByteOrder.LITTLE_ENDIAN).getShort(0);
    }

    private static int readInt(DataInputStream dis) throws IOException {
        return ByteBuffer.allocate(4).putInt(dis.readInt()).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
    }

    private static long readLong(DataInputStream dis) throws IOException {
        return ByteBuffer.allocate(8).putLong(dis.readLong()).order(ByteOrder.LITTLE_ENDIAN).getLong(0);
    }

    private static Number readNumber(DataInputStream dis, byte num) throws IOException {
        switch(num) {
            case 1:
                return readByte(dis);
            case 2:
                return readShort(dis);
            case 4:
                return readInt(dis);
            default:
                return null;
        }
    }

    private static Number[] readSurface(DataInputStream dis, byte num) throws IOException {
        Number[] vertexIndexes = new Number[3];
        for(int i = 0; i < 3; i++) {
            switch(num) {
                case 1:
                    vertexIndexes[i] = readNumber(dis, num).intValue() & 0xFF;
                    break;
                case 2:
                    vertexIndexes[i] = readNumber(dis, num).intValue() & 0xFFFF;
                    break;
                case 4:
                    vertexIndexes[i] = readNumber(dis, num).intValue();
                    break;
                default:
                    return null;
            }
        }
        return vertexIndexes;
    }

    private static float readFloat(DataInputStream dis) throws IOException {
        return ByteBuffer.allocate(4).putFloat(dis.readFloat()).order(ByteOrder.LITTLE_ENDIAN).getFloat(0);
    }

    private static float[] readFloat2(DataInputStream dis) throws IOException {
        FloatBuffer buffer = FloatBuffer.allocate(2);
        for(int i = 0; i < 2; i++) {
            buffer.put(readFloat(dis));
        }
        return buffer.array();
    }

    private static float[] readFloat3(DataInputStream dis) throws IOException {
        FloatBuffer buffer = FloatBuffer.allocate(3);
        for(int i = 0; i < 3; i++) {
            buffer.put(readFloat(dis));
        }
        return buffer.array();
    }

    private static float[] readFloat4(DataInputStream dis) throws IOException {
        FloatBuffer buffer = FloatBuffer.allocate(4);
        for(int i = 0; i < 4; i++) {
            buffer.put(readFloat(dis));
        }
        return buffer.array();
    }

    private static double readDouble(DataInputStream dis) throws IOException {
        return ByteBuffer.allocate(8).putDouble(dis.readDouble()).order(ByteOrder.LITTLE_ENDIAN).getDouble(0);
    }

    private static String readTextBuf(DataInputStream dis, String encoding) throws IOException {
        int textLength = readInt(dis);
        byte textData[] = new byte[textLength];
        if(dis.read(textData, 0, textLength) == textLength) {
            return new String(textData, encoding);
        }
        return null;
    }
}
