package vn.springboot.QuanLyHocSinh.service.inter;

import vn.springboot.QuanLyHocSinh.dto.AcademicTranscriptDetailDto;
import vn.springboot.QuanLyHocSinh.entity.AcademicTranscriptDetail;
import vn.springboot.QuanLyHocSinh.entity.Student;

import java.util.*;

public interface IAcademicTranscriptDetailService {
     List<AcademicTranscriptDetail> getAcademicTranscriptDetailByStudentId(String id);

     void save(AcademicTranscriptDetail academicTranscriptDetail);

     AcademicTranscriptDetail findAcademicTranscriptDetailById(int academicTranscriptDetailId);

     void delete(AcademicTranscriptDetail academicTranscriptDetail);

    AcademicTranscriptDetailDto getAcademicTranscriptDetailDto(AcademicTranscriptDetail academicTranscriptDetail);

    AcademicTranscriptDetail getAcademicTranscriptDetail(AcademicTranscriptDetail academicTranscriptDetail, AcademicTranscriptDetailDto academicTranscriptDetailDto, Student student);

    AcademicTranscriptDetail findAcademicTranscriptDetailByStudentAndSemesterYear(Student student,String semesterYear);
}
