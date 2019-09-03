package Chat_java_rush.task3008.client;


import Chat_java_rush.task3008.ConsoleHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BotClient extends Client
{
    public static void main(String[] args) {
        BotClient botClient = new BotClient();
        botClient.run();
    }
    @Override
    protected String getUserName() {
        return String.format("date_bot_%d", (int) (Math.random() * 100));
    }

    @Override
    protected boolean shouldSendTextFromConsole() {
        return false;
    }

    @Override
    protected SocketThread getSocketThread() {
        return new BotSocketThread();
    }

    public class BotSocketThread extends SocketThread
    {
        @Override
        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            sendTextMessage("Привет чатику. Я бот. Понимаю команды: дата, день, месяц, год, время, час, минуты, секунды.");
            super.clientMainLoop();
        }

        @Override
        protected void processIncomingMessage(String message) {
            ConsoleHelper.writeMessage(message);
            if(message.contains(":")) {
                String[] splitstring = message.split(": ");
                String name = splitstring[0];
                String splittedmessage = splitstring[1];
                //ConsoleHelper.writeMessage(name);
                //ConsoleHelper.writeMessage(splittedmessage);
                Calendar date = Calendar.getInstance();

                if (splittedmessage.equals("дата"))
                    sendTextMessage(String.format("Информация для %s: %s", name, new SimpleDateFormat("d.MM.YYYY").format(date.getTime()).toString()));
                else if (splittedmessage.equals("день"))
                    sendTextMessage(String.format("Информация для %s: %s", name, new SimpleDateFormat("d").format(date.getTime()).toString()));
                else if (splittedmessage.equals("месяц"))
                    sendTextMessage(String.format("Информация для %s: %s", name, new SimpleDateFormat("MMMM").format(date.getTime()).toString()));
                else if (splittedmessage.equals("год"))
                    sendTextMessage(String.format("Информация для %s: %s", name, new SimpleDateFormat("YYYY").format(date.getTime()).toString()));
                else if (splittedmessage.equals("время"))
                    sendTextMessage(String.format("Информация для %s: %s", name, new SimpleDateFormat("H:mm:ss").format(date.getTime()).toString()));
                else if (splittedmessage.equals("час"))
                    sendTextMessage(String.format("Информация для %s: %s", name, new SimpleDateFormat("H").format(date.getTime()).toString()));
                else if (splittedmessage.equals("минуты"))
                    sendTextMessage(String.format("Информация для %s: %s", name, new SimpleDateFormat("m").format(date.getTime()).toString()));
                else if (splittedmessage.equals("секунды"))
                    sendTextMessage(String.format("Информация для %s: %s", name, new SimpleDateFormat("s").format(date.getTime()).toString()));
            }
        }
    }
}
