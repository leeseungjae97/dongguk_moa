package moa.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import moa.Main;
import moa.dao.EclassDAO;
import moa.dao.SmartATDAO;
import moa.util.CustomTopBar;

import java.io.IOException;
import java.util.ArrayList;

public class SubjectPageController {
    public HBox topBar;
    public Label minimum;
    public Label close;
    public ListView subjectListView;
    public ScrollPane listViewScroll;
    public Label major;
    public Label grade;
    public Label num;
    public HBox mySubjectTextBack;
    public VBox infoBack;
    private ArrayList<EclassDAO> eclassDAOArrayList;
    private ArrayList<SmartATDAO> smartATDAOArrayList;
    private int subjectSize;

    public SubjectPageController() {
        eclassDAOArrayList = new ArrayList<>();
        smartATDAOArrayList = new ArrayList<>();
    }
    private void setSize() {
        mySubjectTextBack.setPrefWidth(listViewScroll.getPrefWidth() - infoBack.getPrefWidth());
        addSubjectElement();
    }
    public void call(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/subject_page.fxml"));
            Parent root = loader.load();
            SubjectPageController subjectPageController = loader.getController();

            Scene scene = new Scene(root);

            Main.getPrimaryStage().setScene(scene);
            Main.getPrimaryStage().show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        CustomTopBar.configurationTopBar(topBar, minimum, close);
        setSize();
    }
    public void addSubjectElement() {
        subjectListView.getItems().clear();
        for (int i = 0; i < subjectSize; i++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/subject_element.fxml"));
                HBox cell = loader.load();
                SubjectElementPageController subjectElementController = loader.getController();
                subjectElementController.setData(eclassDAOArrayList.get(i), smartATDAOArrayList.get(i));


            } catch (IOException e) {
                e.printStackTrace();
            }
            //subjectListView.getItems().add();
        }
    }

    public void setSubjectSize(int n) {subjectSize = n;}
    public void addEclassDAO(EclassDAO eclassDAO) {
        eclassDAOArrayList.add(eclassDAO);
    }
    public void addSmartDAO(SmartATDAO smartATDAO) {
        smartATDAOArrayList.add(smartATDAO);
    }
}
