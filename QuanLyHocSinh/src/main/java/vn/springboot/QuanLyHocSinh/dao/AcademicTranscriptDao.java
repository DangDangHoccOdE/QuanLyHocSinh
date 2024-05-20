package vn.springboot.QuanLyHocSinh.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.springboot.QuanLyHocSinh.entity.AcademicTranscript;

@Repository
public interface AcademicTranscriptDao extends JpaRepository<AcademicTranscript, Integer> {
    AcademicTranscript findAcademicTranscriptByStudent_StudentId(String id);

}
