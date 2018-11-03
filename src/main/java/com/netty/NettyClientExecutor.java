package com.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
 
/**
 * Created on 2018-08-07 18:00
 *
 * @author zhshuo
 */
public class NettyClientExecutor {
 
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
 
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY,true)
            .handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                //使用line.separator分隔符
                //socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
                //使用自定义得分隔符
                /*ByteBuf byteBuf = Unpooled.copiedBuffer("$_".getBytes());
                socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,byteBuf));*/
                //socketChannel.pipeline().addLast(new FixedLengthFrameDecoder(10));
                //socketChannel.pipeline().addLast(new StringDecoder());
 
                //使用msgpack定义得编码和解码器
                socketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(65535,0,2,0,2));
                socketChannel.pipeline().addLast(new MSGPackDecoder());
 
                socketChannel.pipeline().addLast(new LengthFieldPrepender(2));
                socketChannel.pipeline().addLast(new MSGPackEncoder());
 
                socketChannel.pipeline().addLast(new NettyClientThread());
 
            }
            });
            ChannelFuture future = bootstrap.connect("127.0.0.1", 9000).sync();
            future.channel().closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
 
 
    }
 
}