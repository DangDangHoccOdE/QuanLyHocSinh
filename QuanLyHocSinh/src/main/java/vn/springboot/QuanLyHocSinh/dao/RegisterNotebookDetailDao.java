package vn.springboot.QuanLyHocSinh.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.springboot.QuanLyHocSinh.entity.RegisterNotebookDetail;

import java.util.*;
@Repository
public interface RegisterNotebookDetailDao extends JpaRepository<RegisterNotebookDetail,Integer> {
     List<RegisterNotebookDetail> getRegisterNoteDetailByClassroomId(int id);
}
