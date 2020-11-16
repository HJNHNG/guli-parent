package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2020-11-03
 */
@Api(description = "讲师管理")
@CrossOrigin        // 解决跨域问题
@RestController
@RequestMapping("/eduservice/edu-teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    // 1.查询所有讲师数据
    @ApiOperation(value = "查询所有讲师")
    @GetMapping("findAll")
    public R findAll(){
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items",list);
    }

    // 2.根据id删除讲师
    @ApiOperation(value = "根据id删除讲师")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id",value = "讲师id",required = true)
            @PathVariable String id){
        boolean result = eduTeacherService.removeById(id);
        if (result){
            return R.ok();
        }else {
            return R.error().message("删除失败");
        }
    }

    // 3.分页查询讲师记录数
    @ApiOperation(value = "分页查询讲师列表")
    @PostMapping("{page}/{size}")
    public R pageList(
            @ApiParam(name = "page",value = "当前页码",required = true)
            @PathVariable Integer page,

            @ApiParam(name = "size",value = "每页记录数",required = true)
            @PathVariable Integer size,

            @ApiParam(name = "teacherQuery",value = "查询条件",required = false)
            @RequestBody TeacherQuery teacherQuery
    ){
        Page<EduTeacher> pageParam = new Page<>(page,size);

        eduTeacherService.pageQuery(pageParam,teacherQuery);

        long total = pageParam.getTotal();

        List<EduTeacher> records = pageParam.getRecords();

        return R.ok().data("total",total).data("records",records);
    }

    // 4.新增讲师
    @ApiOperation(value = "新增讲师")
    @PostMapping
    public R save(
            @ApiParam(name = "teacher",value = "讲师对象",required = true)
            @RequestBody EduTeacher eduTeacher
    ){
        eduTeacherService.save(eduTeacher);
        return R.ok();
    }

    // 5.根据id查询讲师数据
    @ApiOperation(value = "根据id查询讲师数据")
    @GetMapping("{id}")
    public R getById(
            @ApiParam(name = "id",value = "讲师id",required = true)
            @PathVariable String id
    ){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("item",eduTeacher);
    }

    // 6.根据id修改讲师数据
    @ApiOperation(value = "根据id修改讲师数据")
    @PutMapping("{id}")
    public R updateById(
            @ApiParam(name = "id",value = "讲师id",required = true)
            @PathVariable String id,

            @ApiParam(name = "teacher",value = "讲师对象",required = true)
            @RequestBody EduTeacher teacher
    ){
        teacher.setId(id);
        eduTeacherService.updateById(teacher);
        return R.ok();
    }
}

