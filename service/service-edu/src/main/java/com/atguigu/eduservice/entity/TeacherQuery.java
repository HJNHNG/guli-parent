package com.atguigu.eduservice.entity;/*
 *
 *   @date 2020/11/4 8:56
 *   讲师条件查询类
 */

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = " 讲师查询对象",description = "讲师查询对象封装")
@Data
public class TeacherQuery implements Serializable {

    private static final Long serialVersionUID = 1L;

    @ApiModelProperty(value = "讲师名称，模糊查询")
    private String name;

    @ApiModelProperty(value = "讲师等级，1高级讲师 2首席讲师")
    private Integer level;

    @ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:10")
    private String begin;// 使用string类型，前端传过来的数据无需转换

    @ApiModelProperty(value = "查询结束时间", example = "2019-12-01 10:10:10")
    private String end;
}
