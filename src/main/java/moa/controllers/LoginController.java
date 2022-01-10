package moa.controllers;

import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import moa.dao.EclassDAO;
import moa.dao.EclassLectureDAO;
import moa.dao.SmartATDAO;
import moa.util.CustomTopBar;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import static moa.arguments.StringArgu.*;

public class LoginController {
    public HBox topBar;
    public Label minimum;
    public Label close;
    public TextField idTextField;
    public JFXCheckBox idPwSave;
    public Button loginBtn;
    public PasswordField pwTextField;
    public SubjectPageController subjectPageController;
    private ChromeOptions chromeHeadlessOption;
    private Preferences saveIDPW;

    @FXML
    void initialize() {
        CustomTopBar.configurationTopBar(topBar, minimum, close);
        System.setProperty(WEBDRIVER_TYPE_CHROME, WEBDRIVER_PATH);
        chromeHeadlessOption = new ChromeOptions();
        chromeHeadlessOption.addArguments(DRIVER_HEADLESS);
        subjectPageController = new SubjectPageController();
        saveIDPW = Preferences.userRoot();
        checkIDPWSave();
    }

    public void getSmartPage(String id, String password) {
        if(id.equals("") || password.equals("")) {
            new OkBtnPopupController().call();
            return;
        }

        WebDriver smartATDriver = new ChromeDriver(chromeHeadlessOption);
        try {
            smartATDriver.get(SMART_ATTENDANCE);

            WebElement smartLoginBtn = new WebDriverWait(smartATDriver, Duration.ofSeconds(2))
                    .until(ExpectedConditions.elementToBeClickable(By.id("login")));

            WebElement smartId = smartATDriver.findElement(By.id("loginId"));
            WebElement smartPassword = smartATDriver.findElement(By.id("password"));
//        WebElement smartLoginBtn = smartAtDriver.findElement(By.linkText("로그인"));

            smartId.sendKeys(id);
            smartPassword.sendKeys(password);

            smartLoginBtn.click();

            new WebDriverWait(smartATDriver, Duration.ofSeconds(2))
                    .until(ExpectedConditions.elementToBeClickable(By.id("view_today")));

            WebElement subjectTable = smartATDriver.findElement(By.id("lecture"));
            List<WebElement> subjectSize = subjectTable.findElements(By.cssSelector("#lecture > tbody > tr"));

            for (int i = 0; i < subjectSize.size(); i++) {
                WebElement clickSubjectTable = smartATDriver.findElement(By.id("lecture"));
                List<WebElement> clickableSubjects = clickSubjectTable.findElements(By.cssSelector("#lecture > tbody > tr"));
                clickableSubjects.get(i).click();
                getSmartATs(smartATDriver);
            }
        } catch (UnhandledAlertException e) {
            new OkBtnPopupController().call();
            smartATDriver.quit();
            idTextField.setText("");
            pwTextField.setText("");
        } catch (TimeoutException | NoSuchElementException | StaleElementReferenceException e) { smartATDriver.quit(); }

    }
    public void getEclassPage(String id, String password) {
        if(id.equals("") || password.equals("")) {
            new OkBtnPopupController().call();
            return;
        }
        WebDriver eClassDriver = new ChromeDriver(chromeHeadlessOption);
        try {

            eClassDriver.get(ECLASS);

            eClassDriver.switchTo().frame("main");
            //학생/교직원 로그인 버튼
            WebElement openLoginInput = eClassDriver.findElement(By.linkText("학생/교직원 로그인"));
            openLoginInput.click();

            //아이디, 패스워드 입력창
            WebElement eclassId = eClassDriver.findElement(By.name("userDTO.userId"));
            WebElement eclassPassword = eClassDriver.findElement(By.name("userDTO.password"));
            //로그인 버튼
            WebElement loginBtn = eClassDriver.findElement(By.className("loginBtn"));

            //로그인
            eclassId.sendKeys(id);
            eclassPassword.sendKeys(password);

            loginBtn.click();

            //로그인 후 창 리프레시되는거 기다리기. (drop down 되는 span을 기다림)
                new WebDriverWait(eClassDriver, Duration.ofSeconds(5))
                        .until(ExpectedConditions.elementToBeClickable(By.tagName("b")));

            WebElement subjectTable = eClassDriver.findElement(By.id("mCSB_1"));
            List<WebElement> subjectsSize = subjectTable.findElements(By.tagName("button"));

            subjectPageController.setSubjectSize(subjectsSize.size());
            
            //내 강의실 목록의 버튼 및 강의이름 가져오기
            for (int i = 0; i < subjectsSize.size(); i++) {
                WebElement clickSubjectTable = eClassDriver.findElement(By.id("mCSB_1"));
                List<WebElement> clickableSubjects = clickSubjectTable.findElements(By.tagName("button"));
                List<WebElement> subjectName = subjectTable.findElements(By.className("boardTxt"));

                EclassDAO eclassDAO = new EclassDAO(subjectName.get(i).getText());
                clickableSubjects.get(i).click();
                getEclassSubjects(eclassDAO, eClassDriver);
                subjectPageController.addEclassDAO(eclassDAO);
            }

        } catch(UnhandledAlertException e) {
            new OkBtnPopupController().call();
            eClassDriver.quit();
            idTextField.setText("");
            pwTextField.setText("");
        } catch (TimeoutException | NoSuchElementException | StaleElementReferenceException e) { eClassDriver.quit(); }
    }
    public void getSmartATs(WebDriver smartDriver) {
        // div.col-md-12 > div > div > row[1]
        // 출석 :  div class='content-wrapper green' > div > h3 > span class='semi-bold'.text
        // 결석 :  div class='content-wrapper red' > div > h3 > span class='semi-bold'.text
        // 지각 :  div class='content-wrapper blue' > div > h3 > span class='semi-bold'.text

        // div.col-md-12 > div > div > row[2]
        // 출석인정 :  div class='content-wrapper gray' > div > h3 > span class='semi-bold'.text
        // 조퇴(이탈) :  div class='content-wrapper gray' > div > h3 > span class='semi-bold'.text
        try {
            new WebDriverWait(smartDriver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.elementToBeClickable((By.id("select-class"))));

            WebElement green = smartDriver.findElement(By.cssSelector("div[class='content-wrapper green']"));
            WebElement red = smartDriver.findElement(By.cssSelector("div[class='content-wrapper red']"));
            WebElement blue = smartDriver.findElement(By.cssSelector("div[class='content-wrapper blue']"));
            WebElement gray1 = smartDriver.findElements(By.cssSelector("div[class='content-wrapper gray']")).get(0);
            WebElement gray2 = smartDriver.findElements(By.cssSelector("div[class='content-wrapper gray']")).get(1);

            String greenText = green.findElements(By.className("semi-bold")).get(1).getText();
            String redText = red.findElements(By.className("semi-bold")).get(1).getText();
            String blueText = blue.findElements(By.className("semi-bold")).get(1).getText();
            String gray1Text = gray1.findElements(By.className("semi-bold")).get(1).getText();
            String gray2Text = gray2.findElements(By.className("semi-bold")).get(1).getText();

            System.out.println(greenText + " " + redText + " " + blueText);
            System.out.println(gray1Text + " " + gray2Text);

            SmartATDAO smartATDAO = new SmartATDAO(greenText, redText, blueText, gray1Text, gray2Text);
            subjectPageController.addSmartDAO(smartATDAO);

            smartDriver.navigate().back();

            new WebDriverWait(smartDriver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.elementToBeClickable(By.id("view_today")));
        } catch (TimeoutException | NoSuchElementException | StaleElementReferenceException e) { smartDriver.quit(); }
    }
    public void getEclassSubjects(EclassDAO eclassDAO, WebDriver eClassDriver) {
        //회상강의 시작
        // > ul > li.text ="[보강] @강 [2021-01-01 09:30]"
        // class=btn btn-orange fcWhite
        // title="화상강의 시작"
        try {
            new WebDriverWait(eClassDriver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.elementToBeClickable((By.linkText("화상강의 시작"))));

            List<WebElement> subjectVideos = eClassDriver.findElements(By.cssSelector("div[class='video on']"));
            for (WebElement element : subjectVideos) {
                WebElement startLectureBtn = null;
                WebElement prevLecture = null;
                String date = "";

                try {
                    startLectureBtn = element.findElement(By.cssSelector("a[class='btn btn-orange fcWhite']"));
                    prevLecture = element.findElement(By.cssSelector("a[class='btn btn-blue\t fcWhite']"));
                    date = element.findElement(By.xpath("ul[1]/li")).getText();
                } catch (NoSuchElementException e) {
                }
                EclassLectureDAO eclassLectureDAO = new EclassLectureDAO(startLectureBtn, prevLecture, date);
                eclassDAO.addEclassLectureDAO(eclassLectureDAO);
            }
            List<WebElement> cols = eClassDriver.findElements(By.cssSelector("th[scope='col']"));
            for (int i = 0; i < cols.size(); i++) {
                System.out.println(cols.get(i).getText());
                String week = cols.get(i).getText();
                eclassDAO.addWeek(week);
            }

            eClassDriver.navigate().back();
            eClassDriver.switchTo().frame(0);

            new WebDriverWait(eClassDriver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.elementToBeClickable(By.id("mCSB_1")));

        } catch (TimeoutException | NoSuchElementException | StaleElementReferenceException e) { eClassDriver.quit(); }
    }
    public void loginBtnClick(ActionEvent actionEvent) {
        String id = idTextField.getText().toString();
        String password = pwTextField.getText().toString();
//        getSmartPage(id, password);
        getEclassPage(id, password);

        subjectPageController.call();
    }
    public void checkIDPWSave() {
        if(!saveIDPW.get("id", "").equals("") && !saveIDPW.get("pw", "").equals("")) {
            idTextField.setText(saveIDPW.get("id", ""));
            pwTextField.setText(saveIDPW.get("pw", ""));
            System.out.println(saveIDPW.get("pw", ""));
            idPwSave.setSelected(true);
        }
    }
    public void clickSave(ActionEvent actionEvent) {
        if(actionEvent.getEventType() == ActionEvent.ACTION) {
            if(idPwSave.isSelected()) {
                saveIDPW.put("id", idTextField.getText());
                saveIDPW.put("pw", pwTextField.getText());
            }
            else {
                try {
                    saveIDPW.clear();
                } catch (BackingStoreException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void inputIdText(KeyEvent actionEvent) {
        if(idPwSave.isSelected()) {
            saveIDPW.put("id", idTextField.getText());

        }
    }

    public void inputPwText(KeyEvent actionEvent) {
        if(idPwSave.isSelected()) {
            saveIDPW.put("pw", pwTextField.getText());
        }
    }
}