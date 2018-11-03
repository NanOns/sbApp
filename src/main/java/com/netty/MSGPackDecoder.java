package com.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;
import org.msgpack.type.Value;

import java.util.List;

public class MSGPackDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int i = byteBuf.readableBytes();
        final byte[] bytes = new byte[i];
        byteBuf.getBytes(byteBuf.readerIndex(),bytes,0,i);
        MessagePack messagePack = new MessagePack();
        Value read = messagePack.read(bytes);
        list.add(read);
    }
}
