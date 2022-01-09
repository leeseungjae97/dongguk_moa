package moa.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import moa.Main;
import moa.util.CustomTopBar;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OkBtnPopupController implements Initializable {
    public Label popupContent;
    public Button okay;
    public BorderPane popupBase;

    private String content;

    public OkBtnPopupController() {
        super();
    }
    public OkBtnPopupController(String content) {
        super();
        this.content = content;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) { }
    public void call(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ok_btn_popup.fxml"));
            Parent root = loader.load();
            OkBtnPopupController okBtnPopupController = loader.getController();
            okBtnPopupController.configureBottom();

            Scene scene = new Scene(root);
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void configureBottom() {
        if(content == null || content.equals("")) {
            popupContent.setText("아이디 혹은 비밀번호가\n다릅니다.");
        }
        okay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) popupContent.getScene().getWindow();
                stage.close();
            }
        });
    }
}
