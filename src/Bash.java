import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class Bash {

    String Bash;

    public Bash() throws IOException {
        int random = 400000 + (int) (Math.random()*((451000 - 400000) + 1));
        String URL = "http://bash.im/quote/" + random;
        Document docAn = Jsoup.connect(URL).get();
        Element trElement = docAn.getElementsByAttributeValue("class", "text").get(0);
        Bash = "Цитата №: " + random + "\n" + trElement.text();
    }

    public String getBash() {
        return Bash;
    }
}
