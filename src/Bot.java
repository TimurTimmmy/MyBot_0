import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    private String token = "537918677:AAEXculc7FsCOIXRhdqwdpePPgAU8m48p2U";

    @Override
    public void onUpdateReceived(Update update) {
        Message msg = update.getMessage();
        String txt = msg.getText();

        switch (txt) {
        case "/start":
        sendMsg(msg, "Добро пожаловать!");
        break;

        case "Погода в Москве":
        try {
            Weather wt = new Weather();
            sendMsg(msg, wt.getWeather());
        } catch (IOException e) {
            e.printStackTrace();
        }
        break;

        case "Курс валют":
        try {
            Currency cr = new Currency();
            sendMsg(msg, cr.getCurrency());
        } catch (IOException e) {
            e.printStackTrace();
        }
        break;

        case "Случайный анекдот":
        try {
            Anekdot an = new Anekdot();
            sendMsg(msg, an.getAnekdot());
        } catch (IOException e) {
            e.printStackTrace();
        }
        break;

        case "Лента новостей":
        try {
            News news = new News();
            sendMsg(msg, news.getNews());
        } catch (IOException e) {
            e.printStackTrace();
        }
        break;

        default:
        sendMsg(msg, "Введите команду");
    }
}

    @Override
    public String getBotUsername() {
        return "TestUtilityBot";
    }

    @Override
    public String getBotToken() {
        return token;
    }

     private void sendMsg(Message msg, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);

        // Create keyboard
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboad(false);

        // Rows keyboard
        List<KeyboardRow> keyboard = new ArrayList<>();

        // First row
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Add buttons
        keyboardFirstRow.add("Погода в Москве");
        keyboardFirstRow.add("Курс валют");

        // Second row
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        // Add buttons
        keyboardSecondRow.add("Случайный анекдот");
        keyboardSecondRow.add("Лента новостей");

        // Add rows to List
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);

        replyKeyboardMarkup.setKeyboard(keyboard);

        sendMessage.setChatId(String.valueOf(msg.getChatId()));
        sendMessage.setText(text);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }
}