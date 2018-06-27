import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.File;
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

        case "\u26C5 Москва":
        try {
            Weather wt = new Weather();
            sendMsg(msg, wt.getWeather());
        } catch (IOException e) {
            e.printStackTrace();
        }
        break;

        case "Курс \uD83D\uDCB6 \uD83D\uDCB5":
        try {
            Currency cr = new Currency();
            sendMsg(msg, cr.getCurrency());
        } catch (IOException e) {
            e.printStackTrace();
        }
        break;

        case "\uD83D\uDE02 Анекдот":
        try {
            Anekdot an = new Anekdot();
            sendMsg(msg, an.getAnekdot());
        } catch (IOException e) {
            e.printStackTrace();
        }
        break;

        case "\uD83D\uDCF0 Лента":
        try {
            News news = new News();
            sendMsg(msg, news.getNews());
        } catch (IOException e) {
            e.printStackTrace();
        }
        break;

        case "\uD83D\uDE87 Москова":
            String pathMetro = "C:\\tmp\\metromap.jpg";
        try {
            sendPhoto(msg,pathMetro);
        } catch (Exception e) {
            e.printStackTrace();
        }
        break;

        case "\uD83C\uDD71 Bash":
        try {
            Bash bash = new Bash();
            sendMsg(msg, bash.getBash());
        } catch (IOException e) {
            e.printStackTrace();
        }
        break;

        default:
        sendChat(txt);

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
        keyboardFirstRow.add("\u26C5 Москва");
        keyboardFirstRow.add("Курс \uD83D\uDCB6 \uD83D\uDCB5");
        keyboardFirstRow.add("\uD83C\uDD71 Bash");

        // Second row
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        // Add buttons
        keyboardSecondRow.add("\uD83D\uDE02 Анекдот");
        keyboardSecondRow.add("\uD83D\uDCF0 Лента");
        keyboardSecondRow.add("\uD83D\uDE87 Москова");

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
            sendChat(String.valueOf(e));
        }
    }

    private void sendChat(String text){
        SendMessage sendChat = new SendMessage();
        sendChat.enableMarkdown(true);
        sendChat.setChatId("-1001370302039");
        sendChat.setText("Cообщение от бота: " + "\n" + "StackTrace " + text);
        try {
            sendMessage(sendChat);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            sendChat(String.valueOf(e));
        }

    }

    private void sendPhoto(Message msg, String path){
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(String.valueOf(msg.getChatId()));
        sendPhoto.setNewPhoto(new File(path));
        try {
            sendPhoto(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            sendChat(String.valueOf(e));
        }
    }
}