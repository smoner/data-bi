package com.yonyou.yuncai.cpu.bi.user.service;

import com.yonyou.yuncai.cpu.bi.user.entity.Users;

import java.util.List;

/**
 * Created by fengjqc on 2017/3/20.
 */
public interface IUsersService {
    public Users getUserById(Integer id);
    public int insert(Users users);
    public int insertUsersBatch(List<Users> usersList);
    public int updateString2Json();
}
