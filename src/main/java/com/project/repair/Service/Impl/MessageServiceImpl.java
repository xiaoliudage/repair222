package com.project.repair.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.repair.Entity.Message;
import com.project.repair.Mapper.MessageMapper;
import com.project.repair.Service.MessageService;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService{
}
