package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.TeacherQuery;
import com.atguigu.eduservice.mapper.EduTeacherMapper;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2020-11-03
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public boolean removeById(Serializable id) {
        Integer result = baseMapper.deleteById(id);
        return null != result && result > 0;
    }

    /**
     * 分页条件查询讲师数据
     * @param pageParam         分页
     * @param teacherQuery      条件
     */
    @Override
    public void pageQuery(Page<EduTeacher> pageParam, TeacherQuery teacherQuery) {

        // 构造一个条件查询对象
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();

        // 根据数据库中sort字段排序
        queryWrapper.orderByAsc("sort");

        // 为null，直接执行查询，退出这个方法
        if (teacherQuery == null){
            baseMapper.selectList(queryWrapper);
            return;
        }

        if (StringUtils.isNotEmpty(teacherQuery.getName())){
            queryWrapper.like("name",teacherQuery.getName());
        }

        if (teacherQuery.getLevel() != null){
            queryWrapper.eq("level",teacherQuery.getLevel());
        }

        if (StringUtils.isNotEmpty(teacherQuery.getBegin())){
            queryWrapper.ge("gmt_create", teacherQuery.getBegin());
        }

        if (StringUtils.isNotEmpty(teacherQuery.getEnd())) {
            queryWrapper.le("gmt_create", teacherQuery.getEnd());
        }

        baseMapper.selectPage(pageParam,queryWrapper);
    }
}
