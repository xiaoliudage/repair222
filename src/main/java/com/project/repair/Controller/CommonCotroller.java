package com.project.repair.Controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.project.repair.Dto.MessageDto;
import com.project.repair.Entity.Message;
import com.project.repair.Entity.User;
import com.project.repair.Service.MessageService;
import com.project.repair.Utils.UserContext;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/common")
public class CommonCotroller {


    @Resource
    private MessageService messageService;


    /**
     * 私聊
     */
    @PostMapping("/chat")
    public Message chat(@RequestBody MessageDto messageDto) {

        Message message = new Message();

        if (UserContext.getUser() != null) {
            BeanUtils.copyProperties(messageDto, message);
            message.setSenderId(UserContext.getUser().getId());
            message.setCreatedAt(LocalDateTime.now());
        }

        if (UserContext.getRepair() != null) {
            BeanUtils.copyProperties(messageDto, message);
            message.setSenderId(UserContext.getRepair().getId());
            message.setCreatedAt(LocalDateTime.now());
        }

        messageService.save(message);

        return message;
    }

    /*@PostMapping("/chat/All")
    public List<Message> charAll() {
        Message message = new Message();

        *//**
         * 标识这个用户是普通用户
         * *//*
        if (UserContext.getUser() != null) {
            LambdaQueryWrapper<Message> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Message::getSenderId, UserContext.getUser().getId())
                    .or()
                    .eq(Message::getReceiverId, UserContext.getUser().getId());
            return messageService.list(queryWrapper);
        }

        *//**
         * 这个表示当前用户是维修人员
         * *//*
        if (UserContext.getRepair() != null) {
            LambdaQueryWrapper<Message> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Message::getSenderId, UserContext.getRepair().getId())
                    .or()
                    .eq(Message::getReceiverId, UserContext.getRepair().getId());
            return messageService.list(queryWrapper);
        }

        return null;

    }*/


    /**
     * 先实现维修人员界面的聊天功能
     * */
    @GetMapping("/chat/get")
    public List<Message> charAll(@RequestParam Integer receiverId) {

        /**
         * 标识这个用户是普通用户
         * */
        if (UserContext.getUser() != null) {
            LambdaQueryWrapper<Message> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Message::getSenderId, UserContext.getUser().getId())
                    .eq(Message::getReceiverId, receiverId);
            return messageService.list(queryWrapper);
        }

        /**
         * 这个表示当前用户是维修人员
         * */
        if (UserContext.getRepair() != null) {
            LambdaQueryWrapper<Message> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Message::getSenderId, UserContext.getRepair().getId())
                    .eq(Message::getReceiverId, receiverId);
            return messageService.list(queryWrapper);
        }

        return null;

    }
}
