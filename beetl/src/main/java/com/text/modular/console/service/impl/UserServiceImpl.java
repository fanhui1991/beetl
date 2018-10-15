package com.text.modular.console.service.impl;

import com.text.modular.console.dao.UserDao;
import com.text.modular.console.service.UserListService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by FH on 2018/8/27.
 */
@Service("userService")
public class UserServiceImpl implements UserListService {

    @Resource
    UserDao userDao;

    @Override
    public List<Map<String, Object>> queryUserFistList(Map map) {
        List<Map<String, Object>> maps = userDao.queryUserFistList(map);
        return maps;
    }

    @Override
    public List<Map<String, Object>> queryUserBannerList(Map map) {
        List<Map<String, Object>> maps = userDao.queryUserBannerList(map);
        return maps;
    }

    @Override
    public List<Map<String, Object>> queryNewUserBannerList(Map map) {
        List<Map<String, Object>> maps = userDao.queryNewUserBannerList(map);
        return maps;
    }

    @Override
    public List<Map<String, Object>> queryContentList(Map map) {
        List<Map<String, Object>> maps = userDao.queryContentList(map);
        return maps;
    }

    @Override
    public int queryContentCount(Map<String, Object> params) {
        return userDao.queryContentCount(params);
    }

    @Override
    public int insertContent(Map<String, Object> params) {
        return userDao.insertContent(params);
    }
    @Override
    public int contentUpdate(Map<String, Object> params) {
        return userDao.contentUpdate(params);
    }
}
