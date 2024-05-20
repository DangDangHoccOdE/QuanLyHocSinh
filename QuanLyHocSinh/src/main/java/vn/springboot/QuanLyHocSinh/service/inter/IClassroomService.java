package vn.springboot.QuanLyHocSinh.service.inter;
import vn.springboot.QuanLyHocSinh.dto.ClassroomDto;
import vn.springboot.QuanLyHocSinh.entity.Classroom;
import vn.springboot.QuanLyHocSinh.entity.Teacher;

import java.util.*;

 public interface IClassroomService {
     List<Classroom> showAllClassroom();

     Classroom findClassroomByName(String name);

     void saveClassroom(Classroom classroom);

     Classroom findClassroomById(int id);

     void deleteClassroom(Classroom classroom);
     List<Teacher> getAllHomeRoomTeacher();

     ClassroomDto findClassroomDto(Classroom classroom);

     Classroom getClassroom(ClassroomDto classroomDto, Classroom classroom);

}
