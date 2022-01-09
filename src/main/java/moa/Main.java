package moa;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    public static Stage pStage;
    @Override
    public void init() throws Exception {
        super.init();
        System.setProperty("prism.lcdtext", "false");
        System.setProperty("file.encoding","UTF-8");
        //Font.loadFont(getClass().getResourceAsStream("/fonts/segoe-ui-bold.ttf"), 10);
        Font.loadFont(getClass().getResourceAsStream("/fonts/segoe-ui-bold.woff"), 10);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        pStage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Parent root = loader.load();

        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root, 340, 280));
        primaryStage.setTitle("Dongguk MOA");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    public static Stage getPrimaryStage() {return pStage;}


    public static void main(String[] args) {
        launch(args);
    }
}
