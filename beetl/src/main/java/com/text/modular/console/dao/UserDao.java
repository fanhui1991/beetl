package com.text.modular.console.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by FH on 2018/8/27.
 */
public interface UserDao {

    List<Map<String,Object>> queryUserFistList(Map map);

    List<Map<String,Object>> queryUserBannerList(Map map);

    List<Map<String,Object>> queryNewUserBannerList(Map map);

    List<Map<String,Object>> queryContentList(Map map);

    int queryContentCount(Map<String, Object> params);

    int insertContent(Map<String, Object> params);

    int contentUpdate(Map<String, Object> params);
}
