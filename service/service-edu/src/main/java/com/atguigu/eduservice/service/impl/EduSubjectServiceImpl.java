package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.config.SubjectExcelListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.ExcelSubjectData;
import com.atguigu.eduservice.entity.SubjectNestedVo;
import com.atguigu.eduservice.entity.SubjectVo;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.config.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.Subject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2020-11-14
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {


    @Override
    public void importSubjectData(MultipartFile file, EduSubjectService eduSubjectService) {
        try {
            // 获取文件输入流
            InputStream inputStream = file.getInputStream();
            // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
            EasyExcel.read(inputStream, ExcelSubjectData.class,new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
            throw new GuliException(20002,"添加课程分类失败");
        }
    }

    @Override
    public List<SubjectNestedVo> nestedList() {
        // 最终要得到的数据列表
        ArrayList<SubjectNestedVo> subjectNestedVos = new ArrayList<>();

        // 获取一级分类数据
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",0);
        queryWrapper.orderByAsc("sort","id");
        List<EduSubject> eduSubjects = baseMapper.selectList(queryWrapper);

        // 获取二级分类数据
        QueryWrapper<EduSubject> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.ne("parent_id",0);
        queryWrapper2.orderByAsc("sort","id");
        List<EduSubject> eduSubjects2 = baseMapper.selectList(queryWrapper2);

        // 填充一级分类数据
        int count = eduSubjects.size();
        for (int i = 0; i < count; i++) {
            EduSubject eduSubject = eduSubjects.get(i);

            // 创建一级分类vo对象
            SubjectNestedVo subjectNestedVo = new SubjectNestedVo();
            BeanUtils.copyProperties(eduSubject,subjectNestedVo);
            subjectNestedVos.add(subjectNestedVo);
            //填充二级分类数据
            ArrayList<SubjectVo> subjectVos = new ArrayList<>();
            int count2 = eduSubjects2.size();
            for (int j = 0; j < count2; j++) {
                EduSubject eduSubject2 = eduSubjects2.get(j);
                if (eduSubject.getId().equals(eduSubject2.getParentId())){
                    SubjectVo subjectVo = new SubjectVo();
                    BeanUtils.copyProperties(eduSubject2,subjectVo);
                    subjectVos.add(subjectVo);
                }
            }
            subjectNestedVo.setChildren(subjectVos);
        }
        return subjectNestedVos;
    }
}
