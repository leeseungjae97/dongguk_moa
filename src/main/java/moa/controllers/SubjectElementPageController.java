package moa.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import moa.dao.EclassDAO;
import moa.dao.EclassLectureDAO;
import moa.dao.SmartATDAO;
import moa.util.StringUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.prefs.Preferences;

import static moa.arguments.StringArgu.*;

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

    private Preferences saveIDPW = Preferences.userRoot();
    private EclassLectureDAO eclassLectureDAO;
    private SmartATDAO smartATDAO;

    private String lectureDateStr;
    
    @FXML
    void initialize() {
    }

    public void setData(EclassDAO eclassDAO, SmartATDAO smartATDAO) {
        eclassLectureDAO = eclassDAO.getEclassLectureDAOArrayList().get(eclassDAO.getEclassLectureDAOArrayList().size()-1);
        lectureName.setText(eclassDAO.getSubjectName());
        //3주차학습목표보기\n2022-01-03 ~ 2022-01-09 -> 3주차
        //week은 한 subject당 최대 3개.
        //당장은 다시보기 안할꺼니까 제일 앞에꺼 하나만 가져오면됨.

        //미리 강의를 만들어놓는것도 생각.
        //제일 앞 화상강의 시작이 들어있는곳.
        lectureWeek.setText("15주차");
        System.out.println(eclassDAO.getWeeks().get(eclassDAO.getWeeks().size()-1));

        //마찬가지로 다시보기를 제외한 현재 강의만 가져오면된다.
        date.setText(eclassDAO.getEclassLectureDAOArrayList().get(eclassDAO.getEclassLectureDAOArrayList().size() - 1).getDate());
        System.out.println(eclassDAO.getEclassLectureDAOArrayList().get(eclassDAO.getEclassLectureDAOArrayList().size() - 1).getDate());
        lectureDateStr = StringUtil.lectureDate(eclassDAO.getEclassLectureDAOArrayList().get(eclassDAO.getEclassLectureDAOArrayList().size() - 1).getDate());

        if(!eclassDAO.getEclassLectureDAOArrayList().get(eclassDAO.getEclassLectureDAOArrayList().size() - 1).getStartLectureBtnName().equals("출결정보")) {
            startLectureText.setText("회상강의 시작");
        }else {
            startLectureText.setText(" ");
        }


        green.setText("출석\n" + smartATDAO.getGreen());
        red.setText("결석\n" + smartATDAO.getRed());
        blue.setText("지각\n" + smartATDAO.getBlue());
        gray1.setText("출석인정\n" + smartATDAO.getGray1());
        gray2.setText("조퇴(이탈)\n" + smartATDAO.getGray2());

//        lectureName.setText("과제중심문제해결");
//        lectureWeek.setText("3주차");
//
//        startLectureText.setText("회상강의 시작");
//        date.setText("21.01.10");
//
//        green.setText("출석\n9회");
//        red.setText("결석\n9회");
//        blue.setText("지각\n9회");
//        gray1.setText("출석인정\n9회");
//        gray2.setText("조퇴(이탈)\n9회");
    }

    public void startLectureBtnClick(MouseEvent mouseEvent) {
        getWebExPage(saveIDPW.get("webExLoginId", ""), saveIDPW.get("webExLoginPw", ""));
    }

    public synchronized void getWebExPage(String id, String password) {
        if(id.equals("") || password.equals("")) {
            new OkBtnPopupController().call();
            return;
        }
        WebDriver webexDriver = new ChromeDriver();
        try {
            webexDriver.get(WEBEX + id + WEBEX_EMAIL_URL);

            new WebDriverWait(webexDriver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='button']")));

            WebElement nextBtn = webexDriver.findElement(By.cssSelector("button[type='button']"));
            nextBtn.click();

            new WebDriverWait(webexDriver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.elementToBeClickable(By.id("webexId")));
            WebElement dLiveId = webexDriver.findElement(By.id("webexId"));
            WebElement dLivePw = webexDriver.findElement(By.id("webexPw"));

            dLiveId.sendKeys(id+WEBEX_EMAIL);
            dLivePw.sendKeys(password);

            webexDriver.findElement(By.linkText("로그인")).click();

            new WebDriverWait(webexDriver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.elementToBeClickable(By.className("meeting_list_content")));

            WebElement meetingList = webexDriver.findElement(By.className("meeting_list_content"));
            List<WebElement> subjects = meetingList.findElements(By.xpath("div[1]/ul/li"));


            for (WebElement subject: subjects) {

                subject.findElement(By.cssSelector("div[class='col col_3 col_0_3']"));

            }

        } catch(UnhandledAlertException e) {
            new OkBtnPopupController().call();
            e.printStackTrace();
            webexDriver.quit();
        } catch (TimeoutException | NoSuchElementException | StaleElementReferenceException e) { e.printStackTrace(); }
    }
}
