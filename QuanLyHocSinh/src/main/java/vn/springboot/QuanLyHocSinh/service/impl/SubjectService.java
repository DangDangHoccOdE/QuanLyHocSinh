package vn.springboot.QuanLyHocSinh.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.springboot.QuanLyHocSinh.dao.SubjectDao;
import vn.springboot.QuanLyHocSinh.dto.SubjectDto;
import vn.springboot.QuanLyHocSinh.entity.Classroom;
import vn.springboot.QuanLyHocSinh.entity.Subject;
import vn.springboot.QuanLyHocSinh.entity.Teacher;
import vn.springboot.QuanLyHocSinh.service.inter.ISubjectService;
import vn.springboot.QuanLyHocSinh.service.inter.ITeacherService;
import vn.springboot.QuanLyHocSinh.utils.SecurityUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectService implements ISubjectService {
    private final SubjectDao subjectDao;
    private final ITeacherService iTeacherService;
    private final SecurityUtils securityUtils;

    @Autowired
    public SubjectService(SubjectDao subjectDao, ITeacherService iTeacherService, SecurityUtils securityUtils) {
        this.subjectDao = subjectDao;
        this.iTeacherService = iTeacherService;
        this.securityUtils = securityUtils;
    }

    @Override
    public List<Subject> showAllSubject() {
        return subjectDao.findAll();
    }

    @Override
    public Subject findSubjectByName(String name) {
        return subjectDao.findSubjectBySubName(name);
    }

    @Override
    public Subject findSubjectById(int id) {
        return subjectDao.findSubjectBySubId(id);
    }

    @Override
    @Transactional
    public void delete(Subject subject) {
        subjectDao.delete(subject);
    }

    @Override
    @Transactional
    public void saveSubject(SubjectDto subjectDto, Subject subject) {
        subject.setSubName(subjectDto.getName());
        subject.setNumberOfPeriods(subjectDto.getNumberOfPeriods());
        Teacher teacher = iTeacherService.findTeacherById(subjectDto.getTeacherId());
        subject.setTeacher(teacher);
        subject.setSubDes(subjectDto.getDescription());

        subjectDao.save(subject);
    }

    @Override
    public SubjectDto getSubjectDto(Subject subject) {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setName(subject.getSubName());
        subjectDto.setTeacherId(subject.getTeacher().getTeacherId());
        subjectDto.setNumberOfPeriods(subject.getNumberOfPeriods());
        subjectDto.setDescription(subject.getSubDes());

        return subjectDto;
    }

    @Override
    public List<Subject> uniqueSubjects(Classroom classroom, Teacher teacher) {
        List<Subject> subjectsByRoom = classroom.getSubjects();
        List<Subject> subjectsByTeacher = teacher.getSubjects();
        List<Subject> subjects = new ArrayList<>();

        for (Subject subject : subjectsByRoom) {
            if (subjectsByTeacher.contains(subject)) {
                subjects.add(subject);
            }
        }

        return subjects;
    }

    @Override
    public List<Subject> getSubjectByRole(Classroom classroom, Teacher teacher) {
        List<Subject> subjects;
        if (securityUtils.hasPrincipal() || classroom.getTeacher()==teacher) {
            subjects = classroom.getSubjects();
        } else {
            subjects = uniqueSubjects(classroom, teacher);
        }
        return subjects;
    }

    @Override
    public List<Subject> getSubjectByRoleToMark(Classroom classroom, Teacher teacher) {
        return uniqueSubjects(classroom, teacher);
    }
}
