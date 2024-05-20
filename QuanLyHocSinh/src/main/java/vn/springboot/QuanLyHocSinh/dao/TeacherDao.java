package vn.springboot.QuanLyHocSinh.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.springboot.QuanLyHocSinh.entity.Teacher;
import java.util.*;


@Repository
public interface TeacherDao extends JpaRepository<Teacher,Integer> {

     Teacher findTeacherByTeacherId(String id);
     Teacher findTeacherByAccount_Email(String email);

     @Query("select t from Teacher t where t.teacherName LIKE %:name%")
     List<Teacher> findTeacherByTeacherName(@Param("name") String name);

}
