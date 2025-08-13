package com.project.repair.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.repair.Entity.User;
import com.project.repair.Mapper.UserMapper;
import com.project.repair.Service.UserService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {
}
