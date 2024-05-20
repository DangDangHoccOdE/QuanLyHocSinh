package vn.springboot.QuanLyHocSinh.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.springboot.QuanLyHocSinh.entity.Subject;

@Repository
public interface SubjectDao extends JpaRepository<Subject, Integer> {
     Subject findSubjectBySubName(String name);

     Subject findSubjectBySubId(int id);
}
