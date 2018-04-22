import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class StrartBot {
    public static void main(String[] args) {
        TelegramBotsApi newBotApi = new TelegramBotsApi();
        try {
            newBotApi.registerBot(new Bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}
