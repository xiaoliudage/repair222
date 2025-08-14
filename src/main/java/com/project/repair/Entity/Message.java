package com.project.repair.Entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("messages")
public class Message {
    private Integer id;
    private Integer senderId;
    private Integer receiverId;
    private String content;
    private LocalDateTime createdAt;
}
