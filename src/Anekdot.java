import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class Anekdot {
    String Anekdot;
    int random;

        public Anekdot() throws IOException {
            random = (int) (Math.random()*10);
            Document docAn = Jsoup.connect("http://anekdot.ru/release/anekdot/day").get();
            Element trElement = docAn.getElementsByAttributeValue("class", "text").get(random);
            Anekdot = trElement.text();
        }

    public String getAnekdot() {
        return Anekdot;
    }
}
