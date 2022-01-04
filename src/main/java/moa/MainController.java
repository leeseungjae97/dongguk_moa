package moa;

import javafx.fxml.FXML;
import moa.arguments.StringArgu;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MainController {
    @FXML
    void initialize() {
        System.setProperty("webdriver.chrome.driver", "E:\\chromedriver_win32\\chromedriver.exe");
        getSmartPage();
        //getEclassPage();
    }
    public void getSmartPage() {
        ChromeOptions chromeHeadlessOption = new ChromeOptions();
        chromeHeadlessOption.addArguments("headless");
        WebDriver smartAtDriver = new ChromeDriver(chromeHeadlessOption);
        smartAtDriver.get(StringArgu.SMART_ATTENDANCE);

        WebElement smartLoginBtn = new WebDriverWait(smartAtDriver, Duration.ofSeconds(2))
                .until(ExpectedConditions.elementToBeClickable(By.id("login")));

        WebElement smartId = smartAtDriver.findElement(By.id("loginId"));
        WebElement smartPassword = smartAtDriver.findElement(By.id("password"));
//        WebElement smartLoginBtn = smartAtDriver.findElement(By.linkText("로그인"));
        
        smartId.sendKeys("2016210162");
        smartPassword.sendKeys("Asqw!2qwas");

        smartLoginBtn.click();

        new WebDriverWait(smartAtDriver, Duration.ofSeconds(2))
                .until(ExpectedConditions.elementToBeClickable(By.id("view_today")));

        WebElement subjectTable = smartAtDriver.findElement(By.id("lecture"));
        List<WebElement> clickableSubjects = subjectTable.findElements(By.cssSelector("#lecture > tbody > tr"));

        clickableSubjects.get(0).click();

    }
    public void getEclassPage() {
// Jsoup 방식 --

//        HttpGet request = new HttpGet(StringArgu.ECLASS);
//
//        Document doc = null;
//
//        try (CloseableHttpClient httpClient = HttpClients.createDefault();
//             CloseableHttpResponse response = httpClient.execute(request)){
//            StringBuffer sb=new StringBuffer();
//            HttpEntity entity = response.getEntity();
//            if (entity != null) {
//                String result = EntityUtils.toString(entity);
//                sb.append(result);
//            }
//            doc = Jsoup.parse(sb.toString());
//
//            System.out.println(doc.body());
//
//
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }

// Selenium 방식 --
        ChromeOptions chromeHeadlessOption = new ChromeOptions();
        chromeHeadlessOption.addArguments("headless");

        WebDriver eClassDriver = new ChromeDriver(chromeHeadlessOption);
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
        eclassId.sendKeys("2016210162");
        eclassPassword.sendKeys("Asqw!2qwas");

        loginBtn.click();

        //로그인 후 창 리프레시되는거 기다리기. (drop down 되는 span을 기다림)
        new WebDriverWait(eClassDriver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(By.tagName("b")));

        //내 강의실 목록의 버튼들 가져오기
        WebElement clickSubject = eClassDriver.findElement(By.id("mCSB_1"));
        List<WebElement> clickableSubjects = clickSubject.findElements(By.tagName("button"));

        //첫번째 강의실 클릭.
        clickableSubjects.get(0).click();
    }
}