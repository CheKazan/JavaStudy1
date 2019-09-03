package chat_socket_v2_notwork;

import java.io.*;
import java.net.Socket;

public class Client {

    private static Socket clientSocket; //сокет для общения
    private static readMsg readMsg;
    private static writeMsg writeMsg;
    private static  BufferedReader reader; // нам нужен ридер читающий с консоли, иначе как
    // мы узнаем что хочет сказать клиент?
    private static   BufferedWriter out; // поток записи в сокет
    private static BufferedReader in; // поток чтения из сокета


    public static void main(String[] args) {
        try {
            try {
                // адрес - локальный хост, порт - 4004, такой же как у сервера
                clientSocket = new Socket("localhost", 4004); // этой строкой мы запрашиваем
                //  у сервера доступ на соединение
                System.out.println("Connected to server");
                reader = new BufferedReader(new InputStreamReader(System.in)); //консоль
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                readMsg= new readMsg();
                writeMsg= new writeMsg();
            } finally { // в любом случае необходимо закрыть сокет и потоки
                System.out.println("Клиент был закрыт...");
                clientSocket.close();

            }
        } catch (IOException e) {
            System.err.println(e);
        }

    }

    public static class readMsg extends Thread // читать соообщения с сервера
    {
       public readMsg() throws IOException { this.run();}

        @Override
        public void run() {
            try
            {
                while(true)
                {
                String serverWord = null; // ждём, что скажет сервер
                serverWord = in.readLine();
                System.out.println(serverWord); // получив - выводим на экран
                }
            } catch (IOException e) {e.printStackTrace();}
        }
    }
    public static class writeMsg extends Thread
    {
        public writeMsg() throws IOException {
            this.run();
        }

        @Override
        public void run() {
            try{
                while(true) {
                    String word = reader.readLine(); // ждём пока клиент что-нибудь
                    // не напишет в консоль
                    if (word.equals("exit")) {
                        try {
                            out.close();
                            reader.close();
                            clientSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    else{
                        out.write(word + "\n"); // отправляем сообщение на сервер
                        out.flush();
                    }
                }
            } catch (IOException e) {e.printStackTrace();}
        }
    }

}