package Chat_java_rush.task3008.client;



import Chat_java_rush.task3008.Connection;
import Chat_java_rush.task3008.ConsoleHelper;
import Chat_java_rush.task3008.Message;
import Chat_java_rush.task3008.MessageType;

import java.io.IOException;
import java.net.Socket;

public class Client
{
    protected Connection connection;
    private volatile boolean clientConnected = false;

    public static void main(String[] args) {
        Client client=new Client();
        client.run();
    }
    protected String getServerAddress()
    {
        ConsoleHelper.writeMessage("Введите адрес сервера:");
        return ConsoleHelper.readString();
    }

    protected int getServerPort()
    {
        ConsoleHelper.writeMessage("Введите номер порта сервера:");
        return ConsoleHelper.readInt();
    }

    protected String getUserName()
    {
        ConsoleHelper.writeMessage("Введите имя пользователя:");
        return ConsoleHelper.readString();
    }

    protected boolean shouldSendTextFromConsole()
    {
        return true;
    }

    protected SocketThread getSocketThread()
    {
        return new SocketThread();
    }

    protected void sendTextMessage(String text)
    {

        try {
            Message m=new Message(MessageType.TEXT,text);
            connection.send(m);
        } catch (IOException e) {
            this.clientConnected=false;
            ConsoleHelper.writeMessage("Сообщение не было отправлено");
        }
    }

    public void run()
    {
        SocketThread socketThread= getSocketThread();
        socketThread.setDaemon(true);
        socketThread.start();

        try {
            synchronized (this) {
                this.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            ConsoleHelper.writeMessage("При работе клиента возникла ошибка");
            System.exit(1);
        }
        if(clientConnected)
        {
            ConsoleHelper.writeMessage("Соединение установлено. Для выхода наберите команду 'exit'.");
            while(clientConnected) {
                String string=ConsoleHelper.readString();
                if(string.equals("exit")) break;
                else if(shouldSendTextFromConsole()) sendTextMessage(string);
            }
        }
        else  ConsoleHelper.writeMessage("Произошла ошибка во время работы клиента.");


    }


    public class SocketThread extends Thread
    {
        protected void processIncomingMessage(String message)
        {
            ConsoleHelper.writeMessage(message);
        }
        protected void informAboutAddingNewUser(String userName)
        {
            ConsoleHelper.writeMessage(String.format("Пользователь %s присоединился к чату",userName));
        }
        protected void informAboutDeletingNewUser(String userName)
        {
            ConsoleHelper.writeMessage(String.format("Пользователь %s покинул чат",userName));
        }

        protected void notifyConnectionStatusChanged(boolean clientConnected)
        {
            Client.this.clientConnected=clientConnected;
            synchronized (Client.this)
            {
                Client.this.notify();
            }

        }

        protected void clientHandshake() throws IOException, ClassNotFoundException
        {
            while(true)
            {
                Message m=connection.receive();
                if(m.getType()==MessageType.NAME_REQUEST)
                {
                    String name = getUserName();
                    Message outmess= new Message(MessageType.USER_NAME,name);
                    connection.send(outmess);
                }
                else if(m.getType()==MessageType.NAME_ACCEPTED)
                {
                    notifyConnectionStatusChanged(true);
                    break;
                }
                else
                {
                    throw new IOException("Unexpected MessageType");
                }

            }
        }

        protected void clientMainLoop() throws IOException, ClassNotFoundException
        {
            while(true)
            {
                Message m=connection.receive();
                if(m.getType()==MessageType.TEXT)
                   processIncomingMessage(m.getData());
                else if(m.getType()==MessageType.USER_ADDED)
                    informAboutAddingNewUser(m.getData());
                else if(m.getType()==MessageType.USER_REMOVED)
                informAboutDeletingNewUser(m.getData());
                else throw new IOException("Unexpected MessageType");
            }

        }

        @Override
        public void run()
        {

            try
            {
                String address= getServerAddress();
                int port= getServerPort();
                Socket socket=new Socket(address,port);
                connection=new Connection(socket);
                clientHandshake();
                clientMainLoop();
            } catch (IOException e) {
                notifyConnectionStatusChanged(false);
               // e.printStackTrace();
            } catch (ClassNotFoundException e) {
                notifyConnectionStatusChanged(false);
                //e.printStackTrace();
            }
        }
    }
}
