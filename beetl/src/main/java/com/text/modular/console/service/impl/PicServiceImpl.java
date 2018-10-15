package com.text.modular.console.service.impl;

import com.text.modular.console.dao.PicDao;
import com.text.modular.console.service.PicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by FH on 2018/8/29.
 */
@Service("picDemoService")
public class PicServiceImpl implements PicService {

    @Resource
    PicDao picDao;

    @Override
    public List<Map<String, Object>> queryPicList(Map map) {
        return picDao.queryPicList(map);
    }

    @Override
    public int queryPicCount(Map<String, Object> params) {
        return picDao.queryPicCount(params);
    }

    @Override
    public int insertPic(Map<String, Object> params) {
        return picDao.insertPic(params);
    }
}
