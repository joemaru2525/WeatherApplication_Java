package javayahooweather02;

import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.image.ImageView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class FXMLDocumentController implements Initializable {
    
    @FXML   private Label label;
    @FXML   private Label labelSubject;
    @FXML   private Label labelAnnounce;
    @FXML   private Label labelDate;
    @FXML   private Label labelWeather;
    @FXML   private Label labelHighTemp;
    @FXML   private Label labelLowTemp;
    @FXML   private Label labelPrecip01;
    @FXML   private Label labelPrecip02;
    @FXML   private Label labelPrecip03;
    @FXML   private Label labelPrecip04;
    @FXML   private ImageView picWeather;
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        System.out.println("You clicked me!");
        label.setText("Got Information");
        
        Document doc = Jsoup.connect("https://weather.yahoo.co.jp/weather/jp/13/4410.html").get();
        Elements title = doc.getElementsByTag("title");
        System.out.println(title.text());
        labelSubject.setText(title.text());
        
        Element announce = doc.select("div.yjw_title_h2 yjw_clr,p.yjSt.yjw_note_h2").first();
        System.out.println(announce.text());
        labelAnnounce.setText(announce.text());
        
        Element dateWeather = doc.select("p.date").first();
        System.out.println(dateWeather.text());
        labelDate.setText(dateWeather.text());
        
        Element weather = doc.select("p.pict").first();
        System.out.println(weather.text());
        labelWeather.setText(weather.text());
        
        Elements content = doc.getElementsByClass("pict");
        Element link = content.select("img").first();
        String linkSrc = link.attr("src");
        System.out.println(linkSrc);
        Image image = new Image(linkSrc );
        picWeather.setImage(image);
        
        Element highTemp = doc.select("li.high").first();
        System.out.println(highTemp.text());
        labelHighTemp.setText(highTemp.text());
        
        Element lowTemp = doc.select("li.low").first();
        System.out.println(lowTemp.text());
        labelLowTemp.setText(lowTemp.text());
        
        Element precip = doc.select("tr.precip").first();
        String data = precip.text().replace("降水", "");
        StringBuilder sb = new StringBuilder();
        sb.append(data);
        sb.append(" ");
        //System.out.println(new String(sb));
        int offset = 0;
        String[] precipArray = { "---", "---", "---", "---" };
        for (int count = 0; count < 4; count++){
            int startPoint = sb.indexOf(" ", offset);
            int stopPoint = sb.indexOf(" ", startPoint + 1);
            offset = stopPoint;
            String eachPrecip = sb.substring(startPoint + 1, stopPoint);
            precipArray[count] = eachPrecip;
        }
        System.out.println(precipArray[0]);
        System.out.println(precipArray[1]);
        System.out.println(precipArray[2]);
        System.out.println(precipArray[3]);
        labelPrecip01.setText(precipArray[0]);
        labelPrecip02.setText(precipArray[1]);
        labelPrecip03.setText(precipArray[2]);
        labelPrecip04.setText(precipArray[3]);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
