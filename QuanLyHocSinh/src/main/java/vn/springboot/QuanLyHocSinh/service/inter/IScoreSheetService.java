package vn.springboot.QuanLyHocSinh.service.inter;

import vn.springboot.QuanLyHocSinh.entity.ScoreSheet;

public interface IScoreSheetService {
    ScoreSheet findScoreSheetBySemesterYearAndClassId(String semesterYear,int classId);
}
