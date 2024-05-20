package vn.springboot.QuanLyHocSinh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.springboot.QuanLyHocSinh.dao.AcademicTranscriptDao;
import vn.springboot.QuanLyHocSinh.entity.AcademicTranscript;
import vn.springboot.QuanLyHocSinh.service.inter.IAcademicTranscriptService;

@Service
public class AcademicTranscriptService implements IAcademicTranscriptService {
    private final AcademicTranscriptDao academicTranscriptDao;

    @Autowired
    public AcademicTranscriptService(AcademicTranscriptDao academicTranscriptDao){
        this.academicTranscriptDao= academicTranscriptDao;
    }

    @Override
    public void save(AcademicTranscript academicTranscript) {
        academicTranscriptDao.save(academicTranscript);
    }

    @Override
    public AcademicTranscript findAcademicTranscriptByStudentId(String id) {
        return academicTranscriptDao.findAcademicTranscriptByStudent_StudentId(id);
    }
}
