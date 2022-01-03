package moa;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import javafx.fxml.FXML;
import moa.arguments.StringArgu;

public class MainController {
    @FXML
    void initialize() {
        getEclassPage();
    }
    public void getEclassPage() {
        try {
            WebClient webClient = new WebClient();
            HtmlPage page = webClient.getPage(StringArgu.ECLASS);
            HtmlUnit
            System.out.println(page.getDocumentElement());
        } catch(Exception e) {
            e.printStackTrace();
        }
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
    }
}