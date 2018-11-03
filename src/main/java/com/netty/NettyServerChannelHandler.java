package com.netty;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
 
/**
 * Created on 2018-08-07 17:42
 *
 * @author zhshuo
 */
public class NettyServerChannelHandler extends ChannelHandlerAdapter {
 
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)  {
        try {
            System.out.println(msg);
 
            /*ByteBuf byteBuf = (ByteBuf) msg; //这个当没有配置StringDecoder解码器得时候
            byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            System.out.println("server received msg:"+new String(bytes,"UTF-8"));*/
 
            /*byte[] smgb = ("this is a server message!"+"$_").getBytes();
            ByteBuf buffer = Unpooled.copiedBuffer(smgb);
            ctx.writeAndFlush(buffer);*/
            ctx.writeAndFlush(new UserInfo().setAge(12323).setName("zhshuo"));
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}