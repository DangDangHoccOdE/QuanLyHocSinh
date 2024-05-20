package vn.springboot.QuanLyHocSinh.service.inter;

import vn.springboot.QuanLyHocSinh.dto.SubjectDto;
import vn.springboot.QuanLyHocSinh.entity.Classroom;
import vn.springboot.QuanLyHocSinh.entity.Subject;
import vn.springboot.QuanLyHocSinh.entity.Teacher;

import java.util.List;
public interface ISubjectService {
    List<Subject> showAllSubject();

     Subject findSubjectByName(String name);

     Subject findSubjectById(int id);

     void delete(Subject subject);

    void saveSubject(SubjectDto subjectDto, Subject subject);

    SubjectDto getSubjectDto(Subject subject);

    List<Subject> uniqueSubjects(Classroom classroom, Teacher teacher);

    List<Subject> getSubjectByRole(Classroom classroom, Teacher teacher);

    List<Subject> getSubjectByRoleToMark(Classroom classroom, Teacher teacher);

}
