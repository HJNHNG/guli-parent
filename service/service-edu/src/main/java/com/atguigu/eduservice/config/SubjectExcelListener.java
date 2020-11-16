package com.atguigu.eduservice.config;/*
 *
 *   @date 2020/11/14 19:39
 *      读取Excel监听器
 */

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.ExcelSubjectData;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.config.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.Map;

public class SubjectExcelListener extends AnalysisEventListener<ExcelSubjectData> {

    private EduSubjectService eduSubjectService;

    // 创建有参数构造，传递subjectService用于操作数据库
    public SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    @Override
    public void invoke(ExcelSubjectData excelSubjectData, AnalysisContext analysisContext) {
        // 判断传入的excel是否为null
        if (excelSubjectData == null){
            throw new GuliException(20001,"添加失败");
        }

        // 添加一级分类
        EduSubject eduOneSubject = this.existOneSubject(eduSubjectService, excelSubjectData.getOneSubjectName());
        if (eduOneSubject == null) {// 没有相同的
            eduOneSubject = new EduSubject();
            eduOneSubject.setTitle(excelSubjectData.getOneSubjectName());
            eduOneSubject.setParentId("0");
            eduSubjectService.save(eduOneSubject);
        }

        // 获取一级分类的id
        String pid = eduOneSubject.getId();

        // 添加二级分类
        EduSubject eduTwoSubject = this.existTwoSubject(eduSubjectService, excelSubjectData.getTwoSubjectName(), pid);
        if (eduTwoSubject == null) {
            eduTwoSubject = new EduSubject();
            eduTwoSubject.setTitle(excelSubjectData.getTwoSubjectName());
            eduTwoSubject.setParentId(pid);
            eduSubjectService.save(eduTwoSubject);
        }

    }

    // 读取excel表头信息
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息："+headMap);
    }

    // 读取完毕后执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    // 判断一级分类是否重复
    public EduSubject existOneSubject(EduSubjectService eduSubjectService,String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        return eduSubjectService.getOne(wrapper);
    }

    // 判断二级分类是否重复
    public EduSubject existTwoSubject(EduSubjectService eduSubjectService,String name,String pid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        return eduSubjectService.getOne(wrapper);
    }
}
