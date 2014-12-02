package info.xiphia.mmd.pmx;

import java.nio.file.Path;
import java.util.List;

/**
 * Created by xiphia on 2014/11/01.
 */
public class PMX {
    private Path path;
    private PMXHeader header;
    private PMXModelInformation modelInformation;
    private List<PMXVertex> vertexes;
    private List<PMXSurface> surfaces;
    private List<String> textures;
    private List<PMXMaterial> materials;

    public PMX(Path p, PMXHeader h, PMXModelInformation information, List<PMXVertex> vers, List<PMXSurface> surfs, List<String> texs, List<PMXMaterial> mats) {
        path = p;
        header = h;
        modelInformation = information;
        vertexes = vers;
        surfaces = surfs;
        textures = texs;
        materials = mats;
    }

    public Path getPath() {
        return path;
    }

    public List<PMXVertex> getVertexes() {
        return vertexes;
    }

    public List<PMXSurface> getSurfaces() {
        return surfaces;
    }

    public List<String> getTextures() {
        return textures;
    }

    public List<PMXMaterial> getMaterials() {
        return materials;
    }
}
