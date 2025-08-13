package com.project.repair.Entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("repair_task")
public class RepairTask {
    private Integer id;
    private String publisherName;//'发布人名称'
    private String publisherPhone;//'发布人电话'
    private String publisherAddress;//'发布人地址'
    private String taskTitle;//'任务标题'
    private String serviceType;//'服务类型'
    private String taskDescription;//'任务描述'
    private BigDecimal budgetAmount;//'预算金额'
    private Date publishTime;//'发布时间'
    private Integer status;//'任务状态(0:待接单,1:已接单,2:已完成,3:已取消)'
    private Date updateTime;//'更新时间'

}