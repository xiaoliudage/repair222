package com.project.repair.Controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.project.repair.Dto.LoginDto;
import com.project.repair.Dto.UpdateUser;
import com.project.repair.Entity.RepairWorker;
import com.project.repair.Entity.User;
import com.project.repair.Service.RepairWorkerService;
import com.project.repair.Service.UserService;
import com.project.repair.Utils.JwtTokenUtil;
import com.project.repair.Utils.UserContext;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final RepairWorkerService repairWorkerService;

    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/add")
    public User add(@RequestBody User user) {
        User user1 = new User();
        user1.setUsername(user.getUsername());
        user1.setPassword(user.getPassword());
        user1.setCreatedAt(LocalDateTime.now());
        user1.setUpdatedAt(LocalDateTime.now());
        user1.setPhone(user.getPhone());
        user1.setAddress(user.getAddress());
        userService.save(user1);
        return user1;
    }

    @PostMapping("/repair_add")
    public RepairWorker repair_add(@RequestBody RepairWorker repairWorker) {
        RepairWorker user1 = new RepairWorker();
        user1.setUsername(repairWorker.getUsername());
        user1.setPassword(repairWorker.getPassword());
        user1.setRepairField(repairWorker.getRepairField());
        user1.setPhone(repairWorker.getPhone());
        user1.setBaseFee(repairWorker.getBaseFee());
        user1.setAddress(repairWorker.getAddress());
        user1.setCreatedAt(LocalDateTime.now());
        user1.setUpdatedAt(LocalDateTime.now());
        repairWorkerService.save(user1);
        return user1;
    }

    /**
     * 获取用户详情
     * */
    @GetMapping("/getById")
    public User getById(@RequestParam("id") Integer id) {
        return userService.getById(id);
    }

    /**
     * 获取维修人员详情
     * */
    @GetMapping("/getpairById")
    public RepairWorker getrepairById(@RequestParam("id") Integer id) {
        return repairWorkerService.getById(id);
    }

    /**
     * 用户登录
     * */
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginDto loginDto) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, loginDto.getUsername());
        User user1 = userService.getOne(queryWrapper);

        if(user1==null ||!user1.getPassword().equals(loginDto.getPassword())){
            return null;
        }

        String token = jwtTokenUtil.generateToken(user1.getUsername());

        /**
         * 设置返回数据
         * */
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("user", user1);

        return map;
    }


    /**
     * 修改个人信息
     * */
    @PostMapping("/update")
    public User update(@RequestBody UpdateUser updateUser) {
        Integer id = UserContext.getUser().getId();
        User user = new User();
        BeanUtils.copyProperties(updateUser, user);
        user.setId(id);
        user.setUpdatedAt(LocalDateTime.now());
        userService.updateById(user);
        return user;
    }

    /**
     * 修改个人信息时，进行用户回显
     * */
    @GetMapping("/update/getById")
    public UpdateUser updateGetById(@RequestParam("id") Integer id) {
        UpdateUser updateUser = new UpdateUser();
        User user = userService.getById(id);
        BeanUtils.copyProperties(user, updateUser);
        return updateUser;
    }
}
