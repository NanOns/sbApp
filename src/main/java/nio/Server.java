package nio;

public class Server {
    public static void main(String[] args) {
        new Thread(new ServerHandler()).start();
    }
}
