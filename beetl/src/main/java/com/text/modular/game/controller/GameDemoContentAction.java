package com.text.modular.game.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.text.modular.console.service.UserListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by fh
 */
@Controller
@RequestMapping("/demo")
public class GameDemoContentAction {
    protected static final Logger LOG = LoggerFactory.getLogger(GameDemoContentAction.class);

    private String PREFIX = "/demo/";


    @Autowired
    @Qualifier(value = "userService")
    UserListService userListService;

    @RequestMapping(value = "/toDemoContentListPage", method = {RequestMethod.GET, RequestMethod.POST})
    public String toDemoContentListPage(Model model) throws ParseException {
        return PREFIX+"toDemoContentList.html";
    }
    /**
     * 获取订单列表
     */
    @RequestMapping(value = "/queryContentList")
    @ResponseBody
    public Object queryContentList(HttpServletRequest request, HttpServletResponse response) {
        int currentPage = request.getParameter("offset") == null ? 0 : Integer.parseInt(request.getParameter("offset"));
        int showCount = request.getParameter("limit") == null ? 10 : Integer.parseInt(request.getParameter("limit"));
        HashMap<String, Object> params = new HashMap<String, Object>();
        String content_id = request.getParameter("content_id");
        String status =  request.getParameter("status");
        params.put("status",status);
        params.put("content_id",content_id);
        params.put("currentPage",currentPage);
        params.put("showCount",showCount);
        List<Map<String, Object>> contentList = userListService.queryContentList(params);
        int contentCount = userListService.queryContentCount(params);
        Map teamMap = new HashMap<String,Object>();
        teamMap.put("rows", contentList);
        teamMap.put("total",contentCount);
        return teamMap;
    }

    /**
     * 跳转详情页面
     */
    @RequestMapping("/contentPostInfo/{id}")
    public String postDetails(@PathVariable String id, Model model) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("content_id",id);
        List<Map<String, Object>> List = userListService.queryContentList(params);
        model.addAttribute("list", List.get(0));
        return PREFIX + "contentPostInfo.html";
    }

    /**
     * 跳转添加页面
     */
    @RequestMapping("/addContent")
    public String addContent(Model model) {
        return PREFIX + "addContentPost.html";
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/contentInsert")
    @ResponseBody
    public Object contentInsert(HttpServletRequest request, HttpServletResponse response) throws Exception{
        HashMap<String, Object> params = new HashMap<String, Object>();
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        byte[] bytes =String.valueOf(content).getBytes("UTF-8");
        params.put("bytes",bytes);
        params.put("title",title);
        params.put("user_id","8690045");
        int i = userListService.insertContent(params);
        Map<String,Object> responseMap = new HashMap<String,Object>();
        responseMap.put("code",i);
        return  responseMap;
    }

    /**
     *  编辑
     */
    @RequestMapping(value = "/contentUpdate")
    @ResponseBody
    public Object contentUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception{
        HashMap<String, Object> params = new HashMap<String, Object>();
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String id = request.getParameter("id");

        byte[] bytes =String.valueOf(content).getBytes("UTF-8");
        params.put("bytes",bytes);
        params.put("title",title);
        params.put("id",id);
        int i = userListService.contentUpdate(params);
        Map<String,Object> responseMap = new HashMap<String,Object>();
        responseMap.put("code",i);
        return  responseMap;
    }

    /**
     * 上传图片
     */
    @RequestMapping(value = "/addPic")
    @ResponseBody
    public Object addPic(HttpServletRequest request,HttpServletResponse response,@RequestParam("imgFile") MultipartFile file) throws Exception{
        HashMap<String, Object> params = new HashMap<String, Object>();
        Properties props = PropertiesLoaderUtils.loadAllProperties("application.properties");
		String url= (String) props.get("img.imgPath");
        String picname = file.getOriginalFilename();
        String Suffix=picname.split("\\.")[1];
        long l = System.currentTimeMillis();
        String pic_name = "Logo"+l+"."+Suffix;
        params.put("pic_name",pic_name);
        String bigCutPath =  (this.getClass().getResource("/").getPath())+"static/images/Logo"+l+"."+Suffix;
        File bigCutFile = new File(bigCutPath);
        file.transferTo(bigCutFile);
        JSONObject obj = new JSONObject();
        obj.put("error", 0);
        obj.put("msg","上传成功");
        obj.put("url",url+"static/images/Logo"+l+"."+Suffix);
        return obj;
    }
}
