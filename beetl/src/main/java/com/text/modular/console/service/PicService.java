package com.text.modular.console.service;

import java.util.List;
import java.util.Map;

/**
 * Created by FH on 2018/8/29.
 */
public interface PicService {

    List<Map<String,Object>> queryPicList(Map map);

    int queryPicCount(Map<String, Object> params);

    int insertPic(Map<String, Object> params);
}
