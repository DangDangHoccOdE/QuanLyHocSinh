package vn.springboot.QuanLyHocSinh.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import vn.springboot.QuanLyHocSinh.dao.AcademicTranscriptDetailDao;
import vn.springboot.QuanLyHocSinh.dto.AcademicTranscriptDetailDto;
import vn.springboot.QuanLyHocSinh.entity.AcademicTranscriptDetail;
import vn.springboot.QuanLyHocSinh.entity.Student;
import vn.springboot.QuanLyHocSinh.service.inter.IAcademicTranscriptDetailService;
import vn.springboot.QuanLyHocSinh.service.inter.IAcademicTranscriptService;
import vn.springboot.QuanLyHocSinh.utils.AcademicPerformance;
import vn.springboot.QuanLyHocSinh.utils.ConvertString;

import java.util.List;

@Service
public class AcademicTranscriptDetailService implements IAcademicTranscriptDetailService {
    private final AcademicTranscriptDetailDao academicTranscriptDetailDao;
    private final ConvertString convertString;
    private final AcademicPerformance academicPerformance;
    private final IAcademicTranscriptService iAcademicTranscriptService;

    public AcademicTranscriptDetailService(AcademicTranscriptDetailDao academicTranscriptDetailDao, ConvertString convertString, AcademicPerformance academicPerformance, IAcademicTranscriptService iAcademicTranscriptService){
        this.academicTranscriptDetailDao = academicTranscriptDetailDao;
        this.convertString = convertString;
        this.academicPerformance = academicPerformance;
        this.iAcademicTranscriptService = iAcademicTranscriptService;
    }

    @Override
    public List<AcademicTranscriptDetail> getAcademicTranscriptDetailByStudentId(String id) {
        return academicTranscriptDetailDao.findAcademicTranscriptDetailByStudent_StudentId(id);
    }

    @Transactional
    @Override
    public void save(AcademicTranscriptDetail academicTranscriptDetail) {
        academicTranscriptDetailDao.save(academicTranscriptDetail);
    }

    @Override
    public AcademicTranscriptDetail findAcademicTranscriptDetailById(int academicTranscriptDetailId) {
        return academicTranscriptDetailDao.findAcademicTranscriptDetailById(academicTranscriptDetailId);
    }

    @Override
    @Transactional
    public void delete(AcademicTranscriptDetail academicTranscriptDetail) {
        academicTranscriptDetailDao.delete(academicTranscriptDetail);
    }

    @Override
    public AcademicTranscriptDetailDto getAcademicTranscriptDetailDto(AcademicTranscriptDetail academicTranscriptDetail) {
        AcademicTranscriptDetailDto academicTranscriptDetailDto = new AcademicTranscriptDetailDto();
        academicTranscriptDetailDto.setStudentId(academicTranscriptDetail.getStudent().getStudentId());
        academicTranscriptDetailDto.setStudentName(academicTranscriptDetail.getStudent().getStudentName());
        academicTranscriptDetailDto.setClassName(academicTranscriptDetail.getStudent().getClassroom().getClassName());
        academicTranscriptDetailDto.setSemesterYear(academicTranscriptDetail.getSemesterYear());
        academicTranscriptDetailDto.setScore(academicTranscriptDetail.getScore());
        academicTranscriptDetailDto.setTime(academicTranscriptDetail.getTime().toString());
        academicTranscriptDetailDto.setTeacherName(academicTranscriptDetail.getTeacher().getTeacherName());
        academicTranscriptDetailDto.setReviewOfTeachersAndParents(academicTranscriptDetail.getReviewOfTeachersAndParents());
        return academicTranscriptDetailDto;
    }

    @Override
    public AcademicTranscriptDetail getAcademicTranscriptDetail(AcademicTranscriptDetail academicTranscriptDetail, AcademicTranscriptDetailDto academicTranscriptDetailDto, Student student) {
        academicTranscriptDetail.setStudent(student);
        academicTranscriptDetail.setTeacher(student.getClassroom().getTeacher());
        academicTranscriptDetail.setScore(academicTranscriptDetailDto.getScore());
        academicTranscriptDetail.setReviewOfTeachersAndParents(academicTranscriptDetailDto.getReviewOfTeachersAndParents());
        academicTranscriptDetail.setSemesterYear(academicTranscriptDetailDto.getSemesterYear());
        academicTranscriptDetail.setTime(convertString.convertStringToDate(academicTranscriptDetailDto.getTime()));
        academicTranscriptDetail.setAcademicTranscript(iAcademicTranscriptService.findAcademicTranscriptByStudentId(academicTranscriptDetailDto.getStudentId()));
        academicTranscriptDetail.setAcademicAbility(academicPerformance.academicPerformance(academicTranscriptDetailDto.getScore()));
        return academicTranscriptDetail;
    }

    @Override
    public AcademicTranscriptDetail findAcademicTranscriptDetailByStudentAndSemesterYear(Student student, String semesterYear) {
        return academicTranscriptDetailDao.findAcademicTranscriptDetailByStudentAndSemesterYear(student,semesterYear);
    }
}
