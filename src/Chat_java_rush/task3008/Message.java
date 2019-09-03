package Chat_java_rush.task3008;

import java.io.Serializable;

public class Message implements Serializable {
    private final MessageType type;
    private final String data;

    public MessageType getType() {
        return type;
    }
    public String getData() {
        return data;
    }

    public Message(MessageType type) {
        this.type = type;
        data=null;
    }

    public Message(MessageType type, String data) {
        this.type = type;
        this.data = data;
    }
}
/*
Чат (4)
Сообщение Message - это данные, которые одна сторона отправляет, а вторая принимает.
Каждое сообщение должно иметь тип MessageType, а некоторые и дополнительные
данные, например, текстовое сообщение должно содержать текст. Т.к. сообщения будут
создаваться в одной программе, а читаться в другой, удобно воспользоваться механизмом
сериализации для перевода класса в последовательность битов и наоборот.

Добавь в класс Message:
1) Поддержку интерфейса Serializable.
2) final поле типа MessageType type, которое будет содержать тип сообщения.
3) final поле типа String data, которое будет содержать данные сообщения.
4) Геттеры для этих полей.
5) Конструктор, принимающий только MessageType, он должен проинициализировать
поле type переданным параметром, а поле data оставить равным null.
6) Конструктор, принимающий MessageType type и String data. Он должен также
инициализировать все поля класса.


Требования:
1. Класс Message должен поддерживать интерфейс Serializable.
2. Приватное поле type в классе Message должно быть объявлено с модификатором final и быть типа MessageType.
3. Приватное поле data в классе Message должно быть объявлено с модификатором final и быть типа String.
4. В классе Message должны быть созданы корректные геттеры для полей data и type.
5. Конструктор класса Message с одним параметром типа MessageType должен инициализировать поле type и оставлять поле data равным null.
6. Конструктор класса Message с двумя параметрами (MessageType и String) должен инициализировать поля type и data.
 */