package vn.springboot.QuanLyHocSinh.service.inter;

import vn.springboot.QuanLyHocSinh.entity.AcademicTranscript;

public interface IAcademicTranscriptService {
     void save(AcademicTranscript academicTranscript);

     AcademicTranscript findAcademicTranscriptByStudentId(String id);
}
