import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@AllArgsConstructor
@NoArgsConstructor
public class Bot extends TelegramLongPollingBot {
    //Прикрутил логирование, ошибки выводит в консоль
    private static final Logger log = Logger.getLogger(Bot.class);
//больше 10сек- пропал интернет
    final int RECONNECT_PAUSE = 10000;

    @Setter
    @Getter
    String userName;
    @Setter
    @Getter
    String token;

    @Override
    //Обработчик сообщений
    public void onUpdateReceived(Update update) {
        log.debug("Receive new Update. updateID: " + update.getUpdateId());
        log.debug("Receive new Update. updateID: " + update.getUpdateId() + "new message" + update.getMessage());
        Long chatId = update.getMessage().getChatId();
        /////////Сервисная часть для загрузки новых картинок   if(!(update.getMessage().hasPhoto())) {
//Преобразуем апдейт в текст
        String inputText = update.getMessage().getText();
//Сравниваем полученный текст с вариантамами ответа
        if (inputText.toLowerCase(Locale.ROOT).startsWith("/start") ||
                inputText.toLowerCase(Locale.ROOT).startsWith("здарова") ||
                inputText.toLowerCase(Locale.ROOT).startsWith("привет") ||
                inputText.toLowerCase(Locale.ROOT).startsWith("приветик") ||
                inputText.toLowerCase(Locale.ROOT).startsWith("сначала")) {
            SendMessage message = new SendMessage();
            CustomsetButtons(message, "Обзор", "Демонстрация");
            message.setChatId(chatId);
            message.setText
                    ("Добрый день, вас приветствует бот для резюме\n" +
                            "Управление в меню осуществляется кнопками снизу \n" +
                            "Управление демонстрацией - командами на русском языке,команды во вкладке демонстрация\n");
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        if (inputText.toLowerCase(Locale.ROOT).startsWith("демонстрация")) {
            SendMessage message = new SendMessage();
            CustomsetButtons(message, "Демонстрация", "Сначала");
            message.setChatId(chatId);
            message.setText("Из функционала: \n" +
                    "Разверни - реверс строки\n" +
                    "Посчитай - Считает буквы\n" +
                    "Разложи - подсчет каждого символа\n" +
                    "Отправь мем - отправит мем\n" +
                    "В результате демонстрации пробел так же считается в результате\n"+
                    "Команды набираются на русском языке и не воспреимчивы к регистру, например: \n" +
                    "Разверни 111222333444\n" +
                    "РаЗвЕрНиПривет,как Дела");
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        if (inputText.toLowerCase(Locale.ROOT).startsWith("обзор")) {
            SendMessage message = new SendMessage();
            CustomsetButtons(message, "Вкратце про личные качества", "Вкратце про навыки");
            message.setChatId(chatId);
            message.setText("Два варианта на выбор:");
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        if (inputText.toLowerCase(Locale.ROOT).startsWith("вкратце про личные качества")) {
            SendMessage message = new SendMessage();
            CustomsetButtons(message, "Вкратце про навыки", "Вкратце про навыки)");
            message.setChatId(chatId);
            message.setText("Пунктуальность, ответственность, желание работать и развиваться");
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        if (inputText.toLowerCase(Locale.ROOT).startsWith("вкратце про навыки")) {
            SendMessage message = new SendMessage();
            CustomsetButtons(message, "Вкратце про навыки", "Сначала");
            message.setChatId(chatId);
            message.setText("Java junior, \n" +
                    "Коллекции: arraylist,linkedlist, hashmap.....\n" +
                    "Многопоточность: Нити, Thread, Runnuble\n" +
                    "Потоки ввода-вывода, input-output stream\n" +
                    "Интерфейсы \n" +
                    "Основы ООП, Абстракция, Инкапсуляция, Полиморфизм, Наследование\n" +
                    "Логирование(log4j)+Maven\n" +
                    "Git(Не из командной строки),API(Имел дело с VK, telegram)\n" +
                    "Post-get запросы");
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

//Разворот встроенными функциями(Стрингбилдер)
        if (inputText.toLowerCase(Locale.ROOT).startsWith("разверни")) {
            SendMessage message = new SendMessage();
            CustomsetButtons(message, "Демонстрация", "сначала");
            message.setChatId(chatId);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(inputText);
            stringBuilder.delete(0, 8);
            stringBuilder.reverse();
            message.setText("Разворот: " + stringBuilder.toString());
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        //Посимвольный подсчет строки через встроенные функции(Стригбилдер)
        if (inputText.toLowerCase(Locale.ROOT).startsWith("посчитай")) {
            SendMessage message = new SendMessage();
            CustomsetButtons(message, "Демонстрация", "сначала");
            message.setChatId(chatId);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(inputText);

            String s = stringBuilder.delete(0, 8).toString();
            message.setText("Число символов: " + s.length());
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }



        if (inputText.toLowerCase(Locale.ROOT).startsWith("разложи")) {
            SendMessage message = new SendMessage();
            CustomsetButtons(message, "Демонстрация", "сначала");
            message.setChatId(chatId);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(inputText);
            String s = stringBuilder.delete(0, 7).toString();
//Посимвольное разложение через HASHMAP
            char[] array = s.toCharArray();
            HashMap<Character, Integer> hashMap = new HashMap<>();
            for (int i = 0; i < array.length; i++) {
                char currentChar = array[i];
                if (hashMap.containsKey(currentChar)) {
                    hashMap.put(currentChar, hashMap.get(currentChar) + 1);
                } else {
                    hashMap.put(currentChar, 1);
                }
            }
            message.setText("Это было не просто: " + hashMap.toString());
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

//Отправка картинки, с заранее определенным файлайди через сервис ниже
        if (inputText.toLowerCase(Locale.ROOT).startsWith("отправь мем")) {
            SendPhoto msg = new SendPhoto()
                    .setChatId(chatId)
                    .setPhoto("AgACAgIAAxkBAAPQYWWAb05-ecn5eHf99PwN5keaDUEAAiG3MRsoeylLtPjSFLCYFn0BAAMCAAN5AAMhBA")
                    .setCaption("Вот мем");
            try {
                sendPhoto(msg);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        //  }
        //////////////////Сервисная часть для загрузки новых мемов,закомитить скобку сверху
            // вспомогательный мАссив с фото разлИчных размеров от телеграМа
//            List<PhotoSize> photos = update.getMessage().getPhoto();
//            // Узнаем файл айди
//            String f_id = photos.stream()
//                    .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
//                    .findFirst()
//                    .orElse(null).getFileId();
//            // Узнаем ширину
//            int f_width = photos.stream()
//                    .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
//                    .findFirst()
//                    .orElse(null).getWidth();
//            // Узнаем высоту
//            int f_height = photos.stream()
//                    .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
//                    .findFirst()
//                    .orElse(null).getHeight();
//            // Устанавливаем подпись
//            String caption = "file_id: " + f_id + "\nwidth: " + Integer.toString(f_width) + "\nheight: " + Integer.toString(f_height);
//            SendPhoto msg = new SendPhoto()
//                    .setChatId(chatId)
//                    .setPhoto(f_id)
//                    .setCaption(caption);
//            try {
//                sendPhoto(msg); //Отправляем фото обратно
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//            }
//        }
    }
////////////////////////



    // Создание клавиатуры стандартным шаблоном
        public synchronized void CustomsetButtons (SendMessage sendMessage, String knopka1, String knopka2){

            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            replyKeyboardMarkup.setSelective(true);
            replyKeyboardMarkup.setResizeKeyboard(true);
            replyKeyboardMarkup.setOneTimeKeyboard(false);
            //Создаем список строк клавиатуры
            List<KeyboardRow> keyboard = new ArrayList<>();
            //Первая строчка клавиатуры
            KeyboardRow keyboardFirstRow = new KeyboardRow();
            //Добавляем кнопки в первую строчку клавиатуры
            keyboardFirstRow.add(new KeyboardButton(knopka1));
            //Вторая строчка клавиатуры
            KeyboardRow keyboardSecondRow = new KeyboardRow();
            //Добавляем кнопки во вторую строчку клавиатуры
            keyboardSecondRow.add(new KeyboardButton(knopka2));
            //Добавляем все строчки клавиатуры в список
            keyboard.add(keyboardFirstRow);
            keyboard.add(keyboardSecondRow);
            //Устанваливаем этот список нашей клавиатуре
            replyKeyboardMarkup.setKeyboard(keyboard);
        }

        @Override
        public String getBotUsername () {
            return userName;
        }

        @Override
        public String getBotToken () {
            return token;
        }
//Установка соединения с серверами
        public void botConnect () {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
            try {
                telegramBotsApi.registerBot(this);
                         log.info("TelegramAPI started. Look for messages");
            } catch (TelegramApiRequestException e) {
                        log.error("Cant Connect. Pause " + RECONNECT_PAUSE / 1000 + "sec and try again. Error: " + e.getMessage());
                try {
                    Thread.sleep(RECONNECT_PAUSE);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                    return;
                }
                botConnect();
            }
        }
    }
