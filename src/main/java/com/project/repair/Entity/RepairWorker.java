package com.project.repair.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@TableName("repair_workers")
@AllArgsConstructor
@NoArgsConstructor
public class RepairWorker {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String username;

    private String password;

    private String repairField;

    private String phone;

    private Integer baseFee;

    private String address;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}