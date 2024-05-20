package vn.springboot.QuanLyHocSinh.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import vn.springboot.QuanLyHocSinh.entity.AcademicTranscriptDetail;
import vn.springboot.QuanLyHocSinh.entity.Classroom;
import vn.springboot.QuanLyHocSinh.entity.Student;
import vn.springboot.QuanLyHocSinh.entity.Teacher;
import vn.springboot.QuanLyHocSinh.service.inter.IAcademicTranscriptDetailService;
import vn.springboot.QuanLyHocSinh.service.inter.IStudentService;
import vn.springboot.QuanLyHocSinh.service.inter.ITeacherService;
import vn.springboot.QuanLyHocSinh.utils.Log;
import vn.springboot.QuanLyHocSinh.utils.SecurityUtils;

import java.nio.file.AccessDeniedException;

@Aspect
@Component
public class AcademicTranscriptDetailAop {
    private final ITeacherService iTeacherService;
    private final IStudentService iStudentService;
    private final SecurityUtils securityUtils;
    private final IAcademicTranscriptDetailService iAcademicTranscriptDetailService;

    public AcademicTranscriptDetailAop(ITeacherService iTeacherService, IStudentService iStudentService, SecurityUtils securityUtils, IAcademicTranscriptDetailService iAcademicTranscriptDetailService) {
        this.iTeacherService = iTeacherService;
        this.iStudentService = iStudentService;
        this.securityUtils = securityUtils;
        this.iAcademicTranscriptDetailService = iAcademicTranscriptDetailService;
    }

    @Before("(execution(* vn.springboot.QuanLyHocSinh.rest.AcademicTranscriptDetailController.createAcademicTranscriptDetail(..))" +
            "|| execution(* vn.springboot.QuanLyHocSinh.rest.AcademicTranscriptDetailController.saveAcademicTranscriptDetail(..)))&& args(..,idStudent)")
    public void checkAccess(String idStudent) throws AccessDeniedException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Teacher teacher=iTeacherService.findTeacherByAccountEmail(authentication.getName());

        Student student = iStudentService.findStudentById(idStudent);
        Classroom classroom = student.getClassroom();
        if(classroom.getTeacher() != teacher && !securityUtils.hasPrincipal()){
            String mess = "Lỗi quyền truy cập khi tạo sổ học bạ với mã học sinh "+student.getStudentId() + ", người dùng đăng nhập: "+authentication.getName();
            Log.error(mess);
            throw new AccessDeniedException(mess);
        }
    }

    @Before("(execution(* vn.springboot.QuanLyHocSinh.rest.AcademicTranscriptDetailController.editAcademicTranscriptDetail(..))" +
            "|| execution(* vn.springboot.QuanLyHocSinh.rest.AcademicTranscriptDetailController.saveEditAcademicTranscriptDetail(..)))&& args(..,academicTranscriptDetailId)")
    public void checkAccess(int academicTranscriptDetailId) throws AccessDeniedException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Teacher teacher=iTeacherService.findTeacherByAccountEmail(authentication.getName());

        AcademicTranscriptDetail academicTranscriptDetail = iAcademicTranscriptDetailService.findAcademicTranscriptDetailById(academicTranscriptDetailId);
        Student student = academicTranscriptDetail.getStudent();
        Classroom classroom = student.getClassroom();
        if(classroom.getTeacher() != teacher && !securityUtils.hasPrincipal()){
            String mess = "Lỗi quyền truy cập khi sửa sổ học bạ với mã học sinh "+student.getStudentId() + ", người dùng đăng nhập: "+authentication.getName();
            Log.error(mess);
            throw new AccessDeniedException(mess);
        }
    }
}
