package com.imooc.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by IntelliJ IDEA.
 * User: JiaZF
 * Date: 2018/8/8
 * Time: 16:00
 * Description:
 */
@Controller
@RequestMapping("api")
public class ApiController {

    @ResponseBody
    @RequestMapping("test")
    public String apiTest(){
        return "is api controller";
    }
}
