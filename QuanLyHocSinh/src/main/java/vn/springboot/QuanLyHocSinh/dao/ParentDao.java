package vn.springboot.QuanLyHocSinh.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.springboot.QuanLyHocSinh.entity.Parent;

@Repository
public interface ParentDao extends JpaRepository<Parent,Integer> {
    Parent findParentByStudent_StudentId(String id);
}
