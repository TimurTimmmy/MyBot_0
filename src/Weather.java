import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


import java.io.IOException;

public class Weather {
    String weather;

    public Weather() throws IOException {

        Document doc = Jsoup.connect("http://realmeteo.ru/moscow/1/current/").get();
        Element trElement = doc.getElementsByAttributeValue("id", "col1").get(0);
        Element trElement2 = doc.getElementsByAttributeValue("id", "col2").get(0);
        Element trElement3 = doc.getElementsByAttributeValue("class","meteodata").get(1);
        Element trElement4 = doc.getElementsByAttributeValue("class","meteodata").get(3);
        weather = "Температура в Москве: " + trElement.text().substring(0,6) + "\n" +
                "Ощущается как: " + trElement2.text().substring(0,6) + "\n" +
                "Давление: " + trElement3.text().substring(0,3) + " мм.рт.ст" + "\n" +
                "Скорость ветра: " + trElement4.text().substring(0,6);
    }

        public String getWeather() {return weather;}
}

