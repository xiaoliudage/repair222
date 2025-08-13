package com.project.repair.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.repair.Entity.RepairWorker;
import com.project.repair.Mapper.RepairWorkerMapper;
import com.project.repair.Service.RepairWorkerService;
import org.springframework.stereotype.Service;

@Service
public class RepairWorkerServiceImpl extends ServiceImpl<RepairWorkerMapper, RepairWorker> implements RepairWorkerService {
}
