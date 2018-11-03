package nio;

public class Client {

    public static void main(String[] args) {
        new Thread(new ClientHandler()).start();
    }

}
