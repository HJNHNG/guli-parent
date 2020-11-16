package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.SubjectNestedVo;
import com.atguigu.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2020-11-14
 */
@Api(description = "课程分类管理")
@CrossOrigin
@RestController
@RequestMapping("/eduservice/edu-subject")
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    @ApiOperation(value = "批量导入课程分类Excel")
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        eduSubjectService.importSubjectData(file,eduSubjectService);
        return R.ok();
    }

    @ApiOperation(value = "嵌套数据列表")
    @GetMapping
    public R nestedList(){
        List<SubjectNestedVo> subjectNestedVos = eduSubjectService.nestedList();
        return R.ok().data("items",subjectNestedVos);
    }
}

