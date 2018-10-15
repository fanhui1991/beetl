package com.text.modular.game.controller;

import com.text.modular.console.service.PicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by FH on 2018/8/29.
 * 图片上传
 */
@Controller
@RequestMapping("/pic")
public class PicAction {

    protected static final Logger LOG = LoggerFactory.getLogger(PicAction.class);

    private String PREFIX = "/pic/";

    @Autowired
    @Qualifier(value = "picDemoService")
    PicService picService;

    @RequestMapping(value = "/toPicPage", method = {RequestMethod.GET, RequestMethod.POST})
    public String toPicPage(Model model) throws ParseException {
        return PREFIX+"picPage.html";
    }

    @RequestMapping(value = "/addPic", method = {RequestMethod.GET, RequestMethod.POST})
    public String addPic(Model model) throws ParseException {
        return PREFIX+"addPic.html";
    }

    /**
     * 获取PIC列表
     */
    @RequestMapping(value = "/queryPicList")
    @ResponseBody
    public Object queryPicList(HttpServletRequest request, HttpServletResponse response) {
        int currentPage = request.getParameter("offset") == null ? 0 : Integer.parseInt(request.getParameter("offset"));
        int showCount = request.getParameter("limit") == null ? 10 : Integer.parseInt(request.getParameter("limit"));
        HashMap<String, Object> params = new HashMap<String, Object>();
        String pic_name = request.getParameter("pic_name");
        String status =  request.getParameter("status");
        params.put("status",status);
        params.put("pic_name",pic_name);
        params.put("currentPage",currentPage);
        params.put("showCount",showCount);
        List<Map<String, Object>> contentList = picService.queryPicList(params);
        int contentCount = picService.queryPicCount(params);
        Map teamMap = new HashMap<String,Object>();
        teamMap.put("rows", contentList);
        teamMap.put("total",contentCount);
        return teamMap;
    }

    /**
     * 获取PIC列表
     */
    @RequestMapping(value = "/appendPic")
    @ResponseBody
    public Object insertPic(HttpServletRequest request,HttpServletResponse response,@RequestParam("pic") MultipartFile file) throws Exception{
        HashMap<String, Object> params = new HashMap<String, Object>();
        String desc = request.getParameter("desc");
        String url = request.getParameter("url");
        String picname = file.getOriginalFilename();
        String Suffix=picname.split("\\.")[1];
        long l = System.currentTimeMillis();
        String pic_name = "Logo"+l+"."+Suffix;
        params.put("desc",desc);
        params.put("url",url);
        params.put("pic_name",pic_name);
        int i = picService.insertPic(params);
        String bigCutPath =  (this.getClass().getResource("/").getPath())+"static/images/Logo"+l+"."+Suffix;
        File bigCutFile = new File(bigCutPath);
        file.transferTo(bigCutFile);
        Map teamMap = new HashMap<String,Object>();
        if(i > 0){
            teamMap.put("msg","上传成功！");
        }else {
            teamMap.put("msg","上传失败！");
        }
        return params;
    }
}
