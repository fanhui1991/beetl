package com.text.modular.game.controller;

import com.text.core.MenuNode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2018/6/16.
 */
@Controller
public class HomeController {

    @RequestMapping("/user")
    public String index1(Model model) {

        List<MenuNode> menus = new ArrayList<MenuNode>();
        MenuNode gameParent = new MenuNode();
        gameParent.setId(1);
        gameParent.setParentId(0);
        gameParent.setName("Demo系统");
        gameParent.setLevels(1);
        gameParent.setIsmenu(1);
        gameParent.setNum(1);
        menus.add(gameParent);

        MenuNode gameOrder = new MenuNode();
        gameOrder.setId(190);
        gameOrder.setParentId(1);
        gameOrder.setName("首页管理");
        gameOrder.setUrl("/demo/toDemoContentListPage");
        gameOrder.setLevels(2);
        gameOrder.setIsmenu(1);
        gameOrder.setNum(1);
        menus.add(gameOrder);

        MenuNode gamePic = new MenuNode();
        gamePic.setId(191);
        gamePic.setParentId(1);
        gamePic.setName("图片管理");
        gamePic.setUrl("/pic/toPicPage");
        gamePic.setLevels(2);
        gamePic.setIsmenu(1);
        gamePic.setNum(2);
        menus.add(gamePic);

        List<MenuNode> titles = MenuNode.buildTitle(menus);
        model.addAttribute("titles", titles);

        return "/index.html";
    }


    @RequestMapping("/blackboard")
    public String blackboard(){
        return "/blackboard.html";
    }

}
