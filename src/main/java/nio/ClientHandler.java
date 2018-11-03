package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ClientHandler implements Runnable {
    SocketChannel channel;
    Selector selector;
    public ClientHandler() {
        try {
            channel = SocketChannel.open();
            channel.configureBlocking(false);
            selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        connect();

        while(true){
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while(iterator.hasNext()){
                SelectionKey next = iterator.next();
                iterator.remove();
                if(next.isValid()){
                    SocketChannel channel = (SocketChannel) next.channel();
                    if(next.isConnectable()){
                        try {
                            if(channel.finishConnect()){
                                channel.register(selector,SelectionKey.OP_READ);
                                ByteBuffer buffer = ByteBuffer.allocate(1024);
                                buffer.put("this is Client".getBytes());
                                buffer.flip();
                                channel.write(buffer);
                                if(!buffer.hasRemaining()){
                                    System.out.println("client send over");
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(next.isReadable()){
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        try {
                            int read = channel.read(buffer);
                            if(read > 0){
                                buffer.flip();
                                byte[] bytes = new byte[buffer.remaining()];
                                buffer.get(bytes);
                                String body = new String(bytes, "UTF-8");
                                System.out.println("Now is "+body);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }

    }

    public void connect(){
        try {
            boolean connect = channel.connect(new InetSocketAddress(8080));
            if(connect){
                channel.register(selector, SelectionKey.OP_READ);
            }else{
                channel.register(selector, SelectionKey.OP_CONNECT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
