import org.apache.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;

public class Program {
    private static final Logger log = Logger.getLogger(Program.class);
//Запускаем бота, имя и токен вносить сюда
private static String token="";//Токен
private static String botusername="";//Имя бота
    public static void main(String[] args) {

        ApiContextInitializer.init();
        Bot superbot = new Bot(botusername, token);
        superbot.botConnect();
    }
}