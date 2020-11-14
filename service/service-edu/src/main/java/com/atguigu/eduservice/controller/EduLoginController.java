package com.atguigu.eduservice.controller;/*
 *
 *   @date 2020/11/11 8:50
 *    管理员登陆接口
 */

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(description = "管理员登陆")
@CrossOrigin        // 解决跨域问题
@RestController
@RequestMapping("/eduservice/user")
public class EduLoginController {

    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }

    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","admin");
    }
}
