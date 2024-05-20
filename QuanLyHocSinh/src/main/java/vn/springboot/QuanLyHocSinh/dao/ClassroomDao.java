package vn.springboot.QuanLyHocSinh.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.springboot.QuanLyHocSinh.entity.Classroom;
import java.util.*;

@Repository
public interface ClassroomDao extends JpaRepository<Classroom, Integer> {
     Classroom findClassroomByClassName(String name);
}
