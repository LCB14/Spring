package com.lcb.spring.mvc.controller;

import com.lcb.spring.mvc.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author changbao.li
 * @since 15 八月 2019
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/getUser.do")
    @ResponseBody
    public Object getUser(User user){
        System.out.println(user.toString());
        return user.toString();
    }
}
