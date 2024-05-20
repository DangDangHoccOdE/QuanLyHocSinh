package vn.springboot.QuanLyHocSinh.service.inter;

import vn.springboot.QuanLyHocSinh.dto.ParentDto;
import vn.springboot.QuanLyHocSinh.entity.Parent;
import vn.springboot.QuanLyHocSinh.entity.Student;

public interface IParentService {
     void saveParent(Parent parent);

     Parent findParentByStudentId(String studentId);

     ParentDto getParentDto(Student student);
}
