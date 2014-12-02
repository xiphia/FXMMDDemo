package info.xiphia.mmd.demo;/**
 * Created by xiphia on 2014/11/01.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getClass().getResource("/main.fxml"));
        Parent root = loader.load();
        ((AppController)loader.getController()).setParent(primaryStage);
        primaryStage.setTitle("JavaFX MMD Demo");
        primaryStage.setScene(new Scene(root, 960, 540));
        primaryStage.show();
    }
}
