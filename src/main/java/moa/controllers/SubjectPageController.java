package moa.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    public VBox subjectListVBox;
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
        System.out.println("call const");
    }
    private void setSize() {
        mySubjectTextBack.setPrefWidth(listViewScroll.getPrefWidth() - infoBack.getPrefWidth());
    }
    public void call(int subjectSize, ArrayList<EclassDAO> eclassDAOArrayList, ArrayList<SmartATDAO> smartATDAOArrayList){
        System.out.println(subjectSize);
        try {
            eclassDAOArrayList = new ArrayList<>();
            smartATDAOArrayList = new ArrayList<>();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/subject_page.fxml"));

            SubjectPageController subjectPageController = new SubjectPageController();
            subjectPageController.setEclassDAOArrayList(eclassDAOArrayList);
            subjectPageController.setSmartATDAOArrayList(smartATDAOArrayList);
            subjectPageController.setSubjectSize(subjectSize);
            loader.setController(subjectPageController);

            Parent root = loader.load();
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
        addSubjectElement();
    }
    public void addSubjectElement() {
        for (int i = 0; i < subjectSize; i++) {
            try {
                System.out.println("addSubject");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/subject_element.fxml"));
                HBox subjectElement = loader.load();
                SubjectElementPageController subjectElementController = loader.getController();
//                subjectElementController.setData(eclassDAOArrayList.get(i), smartATDAOArrayList.get(i));
                subjectElementController.setData(null, null);
                subjectListVBox.getChildren().add(subjectElement);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void setSubjectSize(int n) {
        System.out.println(subjectSize);
        subjectSize = n;
    }

    public void setEclassDAOArrayList(ArrayList<EclassDAO> eclassDAOArrayList) {
        this.eclassDAOArrayList = eclassDAOArrayList;
    }

    public void setSmartATDAOArrayList(ArrayList<SmartATDAO> smartATDAOArrayList) {
        this.smartATDAOArrayList = smartATDAOArrayList;
    }
}
