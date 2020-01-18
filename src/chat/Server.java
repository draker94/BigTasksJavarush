package chat;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private static Map<String, Connection> connectionMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        int port = ConsoleHelper.readInt();
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is running");
            while (true) {
                Socket socket = serverSocket.accept();
                Handler handler = new Handler(socket);
                handler.start();
            }

        } catch (Exception e) {
            System.out.println("Server is down");
            e.printStackTrace();
        }
    }

    public static void sendBroadcastMessage(Message message) {
        for (Map.Entry<String, Connection> e : connectionMap.entrySet()) {
            try {
                e.getValue().send(message);
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("Message aborting");
            }
        }
    }

    private static class Handler extends Thread {
        Socket socket;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException {
            outer:
            while (true) {
                Message request = new Message(MessageType.NAME_REQUEST, "Введите имя пользователя: ");
                connection.send(request);
                Message receive = connection.receive();
                if (!(receive.getType()).equals(MessageType.USER_NAME) || receive.getData().isEmpty()) {
                    continue;
                }
                for (Map.Entry<String, Connection> kv : connectionMap.entrySet()) {
                    if (receive.getData().equals(kv.getKey())) {
                        continue outer;
                    }
                }
                connectionMap.put(receive.getData(), connection);
                connection.send(new Message(MessageType.NAME_ACCEPTED, "Новый пользователь успешно зарегистрирован"));
                return receive.getData();
            }
        }

        private void notifyUsers(Connection connection, String userName) throws IOException, ClassNotFoundException {
            for (Map.Entry<String, Connection> kv : connectionMap.entrySet()) {
                if (!userName.equals(kv.getKey())) {
                    Message sendMessage = new Message(MessageType.USER_ADDED, kv.getKey());
                    connection.send(sendMessage);
                }
            }
        }

        private void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException {
            while (true) {
                Message message = connection.receive();
                if (message.getType() == MessageType.TEXT) {
                    Message message1 = new Message(MessageType.TEXT, userName + ": " + message.getData());
                    sendBroadcastMessage(message1);
                } else {
                    ConsoleHelper.writeMessage("Неверный тип сообщения от" + userName + ".");
                }
            }
        }

        @Override
        public void run() {
            System.out.println("Установленно новое соединение с адрессом: " + socket.getRemoteSocketAddress());
            String username = null;
            try (Connection connection = new Connection(socket)) {
                username = serverHandshake(connection);
                sendBroadcastMessage(new Message(MessageType.USER_ADDED, username));
                notifyUsers(connection, username);
                serverMainLoop(connection, username);

            } catch (IOException | ClassNotFoundException e) {
                ConsoleHelper.writeMessage("Произошла ошибка при обмене данными с удалённым адресом.");
                e.printStackTrace();
            }
            finally {
                if (username != null) {
                    connectionMap.remove(username);
                    sendBroadcastMessage(new Message(MessageType.USER_REMOVED, username));
                }
            }


        }
    }
}
