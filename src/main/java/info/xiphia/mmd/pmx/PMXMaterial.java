package info.xiphia.mmd.pmx;

import info.xiphia.mmd.Vector3;
import info.xiphia.mmd.Vector4;

/**
 * Created by xiphia on 2014/11/03.
 */
public class PMXMaterial {
    private String materialName;
    private String materialNameEn;
    private Vector4 diffuseVector;
    private Vector3 specularVector;
    private float specularScale;
    private Vector3 ambientVector;
    private boolean drawBothSurface;
    private boolean drawGroundShadow;
    private boolean drawToSelfShadowMap;
    private boolean drawToSelfShadow;
    private boolean drawEdge;
    private Vector4 edgeColorVector;
    private float edgeSize;
    private Number normalTextureIndex;
    private Number sphereTextureIndex;
    private byte sphereMode;
    private byte sharedToonFlag;
    private Number toonTextureIndex;
    private String memo;
    private int surfaces;

    public PMXMaterial(String name, String nameEn, Vector4 diffuse, Vector3 specular, float sScale, Vector3 ambient, byte drawFlags, Vector4 edgeColor, float eSize, Number normTextureIndex, Number spTextureIndex, byte spMode, byte sToonFlag, Number tTextureIndex, String m, int surfs) {
        materialName = name;
        materialNameEn = nameEn;
        diffuseVector = diffuse;
        specularVector = specular;
        specularScale = sScale;
        ambientVector = ambient;
        drawBothSurface = (drawFlags & 0x01) == 1;
        drawGroundShadow = ((drawFlags >> 1) & 0x01) == 1;
        drawToSelfShadowMap = ((drawFlags >> 3) & 0x01) == 1;
        drawToSelfShadow = ((drawFlags >> 7) & 0x01) == 1;
        drawEdge = ((drawFlags >> 9) & 0x01) == 1;
        edgeColorVector = edgeColor;
        edgeSize = eSize;
        normalTextureIndex = normTextureIndex;
        sphereTextureIndex = spTextureIndex;
        sphereMode = spMode;
        sharedToonFlag = sToonFlag;
        toonTextureIndex = tTextureIndex;
        memo = m;
        surfaces = surfs;
    }

    public String getMaterialName() {
        return materialName;
    }

    public String getMaterialNameEn() {
        return materialNameEn;
    }

    public Vector4 getDiffuseVector() {
        return diffuseVector;
    }

    public Vector3 getSpecularVector() {
        return specularVector;
    }

    public float getSpecularScale() {
        return specularScale;
    }

    public Vector3 getAmbientVector() {
        return ambientVector;
    }

    public boolean isDrawBothSurface() {
        return drawBothSurface;
    }

    public boolean isDrawGroundShadow() {
        return drawGroundShadow;
    }

    public boolean isDrawToSelfShadowMap() {
        return drawToSelfShadowMap;
    }

    public boolean isDrawToSelfShadow() {
        return drawToSelfShadow;
    }

    public boolean isDrawEdge() {
        return drawEdge;
    }

    public Vector4 getEdgeColorVector() {
        return edgeColorVector;
    }

    public float getEdgeSize() {
        return edgeSize;
    }

    public Number getNormalTextureIndex() {
        return normalTextureIndex;
    }

    public Number getSphereTextureIndex() {
        return sphereTextureIndex;
    }

    public byte getSphereMode() {
        return sphereMode;
    }

    public byte getSharedToonFlag() {
        return sharedToonFlag;
    }

    public Number getToonTextureIndex() {
        return toonTextureIndex;
    }

    public String getMemo() {
        return memo;
    }

    public int getSurfaces() {
        return surfaces;
    }
}
