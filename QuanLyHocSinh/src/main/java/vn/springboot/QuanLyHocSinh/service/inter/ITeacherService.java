package vn.springboot.QuanLyHocSinh.service.inter;

import vn.springboot.QuanLyHocSinh.dto.TeacherDto;
import vn.springboot.QuanLyHocSinh.entity.Teacher;

import java.util.List;

public interface ITeacherService {
     List<Teacher> showAllTeacher();

     void saveTeacher(Teacher t);

     Teacher findTeacherById(String id);

     void deleteTeacher(Teacher teacher);

     Teacher findTeacherByAccountEmail(String email);

     List<Teacher> findAll();

     Teacher getTeacher(TeacherDto teacherDto, Teacher teacher);
     TeacherDto getTeacherDto(Teacher teacher);
     List<Teacher> findTeacherByTeachRole();

     String getEmailTeacher();
     List<Teacher> findTeacherByTeacherName(String name);

     boolean checkPrincipalByTeacher(Teacher teacher);
}
