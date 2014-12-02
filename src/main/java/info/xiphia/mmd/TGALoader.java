package info.xiphia.mmd;

import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by xiphia on 2014/11/03.
 */
public class TGALoader {
    public static Image load(File file) {
        return parse(file);
    }

    public static Image parse(File file) {
        try {
            DataInputStream dis = new DataInputStream(new FileInputStream(file));

            byte idLength = readByte(dis);
            byte colorMap = readByte(dis);
            byte dataTypeCode = readByte(dis);
            short colorMapOrigin = readShort(dis);
            short colorMapLength = readShort(dis);
            byte colorMapDepth = readByte(dis);
            short xOrigin = readShort(dis);
            short yOrigin = readShort(dis);
            short width = readShort(dis);
            short height = readShort(dis);
            byte bitsPerPixel = readByte(dis);
            byte imageDescriptor = readByte(dis);
            String imageType;

            int[] data;

            if(bitsPerPixel == 32) {
                //return null;
                data = new int[width * height * 4];
            } else {
                data = new int[width * height * 3];
            }

            if(idLength != 0) {
                dis.skipBytes(idLength);
            }
            if(colorMap != 0) {
                dis.skipBytes(colorMapDepth * colorMapLength * 3);
            }

            WritableImage image = new WritableImage(width, height);
            if(dataTypeCode == 2) {
                if(bitsPerPixel == 24) {
                    for (int i = 0; i < width * height * 3;) {
                        int b = readByte(dis) & 0xFF;
                        int g = readByte(dis) & 0xFF;
                        int r = readByte(dis) & 0xFF;
                        data[i++] = r;
                        data[i++] = g;
                        data[i++] = b;                    }
                } else if(bitsPerPixel == 32) {
                    for (int i = 0; i < width * height * 4;) {
                        int b = readByte(dis) & 0xFF;
                        int g = readByte(dis) & 0xFF;
                        int r = readByte(dis) & 0xFF;
                        int a = readByte(dis) & 0xFF;
                        data[i++] = a;
                        data[i++] = r;
                        data[i++] = g;
                        data[i++] = b;
                    }
                }
            } else if(dataTypeCode == 10) {
                if(bitsPerPixel == 24) {
                    for (int i = 0; i < width * height * 3;) {
                        byte d = readByte(dis);
                        if(((d >> 7) & 0x01) == 1) {
                            int b = readByte(dis) & 0xFF;
                            int g = readByte(dis) & 0xFF;
                            int r = readByte(dis) & 0xFF;
                            for(int j = 0; j < (d & 0x7F) + 1; j++) {
                                data[i++] = r;
                                data[i++] = g;
                                data[i++] = b;
                            }
                        } else {
                            for(int j = 0; j < (d & 0x7F) + 1; j++) {
                                int b = readByte(dis) & 0xFF;
                                int g = readByte(dis) & 0xFF;
                                int r = readByte(dis) & 0xFF;
                                data[i++] = r;
                                data[i++] = g;
                                data[i++] = b;
                            }
                        }
                    }
                } else if(bitsPerPixel == 32) {
                    for (int i = 0; i < width * height * 4;) {
                        byte d = readByte(dis);
                        if(((d >> 7) & 0x01) == 1) {
                            int b = readByte(dis) & 0xFF;
                            int g = readByte(dis) & 0xFF;
                            int r = readByte(dis) & 0xFF;
                            int a = readByte(dis) & 0xFF;
                            for(int j = 0; j < (d & 0x7F) + 1; j++) {
                                data[i++] = a;
                                data[i++] = r;
                                data[i++] = g;
                                data[i++] = b;
                            }
                        } else {
                            for(int j = 0; j < (d & 0x7F) + 1; j++) {
                                int b = readByte(dis) & 0xFF;
                                int g = readByte(dis) & 0xFF;
                                int r = readByte(dis) & 0xFF;
                                int a = readByte(dis) & 0xFF;
                                data[i++] = a;
                                data[i++] = r;
                                data[i++] = g;
                                data[i++] = b;
                            }
                        }
                    }
                }
            }

            PixelWriter writer = image.getPixelWriter();
            if(bitsPerPixel == 24) {
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        writer.setColor(x, height - y - 1, Color.rgb(data[y * width * 3 + x * 3], data[y * width * 3 + x * 3 + 1], data[y * width * 3 + x * 3 + 2]));
                    }
                }
            } else if(bitsPerPixel == 32) {
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        writer.setColor(x, height - y - 1, Color.rgb(data[y * width * 4 + x * 4 + 1], data[y * width * 4 + x * 4 + 2], data[y * width * 4 + x * 4 + 3], (data[y * width * 4 + x * 4] / 255.0)));
                    }
                }
            }
            dis.close();

            return image;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String dataTypeCodeToString(byte c) {
        String imageType;
        switch(c) {
            case 0:
                imageType = "No Image Data";
                break;
            case 1:
                imageType = "Uncompressed Color-Mapped Image";
                break;
            case 2:
                imageType = "Uncompressed RGB Image";
                break;
            case 3:
                imageType = "Uncompressed Black-White Image";
                break;
            case 9:
                imageType = "RLE Color-Mapped Image";
                break;
            case 10:
                imageType = "RLE RGB Image";
                break;
            case 11:
                imageType = "RLE Black-White Image";
                break;
            default:
                imageType = "Unknown Image";
        }
        return imageType;
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

    private static float readFloat(DataInputStream dis) throws IOException {
        return ByteBuffer.allocate(4).putFloat(dis.readFloat()).order(ByteOrder.LITTLE_ENDIAN).getFloat(0);
    }

    private static double readDouble(DataInputStream dis) throws IOException {
        return ByteBuffer.allocate(8).putDouble(dis.readDouble()).order(ByteOrder.LITTLE_ENDIAN).getDouble(0);
    }
}
