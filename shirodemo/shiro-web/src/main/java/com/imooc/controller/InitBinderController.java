package com.imooc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.PrintWriter;

/**
 * Created by IntelliJ IDEA.
 * User: JiaZF
 * Date: 2018/8/6
 * Time: 13:30
 * Description:
 */
@Controller
@RequestMapping("system")
public class InitBinderController {

    //pass the parameters to front-end using ajax
    @RequestMapping("/getPerson")
    public void getPerson(String name,PrintWriter pw){
        pw.write("hello,"+name);
    }

    @RequestMapping("/name")
    public String sayHello(){
        return "name";
    }

    /**
     * 局部异常 处理 有异常此方法捕获
     * @param
     * @return
     */
    /*@ExceptionHandler
    public ModelAndView exceptionHandler(Exception ex){
        ModelAndView mv = new ModelAndView();
        mv.getModel().put("exception", ex.getMessage());
        mv.setViewName("error");
        System.out.println("in testExceptionHandler");
        return mv;
    }*/

    @RequestMapping("/error")
    public String error(){
        int i = 5/0;
        return "hello";
    }

    /**
     * 通过配置web。xml 跳转错误路径
     * @return
     */
    @RequestMapping("error500")
    public String gotoError(){
        System.out.println("web.xml is ready,is coming===");
        return "error";
    }

    /**
     * 通过配置web。xml 跳转错误路径
     * @return
     */
    @RequestMapping("error404")
    public String gotoError404(){
        System.out.println("web.xml is ready,is coming===");
        return "404";
    }
}
