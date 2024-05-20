package vn.springboot.QuanLyHocSinh.service.inter;

import vn.springboot.QuanLyHocSinh.dto.ReportCardDetailDto;
import vn.springboot.QuanLyHocSinh.entity.ReportCard;
import vn.springboot.QuanLyHocSinh.entity.ReportCardDetail;
import vn.springboot.QuanLyHocSinh.entity.Student;
import vn.springboot.QuanLyHocSinh.entity.Subject;

import java.util.*;

public interface IReportCardDetailService {
     List<ReportCardDetail> findReportCardDetailBySubjectIdAndStudentIdAndSemesterYear(int subjectId,String studentId,String semesterYear);

     void processScoreSaving(Subject subject, List<Student> students, List<Float> listScores, ReportCardDetailDto reportCardDetailDto) ;

     void saveScoreMediumOfReportCard(Subject subject, String studentId, String semesterYear);

     ReportCard getOrCreateReportCard(ReportCardDetailDto reportCardDetailDto,Student student,Subject subject);

     void saveListReportCardDetailScore(List<Student> students,Subject subject,ReportCardDetailDto reportCardDetailDto);

     ReportCardDetail findReportCardDetailBySubNameAndStudentIdAndSemesterYearAndTestName(String subName,String studentId,String semesterYear,String testName);
    }
