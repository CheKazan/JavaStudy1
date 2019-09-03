package Chat_java_rush.task3008;


import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketAddress;

public class Connection implements Closeable {
    private final Socket socket;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;


    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        out= new ObjectOutputStream(socket.getOutputStream());
        in= new ObjectInputStream(socket.getInputStream());
    }
    //Метод send должен вызывать метод writeObject на объекте out с параметром message.
    public void send(Message message) throws IOException{
        synchronized (out){
            out.writeObject(message);
            out.flush();
        }
    }
    //Метод receive должен возвращать сообщение Message полученное из ObjectInputStream с помощью метод readObject.
    public   Message receive() throws IOException, ClassNotFoundException{
        Message message;
        synchronized (in) {
            message = (Message) this.in.readObject();
        }
        return message; }
    public SocketAddress getRemoteSocketAddress(){
        return socket.getRemoteSocketAddress();
    }
    public void close() throws IOException{
        in.close();
        out.close();
        socket.close();
    }


}
/*



5) Метод SocketAddress getRemoteSocketAddress(), возвращающий удаленный адрес
сокетного соединения.
6) Метод void close() throws IOException, который должен закрывать все ресурсы класса.

Класс Connection должен поддерживать интерфейс Closeable.


Требования:
1. Класс Connection должен поддерживать интерфейс Closeable.
2. В классе Connection должно быть создано private final поле socket типа Socket.
3. В классе Connection должно быть создано private final поле out типа ObjectOutputStream.
4. В классе Connection должно быть создано private final поле in типа ObjectInputStream.
5. В классе Connection должен быть создан конструктор с одним параметром типа Socket инициализирующий поля класса в соответствии с условием задачи.
6. В классе Connection должен быть корректно реализован метод send c одним параметром типа Message.
7. В классе Connection должен быть корректно реализован метод receive без параметров.
8. Метод getRemoteSocketAddress класса Connection должен возвращать удаленный адрес сокетного соединения.
9. Метод close класса Connection должен закрывать потоки чтения, записи и сокетное соединение.
 */