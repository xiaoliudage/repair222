package com.project.repair.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private String taskTitle;//'任务标题'
    private String serviceType;//'服务类型'
    private String taskDescription;//'任务描述'
    private BigDecimal budgetAmount;//'预算金额'
}
