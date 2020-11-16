package com.atguigu.eduservice.entity;/*
 *
 *   @date 2020/11/15 20:15
 *
 */

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SubjectNestedVo {
    private String id;
    private String title;
    private List<SubjectVo> children = new ArrayList<>();
}
