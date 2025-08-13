package com.project.repair.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.project.repair.Dto.LoginDto;
import com.project.repair.Dto.TaskDto;
import com.project.repair.Entity.RepairTask;
import com.project.repair.Entity.RepairWorker;
import com.project.repair.Entity.User;
import com.project.repair.Service.RepairWorkerService;
import com.project.repair.Service.TaskService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/repair")
public class RepairController {

    @Autowired
    private RepairWorkerService repairService;

    @Autowired
    private TaskService taskService;

    /**
     * 获取所有维修人员
     * */
    @GetMapping("/getAll")
    public List<RepairWorker> getAll() {
        return repairService.list();
    }

    /**
     * 获取用户详情
     * */
    @GetMapping("/getById/{id}")
    public RepairWorker getById(@PathVariable("id") Integer id) {
        return repairService.getById(id);
    }

    /**
     * 查找服务
     * */
    @GetMapping("/find")
    public List<RepairWorker> find(@RequestParam String type) {
        LambdaQueryWrapper<RepairWorker> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(RepairWorker::getRepairField,type);
        return repairService.list(queryWrapper);
    }

    /**
     * 维修人员登录
     * */
    @PostMapping("/login")
    public RepairWorker login(@RequestBody LoginDto loginDto) {
        LambdaQueryWrapper<RepairWorker> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RepairWorker::getUsername, loginDto.getUsername());
        RepairWorker user1 = repairService.getOne(queryWrapper);
        if (user1 == null) {
            return null;
        }
        if(!user1.getPassword().equals(loginDto.getPassword())){
            return null;
        }
        return user1;
    }

    /**
     * 维修任务发布
     * */
    public void publish(@RequestBody TaskDto taskDto) {
        RepairTask repairTask = new RepairTask();
        BeanUtils.copyProperties(taskDto, repairTask);
        repairTask.setPublisherName("张三");
        repairTask.setPublisherPhone("13800000000");
        repairTask.setPublisherAddress("北京市海淀区");
        repairTask.setPublishTime(new Date());
        repairTask.setStatus(0);
        repairTask.setUpdateTime(new Date());
        taskService.save(repairTask);
    }
}
