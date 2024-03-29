package moa.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
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
    public Label name;
    public Label num;

    public HBox mySubjectTextBack;
    public VBox infoBack;

    private ArrayList<EclassDAO> eclassDAOArrayList;
    private ArrayList<SmartATDAO> smartATDAOArrayList;
    private int subjectSize;
    private String numStr;
    private String nameStr;

    public SubjectPageController() { }
    private void setSize() {
        mySubjectTextBack.setPrefWidth(listViewScroll.getPrefWidth() - infoBack.getPrefWidth());
    }
    public void call(int subjectSize, ArrayList<EclassDAO> eclassDAOArrayList, ArrayList<SmartATDAO> smartATDAOArrayList
                ,String num, String name){
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/subject_page.fxml"));

            SubjectPageController subjectPageController = new SubjectPageController();
            subjectPageController.setEclassDAOArrayList(eclassDAOArrayList);
            subjectPageController.setSmartATDAOArrayList(smartATDAOArrayList);
            subjectPageController.setSubjectSize(subjectSize);
            subjectPageController.setMyInfo(num, name);

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
        setLabelData();

        try {
            addSubjectElement();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void setLabelData() {
        num.setText(numStr);
        name.setText(nameStr);
    }

    private void addSubjectElement() throws IOException {
        System.out.println(eclassDAOArrayList.size() + " : " + smartATDAOArrayList.size());
        if(smartATDAOArrayList.size() == 0 || eclassDAOArrayList.size() == 0) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/no_subject_day.fxml"));
            VBox subjectElement = loader.load();
            subjectListVBox.getChildren().add(subjectElement);
            subjectListVBox.setAlignment(Pos.CENTER);

        }else {
            for (int i = 0; i < subjectSize; i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/subject_element.fxml"));
                VBox subjectElement = loader.load();
                SubjectElementPageController subjectElementController = loader.getController();
                subjectElementController.setData(eclassDAOArrayList.get(i), smartATDAOArrayList.get(i));
                subjectListVBox.getChildren().add(subjectElement);
            }
        }

//test code
//        for (int i = 0; i < subjectSize; i++) {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/subject_element.fxml"));
//            VBox subjectElement = loader.load();
//            SubjectElementPageController subjectElementController = loader.getController();
//            subjectElementController.setData(eclassDAOArrayList.get(i), null);
//            subjectListVBox.getChildren().add(subjectElement);
//        }
    }
    public void setMyInfo(String num, String name) {
        numStr = num;
        nameStr = name;
    }

    public void setSubjectSize(int n) {
        subjectSize = n;
    }

    public void setEclassDAOArrayList(ArrayList<EclassDAO> eclassDAOArrayList) {
        this.eclassDAOArrayList = eclassDAOArrayList;
    }

    public void setSmartATDAOArrayList(ArrayList<SmartATDAO> smartATDAOArrayList) {
        this.smartATDAOArrayList = smartATDAOArrayList;
    }
}
