package vn.springboot.QuanLyHocSinh.service.inter;

import vn.springboot.QuanLyHocSinh.entity.ReportCard;
import vn.springboot.QuanLyHocSinh.entity.Student;
import vn.springboot.QuanLyHocSinh.entity.Teacher;

import java.util.List;

public interface IReportCardService {
     List<ReportCard> findReportCardBySemesterYearAndStudentId(String semesterYear,String studentId);

     void save(ReportCard reportCard);

     List<ReportCard> findReportCardByStudentId(String id);

     ReportCard findReportCardBySemesterYearAndStudentIdAndSubjectId(String semesterYear,String StudentId, int subjectId);
     List<String> findSemesterYearOfStudent(List<ReportCard> reportCards);

     List<ReportCard> findReportCardByRole(Teacher teacher, Student student,String semesterYear);
}
