package vn.springboot.QuanLyHocSinh.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import vn.springboot.QuanLyHocSinh.entity.Classroom;
import vn.springboot.QuanLyHocSinh.entity.Student;
import vn.springboot.QuanLyHocSinh.entity.Teacher;
import vn.springboot.QuanLyHocSinh.service.inter.IStudentService;
import vn.springboot.QuanLyHocSinh.service.inter.ITeacherService;
import vn.springboot.QuanLyHocSinh.utils.Log;
import vn.springboot.QuanLyHocSinh.utils.SecurityUtils;

import org.springframework.security.access.AccessDeniedException;
@Aspect
@Component
public class ParentAop {
    private final ITeacherService iTeacherService;
    private final IStudentService iStudentService;
    private final SecurityUtils securityUtils;

    @Autowired
    public ParentAop(ITeacherService iTeacherService, IStudentService iStudentService,  SecurityUtils securityUtils) {
        this.iTeacherService = iTeacherService;
        this.iStudentService = iStudentService;
        this.securityUtils = securityUtils;
    }

    @Before("execution(* vn.springboot.QuanLyHocSinh.rest.ParentController.showParent(..)) && args(..,studentId)")
    public void checkAccess(String studentId) throws AccessDeniedException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Teacher teacher = iTeacherService.findTeacherByAccountEmail(authentication.getName());

        Student student = iStudentService.findStudentById(studentId);
        Classroom classroom = student.getClassroom();

            if (classroom.getTeacher()!=teacher && !securityUtils.hasPrincipal()) {
                String mess = "Lỗi truy cập khi xem thông tin phụ huynh với mã học sinh: "+student.getStudentId() + ", người dùng: "+authentication.getName();
                Log.error(mess);
                throw new AccessDeniedException(mess);
            }

    }
}
