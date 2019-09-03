package chat_socket_v2_notwork;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private static ServerSocket server; // серверсокет
    private static ArrayList<Clients> clients=new ArrayList<>();

    public static void main(String[] args) throws IOException
    {

           try {
               server = new ServerSocket(4004); // серверсокет прослушивает порт 4004
               System.out.println("Сервер запущен! и ждет клиентов для подключения"); // хорошо бы серверу
               //   объявить о своем запуске
               // слушаем бесконечно новых клиентов. при постукивании
               // в 4004 создаем сокет соединение с клиентом и добавляем в спискок клиентов
               int clientscount = 0;
               while (true) {
                   Socket socket = server.accept(); // accept() будет ждать пока
                   //кто-нибудь не захочет подключиться
                   clientscount++;
                   System.out.println("Обнаружен и подключен клиент: " + clientscount);
                   try {
                       clients.add(new Clients(socket, clientscount));
                   } catch (IOException e) {
                       // Если завершится неудачей, закрывается сокет,
                       // в противном случае, нить закроет его при завершении работ
                       socket.close();
                   }
               }
           } finally {
               server.close();
           }
    }
    private static class Clients extends Thread
    {
        private  Socket clientSocket; //сокет для общения
        private  BufferedReader in; // поток чтения из сокета
        private  BufferedWriter out; // поток записи в
        private  int clientnumber=0;

        public Clients(Socket clientSocket, int clientnumber) throws IOException {
            this.clientSocket = clientSocket;
            this.clientnumber = clientnumber;
            // установив связь и воссоздав сокет для общения с клиентом можно перейти
            // к созданию потоков ввода/вывода.
            // теперь мы можем принимать сообщения
            // и отправлять
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            //запускаем нить обработки связи по сокету с конкретным клиентом
            this.start();
        }

        @Override
        public void run() //cлушаем клиента и преесылаем всем его сообщения пока он не написал
                //экзит
        {
            while (true)
            {
                String word = null; // ждём пока клиент что-нибудь нам напишет
                try {
                    word = in.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.printf("Клиент %d написал: %s",clientnumber,word);
                // не долго думая отвечает клиенту
                if(word.equals("exit"))
                {
                    try {
                        in.close();
                        out.close();
                        clientSocket.close();
                    } catch (IOException e) {e.printStackTrace();}
                    break;
                }
                else {
                    for(Clients c:clients) c.sendMsg(word);
                }
            }
        }
        private void sendMsg(String msg)
        {
            try {
                out.write(String.format("%d : %s %n",clientnumber,msg));
                out.flush(); // выталкиваем все из буфера
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }





}