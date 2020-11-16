package com.atguigu.eduservice.entity;/*
 *
 *   @date 2020/11/14 19:40
 *      课程分类excel实体类
 */

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ExcelSubjectData {

    @ExcelProperty(index = 0)
    private String oneSubjectName;

    @ExcelProperty(index = 1)
    private String twoSubjectName;
}
