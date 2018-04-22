import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;

public class Bot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        Message msg = update.getMessage();
        String txt = msg.getText();

        switch (txt) {
            case "/start":
                sendMsg(msg, "Добро пожаловать!");
                break;

            case "/pogoda":
                try {
                    Weather wt = new Weather();
                    sendMsg(msg, wt.getWeather());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case "/currency":
                try {
                    Currency cr = new Currency();
                    sendMsg(msg, cr.getCurrency());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case "/anekdot":
                try {
                    Anekdot an = new Anekdot();
                    sendMsg(msg, an.getAnekdot());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case "ksu":
                sendMsg(msg,"Я люблю Ксю");
                break;

        }

    }



    @Override
    public String getBotUsername() {
        return "TestUtilityBot";
    }

    @Override
    public String getBotToken() {
        return "537918677:AAEXculc7FsCOIXRhdqwdpePPgAU8m48p2U";
    }

    private void sendMsg(Message msg, String text) {
        SendMessage s = new SendMessage();
        s.setChatId(String.valueOf(msg.getChatId())); // Боту может писать не один человек, и поэтому чтобы отправить сообщение, грубо говоря нужно узнать куда его отправлять
        s.setText(text);
        try {
            sendMessage(s);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }
}