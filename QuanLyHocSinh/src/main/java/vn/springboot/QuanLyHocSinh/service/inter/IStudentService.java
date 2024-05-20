package vn.springboot.QuanLyHocSinh.service.inter;

import vn.springboot.QuanLyHocSinh.dto.ParentDto;
import vn.springboot.QuanLyHocSinh.dto.StudentDto;
import vn.springboot.QuanLyHocSinh.entity.Classroom;
import vn.springboot.QuanLyHocSinh.entity.Student;

import java.util.List;

public interface IStudentService {
     void saveStudent(Student student);

     Student findStudentById(String id);

     void deleteStudent(Student student);

     Student getStudent(Student student,StudentDto studentDto, ParentDto parentDto, Classroom classroom);

     StudentDto getStudentDto(Student student);

     Student findStudentByAccountEmail(String email);

     List<Student> findStudentByStudentNameAndClassroomId(String name,int classId);

     List<Student> findStudentByGenderAndClassroomId(String gender,int classId);
}
