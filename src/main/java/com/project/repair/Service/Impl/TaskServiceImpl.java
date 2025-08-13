package com.project.repair.Service.Impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.repair.Entity.RepairTask;
import com.project.repair.Mapper.TaskMapper;
import com.project.repair.Service.TaskService;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, RepairTask> implements TaskService {
}
