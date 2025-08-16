package com.project.repair.Controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.project.repair.Dto.MessageDto;
import com.project.repair.Dto.chatDto;
import com.project.repair.Entity.Message;
import com.project.repair.Entity.RepairWorker;
import com.project.repair.Entity.User;
import com.project.repair.Service.MessageService;
import com.project.repair.Service.RepairWorkerService;
import com.project.repair.Service.UserService;
import com.project.repair.Utils.UserContext;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/common")
public class CommonCotroller {


    @Resource
    private MessageService messageService;

    @Resource
    private UserService userService;

    @Resource
    private RepairWorkerService repairWorkerService;


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
        }else  {
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
     * 先实现维修人员界面的聊天功能，获取有自己发送的聊天记录
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

    /**
     * 先实现维修人员界面的聊天功能，获取有别人发送的聊天记录
     * */
    @GetMapping("/chat/get1")
    public List<Message> charAll1(@RequestParam Integer senderId) {

        /**
         * 标识这个用户是普通用户
         * */
        if (UserContext.getUser() != null) {
            LambdaQueryWrapper<Message> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Message::getSenderId, senderId)
                    .eq(Message::getReceiverId, UserContext.getUser().getId());
            return messageService.list(queryWrapper);
        }

        /**
         * 这个表示当前用户是维修人员
         * */
        if (UserContext.getRepair() != null) {
            LambdaQueryWrapper<Message> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Message::getSenderId, senderId)
                    .eq(Message::getReceiverId,  UserContext.getRepair().getId());
            return messageService.list(queryWrapper);
        }

        return null;
    }

    /**
     * 查看信息发
     * */
/*
    @GetMapping("/searchmessage")
    public List<Object> searchMessage(@RequestParam("id") int id) {
        // 使用Set来存储结果，自动去重
        Set<Object> resultSet = new HashSet<>();

        // 1. 查询当前用户作为发送者的消息，获取所有接收者
        LambdaQueryWrapper<Message> senderQuery = new LambdaQueryWrapper<>();
        senderQuery.eq(Message::getSenderId, id);
        List<Message> sentMessages = messageService.list(senderQuery);

        for (Message message : sentMessages) {
            Integer receiverId = message.getReceiverId();
            addUserOrRepairWorkerToResult(receiverId, resultSet);
        }

        // 2. 查询当前用户作为接收者的消息，获取所有发送者
        LambdaQueryWrapper<Message> receiverQuery = new LambdaQueryWrapper<>();
        receiverQuery.eq(Message::getReceiverId, id);
        List<Message> receivedMessages = messageService.list(receiverQuery);

        for (Message message : receivedMessages) {
            Integer senderId = message.getSenderId();
            addUserOrRepairWorkerToResult(senderId, resultSet);
        }

        // 转换为List返回
        return new ArrayList<>(resultSet);
    }

    // 辅助方法：根据ID查找用户或维修工，并添加到结果集
    private void addUserOrRepairWorkerToResult(Integer id, Set<Object> resultSet) {
        if (id == null) return;

        // 先尝试查找普通用户
        User user = userService.getById(id);
        if (user != null) {
            resultSet.add(user);
            return;
        }

        // 如果不是普通用户，尝试查找维修工
        RepairWorker repairWorker = repairWorkerService.getById(id);
        if (repairWorker != null) {
            resultSet.add(repairWorker);
        }
        // 如果两者都不是，则不添加（避免null值）
    }
*/

    @GetMapping("/searchmessage")
    public List<chatDto> searchMessage(@RequestParam("id") int id) {
        // 使用Set来存储结果，自动去重
        Set<chatDto> resultSet = new HashSet<>();

        // 1. 查询当前用户作为发送者的消息，获取所有接收者
        LambdaQueryWrapper<Message> senderQuery = new LambdaQueryWrapper<>();
        senderQuery.eq(Message::getSenderId, id);
        List<Message> sentMessages = messageService.list(senderQuery);

        for (Message message : sentMessages) {
            Integer receiverId = message.getReceiverId();
            addUserOrRepairWorkerToResult(receiverId, resultSet);
        }

        // 2. 查询当前用户作为接收者的消息，获取所有发送者
        LambdaQueryWrapper<Message> receiverQuery = new LambdaQueryWrapper<>();
        receiverQuery.eq(Message::getReceiverId, id);
        List<Message> receivedMessages = messageService.list(receiverQuery);

        for (Message message : receivedMessages) {
            Integer senderId = message.getSenderId();
            addUserOrRepairWorkerToResult(senderId, resultSet);
        }

        // 转换为List返回
        return new ArrayList<>(resultSet);
    }

    // 辅助方法：根据ID查找用户或维修工，并转换为chatDto添加到结果集
    private void addUserOrRepairWorkerToResult(Integer id, Set<chatDto> resultSet) {
        if (id == null) return;

        // 先尝试查找普通用户
        User user = userService.getById(id);
        if (user != null) {
            resultSet.add(new chatDto(user.getId(), user.getUsername(), user.getPhone(), user.getAddress()));
            return;
        }

        // 如果不是普通用户，尝试查找维修工
        RepairWorker repairWorker = repairWorkerService.getById(id);
        if (repairWorker != null) {
            resultSet.add(new chatDto(repairWorker.getId(), repairWorker.getUsername(), repairWorker.getPhone(), repairWorker.getAddress()));
        }
        // 如果两者都不是，则不添加（避免null值）
    }

}
