package vn.springboot.QuanLyHocSinh.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.springboot.QuanLyHocSinh.entity.RegisterNotebook;

@Repository
public interface RegisterNotebookDao extends JpaRepository<RegisterNotebook , Integer> {
     RegisterNotebook getRegisterNotebookByClassroomId(int id);
}
