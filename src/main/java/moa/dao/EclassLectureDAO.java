package moa.dao;

import org.openqa.selenium.WebElement;

public class EclassLectureDAO {
    WebElement startLectureBtn;
    WebElement prevLecture;
    String date;

    public EclassLectureDAO(WebElement startLectureBtn, WebElement prevLecture, String date) {
        this.startLectureBtn = startLectureBtn;
        this.prevLecture = prevLecture;
        this.date = date;
    }

    @Override
    public String toString() {
        return "EclassDAO{" +
                "startLectureBtn=" + startLectureBtn +
                ", prevLecture=" + prevLecture +
                ", date='" + date + '\'' +
                '}';
    }

    public WebElement getStartLectureBtn() {
        return startLectureBtn;
    }

    public void setStartLectureBtn(WebElement startLectureBtn) {
        this.startLectureBtn = startLectureBtn;
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
