package info.xiphia.mmd.pmx;

/**
 * Created by xiphia on 2014/11/02.
 */
public class PMXModelInformation {
    private String modelName;
    private String modelNameEn;
    private String modelComment;
    private String modelCommentEn;

    public PMXModelInformation(String name, String nameEn, String comment, String commentEn) {
        modelName = name;
        modelNameEn = nameEn;
        modelComment = comment;
        modelCommentEn = commentEn;
    }

    public String getModelName() {
        return modelName;
    }

    public String getModelNameEn() {
        return modelNameEn;
    }

    public String getModelComment() {
        return modelComment;
    }

    public String getModelCommentEn() {
        return modelCommentEn;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Model Name: ").append(modelName).append("\n");
        builder.append("Model Name(English): ").append(modelNameEn).append("\n");
        builder.append("Comment: \n").append(modelComment).append("\n");
        builder.append("Comment(English): \n").append(modelCommentEn).append("\n");

        return builder.toString();
    }
}
