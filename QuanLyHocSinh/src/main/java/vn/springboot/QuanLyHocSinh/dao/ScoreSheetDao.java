package vn.springboot.QuanLyHocSinh.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.springboot.QuanLyHocSinh.entity.ScoreSheet;

@Repository
public interface ScoreSheetDao extends JpaRepository<ScoreSheet,Integer> {
     ScoreSheet findScoreSheetBySemesterYearAndClassroom_Id(String semesterYear,int classId);
}
