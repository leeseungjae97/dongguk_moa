package moa.dao;

import org.openqa.selenium.WebElement;

public class EclassLectureDAO {
    String startLectureBtnName;
    WebElement prevLecture;
    String date;

    public EclassLectureDAO(String startLectureBtnName, WebElement prevLecture, String date) {
        this.startLectureBtnName = startLectureBtnName;
        this.prevLecture = prevLecture;
        this.date = date;
    }

    @Override
    public String toString() {
        return "EclassDAO{" +
                "startLectureBtn=" + startLectureBtnName +
                ", prevLecture=" + prevLecture +
                ", date='" + date + '\'' +
                '}';
    }

    public String getStartLectureBtnName() {
        return startLectureBtnName;
    }

    public void setStartLectureBtnName(String startLectureBtnName) {
        this.startLectureBtnName = startLectureBtnName;
    }

    public WebElement getPrevLecture() {
        return prevLecture;
    }

    public void setPrevLecture(WebElement prevLecture) {
        this.prevLecture = prevLecture;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
