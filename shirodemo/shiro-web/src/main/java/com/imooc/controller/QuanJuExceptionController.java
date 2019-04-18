package com.imooc.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by IntelliJ IDEA.
 * User: JiaZF
 * Date: 2018/8/6
 * Time: 14:00
 * Description:
 */
@ControllerAdvice
public class QuanJuExceptionController {

    /**
     * 全局 异常。
     * @param ex
     * @return
     */
       /* @ExceptionHandler
        public ModelAndView exceptionHandler(Exception ex){
            ex.printStackTrace();
            ModelAndView mv = new ModelAndView("error");
            mv.addObject("exception", ex.getMessage()+" is quanju");
            System.out.println("in testControllerAdvice222" );
            return mv;
        }*/
}
