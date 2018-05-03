import org.json.JSONObject;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Document;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    String token = "537918677:AAEXculc7FsCOIXRhdqwdpePPgAU8m48p2U";

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


 /*       if (update.hasMessage() && update.getMessage().hasDocument()) {
            Document doc = update.getMessage().getDocument();
            try {
                uploadFile(doc.getFileName(), doc.getFileId());
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(doc.getFileId());
        } */

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

    public void uploadFile(String file_name, String file_id) throws IOException{
        String upPath = "C://tmp/";

        URL url = new URL("https://api.telegram.org/bot"+token+"/getFile?file_id="+file_id);
        BufferedReader in = new BufferedReader(new InputStreamReader( url.openStream()));
        String res = in.readLine();
        JSONObject jresult = new JSONObject(res);
        JSONObject path = jresult.getJSONObject("result");
        String file_path = path.getString("file_path");
        URL downoload = new URL("https://api.telegram.org/file/bot" + token + "/" + file_path);
        FileOutputStream fos = new FileOutputStream(upPath + file_name);
        System.out.println("Start upload");
        ReadableByteChannel rbc = Channels.newChannel(downoload.openStream());
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
//        uploadFlag = 0;
        System.out.println("Uploaded!");
    }


    private void sendMsg(Message msg, String text) {
        SendMessage sendMessage = new SendMessage();

        sendMessage.enableMarkdown(true);

        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboad(false);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add("Погода в Москве");
        keyboardFirstRow.add("Курс валют");

        // Вторая строчка клавиатуры
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardSecondRow.add("Случайный анекдот");
        keyboardSecondRow.add("Лента новостей");

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        // и устанваливаем этот список нашей клавиатуре
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