package info.xiphia.mmd.pmx.util;

import info.xiphia.mmd.TGALoader;
import info.xiphia.mmd.pmx.PMX;
import info.xiphia.mmd.pmx.PMXMaterial;
import info.xiphia.mmd.pmx.PMXSurface;
import info.xiphia.mmd.pmx.PMXVertex;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by xiphia on 2014/11/03.
 */
public class PMX2FX3D {
    public static MeshView[] convert(PMX pmx) {
        int materialsNum = pmx.getMaterials().size();
        int surfaceCount = 0;
        MeshView[] meshes = new MeshView[materialsNum];

        for(int i = 0; i < materialsNum; i++) {
            PMXMaterial material = pmx.getMaterials().get(i);
            Image texture = null;
            if(material.getNormalTextureIndex().intValue() != -1) {
                if(pmx.getTextures().get(material.getNormalTextureIndex().intValue()).endsWith(".tga")) {
                    texture = TGALoader.load(new File(Paths.get(pmx.getPath().getParent().toString(), pmx.getTextures().get(material.getNormalTextureIndex().intValue())).toString()));
                } else {
                    texture = new Image(Paths.get(pmx.getPath().getParent().toString(), pmx.getTextures().get(material.getNormalTextureIndex().intValue())).toUri().toString());
                }
            }
            meshes[i] = new MeshView();
            int vertexesNum = pmx.getVertexes().size();
            int surfacesNum = pmx.getMaterials().get(i).getSurfaces() / 3;
            TriangleMesh mesh = new TriangleMesh();
            for(int j = 0; j < vertexesNum; j++) {
                PMXVertex v = pmx.getVertexes().get(j);
                mesh.getPoints().addAll(-v.getPosition().getX(), v.getPosition().getY(), v.getPosition().getZ());
                mesh.getTexCoords().addAll(v.getUv().getX(), v.getUv().getY());
            }
            for(int j = surfaceCount; j < surfaceCount + surfacesNum; j++) {
                PMXSurface s = pmx.getSurfaces().get(j);
                mesh.getFaces().addAll(s.getZ().intValue(), s.getZ().intValue(), s.getY().intValue(), s.getY().intValue(), s.getX().intValue(), s.getX().intValue());
                mesh.getFaceSmoothingGroups().addAll(16);
            }
            meshes[i].setMesh(mesh);
            meshes[i].setCullFace(CullFace.NONE);
            meshes[i].setRotationAxis(Rotate.Z_AXIS);
            meshes[i].setRotate(180);
            Color diffuseColor = new Color(material.getDiffuseVector().getX(), material.getDiffuseVector().getY(), material.getDiffuseVector().getZ(), material.getDiffuseVector().getW());
            Color specularColor = new Color(material.getSpecularVector().getX(), material.getSpecularVector().getY(), material.getSpecularVector().getZ(), 1);
            PhongMaterial phongMaterial = new PhongMaterial(diffuseColor, texture, null, null, null);
            //PhongMaterial phongMaterial = new PhongMaterial(Color.AQUAMARINE);
            phongMaterial.setSpecularColor(specularColor);
            phongMaterial.setSpecularPower(material.getSpecularScale());

            meshes[i].setMaterial(phongMaterial);
            surfaceCount += surfacesNum;
        }

        return meshes;
    }
}
