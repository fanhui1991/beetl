package com.text.modular.console.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by FH on 2018/8/7.
 */
public interface PicDao {

    List<Map<String,Object>> queryPicList(Map map);

    int queryPicCount(Map<String, Object> params);

    int insertPic(Map<String, Object> params);
}
