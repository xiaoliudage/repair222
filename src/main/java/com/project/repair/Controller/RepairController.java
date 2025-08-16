package com.project.repair.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.project.repair.Dto.LoginDto;
import com.project.repair.Dto.TaskDto;
import com.project.repair.Dto.UpdateRepairWorker;
import com.project.repair.Dto.UpdateUser;
import com.project.repair.Entity.RepairTask;
import com.project.repair.Entity.RepairWorker;
import com.project.repair.Entity.User;
import com.project.repair.Service.RepairWorkerService;
import com.project.repair.Service.TaskService;
import com.project.repair.Utils.JwtTokenUtil;
import com.project.repair.Utils.UserContext;
import org.apache.tomcat.Jar;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/repair")
public class RepairController {

    @Autowired
    private RepairWorkerService repairService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

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
    public Map<String, Object> login(@RequestBody LoginDto loginDto) {
        LambdaQueryWrapper<RepairWorker> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RepairWorker::getUsername, loginDto.getUsername());
        RepairWorker user1 = repairService.getOne(queryWrapper);

        if(user1 ==null ||!user1.getPassword().equals(loginDto.getPassword())){
            return null;
        }

        String token = jwtTokenUtil.generateToken(user1.getUsername());

        /**
         * 获取过期时间
         * */
        Date expiration = jwtTokenUtil.getAllClaimsFromToken(token).getExpiration();
        long expiresIn = expiration.getTime() - new Date().getTime();

        /**
         * 设置返回数据
         * */
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("user", user1);

        return map;
    }

    /**
     * 维修任务发布
     * */
    @PostMapping("/publish")
    public RepairTask publish(@RequestBody TaskDto taskDto) {
        RepairTask repairTask = new RepairTask();
        BeanUtils.copyProperties(taskDto, repairTask);
        if(UserContext.getUser()!=null){
            User user = UserContext.getUser();
            repairTask.setPublisherName(user.getUsername());
            repairTask.setPublisherPhone(user.getPhone());
            repairTask.setPublisherAddress(user.getAddress());
        }else{
            RepairWorker user = UserContext.getRepair();
            repairTask.setPublisherName(user.getUsername());
            repairTask.setPublisherPhone(user.getPhone());
            repairTask.setPublisherAddress(user.getAddress());
        }

        repairTask.setPublishTime(new Date());
        repairTask.setStatus(0);
        repairTask.setUpdateTime(new Date());
        taskService.save(repairTask);
        return repairTask;
    }

    /**
     * 修改个人信息
     * */
    @PostMapping("/update")
    public RepairWorker update(@RequestBody UpdateRepairWorker updateRepairWorker) {
        Integer id = UserContext.getRepair().getId();
        RepairWorker repairWorker = new RepairWorker();
        BeanUtils.copyProperties(updateRepairWorker, repairWorker);
        repairWorker.setId(id);
        repairWorker.setUpdatedAt(LocalDateTime.now());
        repairService.updateById(repairWorker);
        return repairWorker;
    }

    /**
     * 修改个人信息时，进行用户回显
     * */
    @GetMapping("/update/getById")
    public UpdateRepairWorker updateGetById(@RequestParam("id") Integer id) {
        UpdateRepairWorker updateRepairWorker = new UpdateRepairWorker();
        RepairWorker repairWorker = repairService.getById(id);
        BeanUtils.copyProperties(repairWorker, updateRepairWorker);
        return updateRepairWorker;
    }
}
