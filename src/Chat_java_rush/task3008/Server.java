package Chat_java_rush.task3008;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private static Map<String, Connection> connectionMap = new ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException {

        //ConsoleHelper consoleHelper = new ConsoleHelper();
        //Socket socket = null;
        ServerSocket serverSocket = new ServerSocket(ConsoleHelper.readInt());
        System.out.println("Server Started");

        try {
            while (true){
                Socket socket = serverSocket.accept();
                Handler handler= new Handler(socket);
                handler.start();
            }
        }catch (IOException e) {
            System.out.println(e);
           // socket.close();
            serverSocket.close();
        }

    }
    public static void sendBroadcastMessage(Message message){

        for(Map.Entry<String,Connection> m:connectionMap.entrySet()) {
            try {
                m.getValue().send(message);
            } catch (IOException e) {
                System.out.println("Сообщение не было отправлено");

            }
        }
    }

    private static class Handler extends Thread{
        private Socket socket;
        private Connection connection;
        private String name;
        public Handler(Socket socket){
            this.socket=socket;
        }

        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException
        {
            Message inputMessage;
            String name;
            while(true)
            {
                connection.send(new Message(MessageType.NAME_REQUEST));
                inputMessage=connection.receive();
                name= inputMessage.getData();
                if(inputMessage.getType()==MessageType.USER_NAME && !name.isEmpty() && !connectionMap.containsKey(name))
                {
                    connectionMap.put(name,connection);
                    connection.send(new Message(MessageType.NAME_ACCEPTED));
                    return name;
                }

            }

        }
        private void notifyUsers(Connection connection, String userName) throws IOException
        {
            for (String name : connectionMap.keySet()) {
                if (!(name.equals(userName))) {
                    connection.send(new Message(MessageType.USER_ADDED,  name));
                }
            }
        }

        private void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException
        {
            Message inputMessage;
            Message outputMessage;

            while(true)
            {
                inputMessage=connection.receive();
                if(inputMessage.getType()==MessageType.TEXT)
                {
                    outputMessage= new Message(MessageType.TEXT,String.format("%s: %s",userName,inputMessage.getData()));
                    sendBroadcastMessage(outputMessage);

                } else
                {
                    ConsoleHelper.writeMessage("Ошибочный формат сообщения");
                }
            }

        }

        @Override
        public void run()
        {
            ConsoleHelper.writeMessage(String.valueOf(socket.getRemoteSocketAddress()));
            try {
                connection = new Connection(this.socket);
                name = serverHandshake(connection);
                sendBroadcastMessage(new Message(MessageType.USER_ADDED, name));
                notifyUsers(connection, name);
                serverMainLoop(connection,name);

            }
            catch (ClassNotFoundException e) {e.printStackTrace();
                ConsoleHelper.writeMessage("Произошла ошибка при обмене данными с удаленным адресом.");}
            catch (Exception e )
            {  e.printStackTrace();
               ConsoleHelper.writeMessage("Произошла ошибка при обмене данными с удаленным адресом.");}
            finally {

               if(name!=null) connectionMap.remove(name);
                sendBroadcastMessage(new Message(MessageType.USER_REMOVED,name));

            }
            ConsoleHelper.writeMessage("Соединение с удаленным адресом закрыто");

        }
    }
}
