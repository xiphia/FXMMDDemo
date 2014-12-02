package info.xiphia.mmd.demo;

import info.xiphia.mmd.pmx.*;
import info.xiphia.mmd.pmx.util.PMX2FX3D;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by xiphia on 2014/11/01.
 */
public class AppController implements Initializable {
    private Stage primaryStage;
    private PMX model;
    private PerspectiveCamera camera;
    private SubScene subScene;
    private Group subSceneRoot;
    private PointLight pointLight;
    private Sphere lightIndicator;
    private AmbientLight ambientLight;

    @FXML
    AnchorPane anchor;
    @FXML
    Slider degXSlider;
    @FXML
    Slider lightXSlider;
    @FXML
    Slider lightYSlider;
    @FXML
    Slider lightZSlider;
    @FXML
    ListView<String> vertexList;
    @FXML
    ListView<String> faceList;
    @FXML
    ListView<String> textureList;
    @FXML
    ListView<String> materialList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        subSceneRoot = new Group();
        subScene = new SubScene(subSceneRoot, 0, 0, true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.WHITE);
        camera = new PerspectiveCamera(true);
        camera.getTransforms().addAll(new Translate(0, 10, -50));
        camera.setFieldOfView(25.0);
        lightIndicator = new Sphere(0.1);
        lightIndicator.setMaterial(new PhongMaterial(Color.RED));
        ambientLight = new AmbientLight(new Color(1.0, 1.0, 1.0, 1.0));
        pointLight = new PointLight(new Color(0.25, 0.25, 0.25, 1.0));
        subScene.setCamera(camera);
        anchor.getChildren().add(subScene);
        subScene.widthProperty().bind(anchor.widthProperty());
        subScene.heightProperty().bind(anchor.heightProperty());
        degXSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                camera.setRotationAxis(Rotate.Y_AXIS);
                camera.setRotate(number2.doubleValue());
            }
        });
        pointLight.translateXProperty().bind(lightXSlider.valueProperty());
        pointLight.translateYProperty().bind(lightYSlider.valueProperty());
        pointLight.translateZProperty().bind(lightZSlider.valueProperty());
        lightIndicator.translateXProperty().bind(lightXSlider.valueProperty());
        lightIndicator.translateYProperty().bind(lightYSlider.valueProperty());
        lightIndicator.translateZProperty().bind(lightZSlider.valueProperty());
    }

    public void loadPMX(ActionEvent e) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open PMX");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MMD Model(PMX)", "*.pmx"));
        File pmxFile = chooser.showOpenDialog(primaryStage);
        if(pmxFile != null) {
            model = PMXLoader.load(pmxFile);
            List<PMXVertex> vertices = model.getVertexes();
            vertexList.getItems().clear();
            for(PMXVertex v: vertices) {
                vertexList.getItems().add(v.getPosition().toString());
            }
            List<PMXSurface> faces = model.getSurfaces();
            faceList.getItems().clear();
            for(PMXSurface f: faces) {
                faceList.getItems().add(f.toString());
            }
            List<String> textures = model.getTextures();
            textureList.getItems().clear();
            textureList.getItems().addAll(textures);
            List<PMXMaterial> materials = model.getMaterials();
            for(PMXMaterial m: materials) {
                materialList.getItems().add(m.getMaterialName());
            }
            MeshView[] meshViews = PMX2FX3D.convert(model);
            subSceneRoot.getChildren().clear();
            subSceneRoot.getChildren().addAll(pointLight);
            subSceneRoot.getChildren().add(ambientLight);
            subSceneRoot.getChildren().addAll(meshViews);
        }
    }

    public void setParent(Stage stage) {
        primaryStage = stage;
    }
}
