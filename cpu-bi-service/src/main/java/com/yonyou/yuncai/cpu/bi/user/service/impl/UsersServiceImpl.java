package com.yonyou.yuncai.cpu.bi.user.service.impl;

import com.yonyou.yuncai.cpu.bi.user.entity.Users;
import com.yonyou.yuncai.cpu.bi.user.mapper.UsersMapper;
import com.yonyou.yuncai.cpu.bi.user.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fengjqc on 2017/3/20.
 */
@Service
public class UsersServiceImpl implements IUsersService {

    @Autowired
    private UsersMapper usersMapper;
    public Users getUserById(Integer id) {
        return usersMapper.selectByPrimaryKey(id);
    }

    public int insert(Users users) {
        return usersMapper.insert(users);
    }

    public int insertUsersBatch(List<Users> usersList) {
        return usersMapper.insertUsersBatch(usersList);
    }

    public int updateString2Json() {
        return usersMapper.updateString2Json();
    }

}