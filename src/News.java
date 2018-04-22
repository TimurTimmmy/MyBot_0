import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class News {
    String news = "Лента новостей: " + "\n";
    int i = 0;

    public News() throws IOException {
        Document doc = Jsoup.connect("http://odnastroka.ru/").get();
        while (i < 7){
            Element trElement = doc.getElementsByAttributeValue("class", "custom-3 ").get(i);
            news = "\n" + news + trElement.text() + "\n" ;
            i++;
        }
        news = news + "Новости предоставлены сайтом odnastroka.ru " + "\uD83D\uDE01";
    }

    public String getNews() {
        return news;
    }
}
