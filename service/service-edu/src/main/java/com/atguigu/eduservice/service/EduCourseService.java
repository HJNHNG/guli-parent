package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.CourseInfoForm;
import com.atguigu.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author atguigu
 * @since 2020-11-16
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoForm courseInfoForm);
}
