package com.zs.sb.service.impl;

import com.zs.sb.rabbitMQ.MessageSendUtil;
import com.zs.sb.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageSendUtil messageSendUtil;

    @Override
    public void messageSend(String content) {
        messageSendUtil.sendMessage(content);
    }
}
