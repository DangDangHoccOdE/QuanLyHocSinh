package vn.springboot.QuanLyHocSinh.service.inter;

import vn.springboot.QuanLyHocSinh.dto.RegisterNotebookDetailDto;
import vn.springboot.QuanLyHocSinh.entity.Classroom;
import vn.springboot.QuanLyHocSinh.entity.RegisterNotebookDetail;
import vn.springboot.QuanLyHocSinh.entity.Teacher;

import java.util.*;

public interface IRegisterNotebookDetailService {
     List<RegisterNotebookDetail> getRegisterNotebookDetailByClassId(int id);

     void saveRegisterNotebookDetail(RegisterNotebookDetail registerNotebookDetail);

     RegisterNotebookDetail findRegisterNotebookDetailById(int id);

     void delete(RegisterNotebookDetail registerNotebookDetail);

     RegisterNotebookDetailDto getRegisterNotebookDetailDto(RegisterNotebookDetail registerNotebookDetail);
     void saveRegisterNotebookDetail(RegisterNotebookDetailDto registerNotebookDetailDto, RegisterNotebookDetail registerNotebookDetail, int classId, Teacher teacher);

     List<RegisterNotebookDetail> findRegisterNotebookDetailByTeacher(Teacher teacher, Classroom classroom);
}

