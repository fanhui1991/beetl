package com.text.modular.console.controller;

import com.text.modular.console.service.UserListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by FH on 2018/8/27.
 */

@Controller
@RequestMapping("/")
public class UserController {

    protected final static Logger Log = LoggerFactory.getLogger(UserController.class);

    private static String PREFIX = "/boKe/";

    @Autowired
    @Qualifier(value = "userService")
    UserListService userListService;


    @RequestMapping("")
    public String  index(Model model) throws Exception{
        HashMap<String,Object> resMap = new HashMap<String,Object>();
        resMap.put("USER_ID","8690045");
        List<Map<String, Object>> list = userListService.queryUserFistList(resMap);
        model.addAttribute("list",list.get(0));
        model.addAttribute("content",list);
        return PREFIX + "boKeDome.html";
    }

    /**
     *相册
     */
    @RequestMapping("album")
    public String  album(Model model) throws Exception{
        HashMap<String,Object> resMap = new HashMap<String,Object>();
        resMap.put("USER_ID","8690045");
        List<Map<String, Object>> list = userListService.queryUserFistList(resMap);
        List<Map<String, Object>> bannerList = userListService.queryUserBannerList(resMap);
        List<Map<String, Object>> newBannerList = userListService.queryNewUserBannerList(resMap);
        model.addAttribute("list",list.get(0));
        model.addAttribute("bannerList",bannerList);
        model.addAttribute("newBannerList",newBannerList);
        return PREFIX + "albumDemo.html";
    }

    /**
     *全文
     */
    @RequestMapping("/postInfo/{id}")
    public String postInfo(@PathVariable String id, Model model) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("content_id",id);
        params.put("USER_ID","8690045");
        List<Map<String, Object>> list = userListService.queryUserFistList(params);
        model.addAttribute("list",list.get(0));
        List<Map<String, Object>> List = userListService.queryContentList(params);
        model.addAttribute("content", List.get(0));
        return PREFIX + "postInfo.html";
    }
}
