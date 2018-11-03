package com.netty;


import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;
 
/**
 * Created on 2018-08-07 20:05
 *
 * @author zhshuo
 */
public class NettyClientThread extends ChannelHandlerAdapter {
 
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx){
 
        try {
            List<UserInfo> userInfos = new ArrayList<UserInfo>();
            for(int i = 0;i < 1000;i++){
//                userInfos.add(new UserInfo().setName("name"+i).setAge(10+i));
                ctx.write(new UserInfo().setName("name"+i).setAge(10+i));
            }
            ctx.flush();
//            ctx.writeAndFlush(userInfos);
        /*String smsg = "this is a client message!"+"$_";
        byte[] bytes = smsg.getBytes();
        for(int i = 0;i < 100;i++){
//            ByteBuf buffer = Unpooled.buffer(bytes.length);
//            buffer.writeBytes(bytes);
            ctx.writeAndFlush(Unpooled.copiedBuffer(bytes));
        }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            System.out.println(msg);
        /*ByteBuf rmsg = (ByteBuf) msg;
        byte[] bytes = new byte[rmsg.readableBytes()];
        rmsg.readBytes(bytes);
        System.out.println("client received server's msg:"+new String(bytes,"UTF-8"));*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}