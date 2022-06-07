package com.sangjie.seckill.mapper;

import com.sangjie.seckill.pojo.User;
import org.apache.ibatis.annotations.Mapper;

public interface UserMapper {
    User selectUserById(String mobile);
}
