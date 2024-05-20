package vn.springboot.QuanLyHocSinh.service.inter;

import vn.springboot.QuanLyHocSinh.entity.ReportCard;
import vn.springboot.QuanLyHocSinh.entity.Student;
import vn.springboot.QuanLyHocSinh.entity.Teacher;

import java.util.List;

public interface IReportCardService {
     List<ReportCard> findReportCardBySemesterYearAndStudentId(String semesterYear,String studentId);

     void save(ReportCard reportCard);

     ReportCard findReportCardBySemesterYearAndStudentIdAndSubjectId(String semesterYear,String StudentId, int subjectId);

     List<ReportCard> findReportCardByRole(Teacher teacher, Student student,String semesterYear);
}
