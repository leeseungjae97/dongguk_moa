package moa.controllers;

import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Popup;
import moa.Main;
import moa.arguments.StringArgu;
import moa.util.CustomTopBar;
import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class LoginController {
    public HBox topBar;
    public Label minimum;
    public Label close;
    public TextField idTextField;
    public JFXCheckBox idPwSave;
    public Button loginBtn;
    public PasswordField psTextField;

    @FXML
    void initialize() {
        CustomTopBar.configurationTopBar(topBar, minimum, close);
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");

    }

    public void getSmartPage(String id, String password) {
        if(id.equals("") || password.equals("")) {
            new OkBtnPopupController().call();
            return;
        }

        ChromeOptions chromeHeadlessOption = new ChromeOptions();
//            chromeHeadlessOption.addArguments("headless");
        WebDriver smartAtDriver = new ChromeDriver(chromeHeadlessOption);
        try {
            smartAtDriver.get(StringArgu.SMART_ATTENDANCE);

            WebElement smartLoginBtn = new WebDriverWait(smartAtDriver, Duration.ofSeconds(2))
                    .until(ExpectedConditions.elementToBeClickable(By.id("login")));

            WebElement smartId = smartAtDriver.findElement(By.id("loginId"));
            WebElement smartPassword = smartAtDriver.findElement(By.id("password"));
//        WebElement smartLoginBtn = smartAtDriver.findElement(By.linkText("로그인"));

            smartId.sendKeys(id);
            smartPassword.sendKeys(password);

            smartLoginBtn.click();

            new WebDriverWait(smartAtDriver, Duration.ofSeconds(2))
                    .until(ExpectedConditions.elementToBeClickable(By.id("view_today")));

            WebElement subjectTable = smartAtDriver.findElement(By.id("lecture"));
            List<WebElement> clickableSubjects = subjectTable.findElements(By.cssSelector("#lecture > tbody > tr"));

            clickableSubjects.get(0).click();
        } catch (UnhandledAlertException e) {
            new OkBtnPopupController().call();
            smartAtDriver.quit();
            idTextField.setText("");
            psTextField.setText("");
        }

    }
    public void getEclassPage(String id, String password) {
        if(id.equals("") || password.equals("")) {
            new OkBtnPopupController().call();
            return;
        }
        ChromeOptions chromeHeadlessOption = new ChromeOptions();
        chromeHeadlessOption.addArguments("headless");

        WebDriver eClassDriver = new ChromeDriver(chromeHeadlessOption);
        try {

            eClassDriver.get(StringArgu.ECLASS);

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

            //내 강의실 목록의 버튼들 가져오기
            WebElement clickSubject = eClassDriver.findElement(By.id("mCSB_1"));
            List<WebElement> clickableSubjects = clickSubject.findElements(By.tagName("button"));

            //첫번째 강의실 클릭.
            clickableSubjects.get(0).click();

        } catch(UnhandledAlertException e) {
            new OkBtnPopupController().call();
            eClassDriver.quit();
            idTextField.setText("");
            psTextField.setText("");
        }
    }

    public void loginBtnClick(ActionEvent actionEvent) {
        String id = idTextField.getText().toString();
        String password = psTextField.getText().toString();
        getSmartPage(id, password);
        //getEclassPage();
    }
}