package com.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * Created on 2018-08-07 17:38
 *
 * @author zhshuo
 */
public class NettyServerExecutor {
  
    public static void main(String[] args) throws Exception {
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
  
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(parentGroup, workerGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 1024)
            .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                //使用line.separator分隔符
                //socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
                //使用自定义得分隔符
                /*ByteBuf byteBuf = Unpooled.copiedBuffer("$_".getBytes());
                socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,byteBuf));*/
 
                //使用固定长度得消息体,如果消息超长,会自动截取,如果消息长度不足,会自动等待,直到达到设定得长度
                //socketChannel.pipeline().addLast(new FixedLengthFrameDecoder(10));
 
                //Stirng类型得编码器
                //socketChannel.pipeline().addLast(new StringDecoder());
 
                //使用msgpack定义得编码和解码器
 
                //在msgpack解码器之前添加LengthFieldBasedFrameDecoder用于处理半包消息
                socketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));
                socketChannel.pipeline().addLast(new MSGPackDecoder());
 
                //在msgoack编码器之前添加LengthFieldPrepender,它将在bytebuf之前新增2个字节得消息长度字段
                socketChannel.pipeline().addLast(new LengthFieldPrepender(2));
                socketChannel.pipeline().addLast(new MSGPackEncoder());
 
                socketChannel.pipeline().addLast(new NettyServerChannelHandler());
                }
            });
            ChannelFuture future = serverBootstrap.bind("127.0.0.1", 9000).sync();
            System.out.println("Server Has start up,waiting for client....");
            future.channel().closeFuture().sync();
        } finally {
            // 优雅退出，释放线程池资源
            parentGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
  
}