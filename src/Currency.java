import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Currency {
    String Currency;

    public Currency() throws IOException {
        Document docDol = Jsoup.connect("http://moskva.1000bankov.ru/kurs/usd/").get();
        Document docEuro = Jsoup.connect("http://moskva.1000bankov.ru/kurs/eur/").get();
        Elements trElements = docDol.getElementsByAttributeValueContaining
                ("style", "font-size: 1.2rem;");
        Elements trElementsEuro = docEuro.getElementsByAttributeValueContaining
                ("style", "font-size: 1.2rem;");
        Currency = "\uD83D\uDCB5" + trElements.text() + "\n" + "\uD83D\uDCB6" + trElementsEuro.text();
    }

    public String getCurrency() {
        return Currency;
    }
}
