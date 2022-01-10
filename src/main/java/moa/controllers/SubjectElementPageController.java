package moa.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import moa.dao.EclassDAO;
import moa.dao.SmartATDAO;

import java.util.ArrayList;

public class SubjectElementPageController {
    public VBox currentLectureInfoBox;
    public Label lectureName;
    public Label lectureWeek;
    public Label green;
    public Label red;
    public Label blue;
    public Label gray1;
    public Label gray2;
    public Label startLectureText;
    public Label date;
    @FXML
    void initialize() {
        System.out.println("CALL");
    }

    public void setData(EclassDAO eclassDAO, SmartATDAO smartATDAO) {
        System.out.println("SET DATA");
//        lectureName.setText(eclassDAO.getSubjectName());
//        lectureWeek.setText(eclassDAO.getWeeks().get(eclassDAO.getWeeks().size()));
//
//        startLectureText.setText("회상강의 시작");
//        date.setText(eclassDAO.getEclassLectureDAOArrayList().get(eclassDAO.getEclassLectureDAOArrayList().size()).getDate());
//
//        green.setText("출석\n" + smartATDAO.getGreen() + "회");
//        red.setText("결석\n" + smartATDAO.getRed() + "회");
//        blue.setText("지각\n" + smartATDAO.getBlue() + "회");
//        gray1.setText("출석인정\n" + smartATDAO.getGray1() + "회");
//        gray2.setText("조퇴(이탈)\n" + smartATDAO.getGray2() + "회");

        lectureName.setText("과제중심문제해결");
        lectureWeek.setText("3주차");

        startLectureText.setText("회상강의 시작");
        date.setText("2021-01-10");

        green.setText("출석\n9회");
        red.setText("결석\n9회");
        blue.setText("지각\n9회");
        gray1.setText("출석인정\n9회");
        gray2.setText("조퇴(이탈)\n9회");
    }

}
