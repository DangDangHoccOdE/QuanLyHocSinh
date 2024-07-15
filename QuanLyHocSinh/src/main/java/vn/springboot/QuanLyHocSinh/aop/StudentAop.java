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
import vn.springboot.QuanLyHocSinh.service.inter.IClassroomService;
import vn.springboot.QuanLyHocSinh.service.inter.IStudentService;
import vn.springboot.QuanLyHocSinh.service.inter.ITeacherService;
import vn.springboot.QuanLyHocSinh.utils.CheckAccessClassroom;
import vn.springboot.QuanLyHocSinh.utils.Log;
import vn.springboot.QuanLyHocSinh.utils.SecurityUtils;

import org.springframework.security.access.AccessDeniedException;
@Aspect
@Component
public class StudentAop {
    private final SecurityUtils securityUtils;
    private final ITeacherService iTeacherService;
    private final IClassroomService iClassroomService;
    private final CheckAccessClassroom checkAccessClassroom;

    private final IStudentService iStudentService;

    @Autowired
    public StudentAop(SecurityUtils securityUtils, ITeacherService iTeacherService, IClassroomService iClassroomService, CheckAccessClassroom checkAccessClassroom, IStudentService iStudentService) {
        this.securityUtils = securityUtils;
        this.iTeacherService = iTeacherService;
        this.iClassroomService = iClassroomService;
        this.checkAccessClassroom = checkAccessClassroom;
        this.iStudentService = iStudentService;
    }

    @Before("execution(* vn.springboot.QuanLyHocSinh.rest.StudentController.getAllStudent(..)) && args(..,classId)")
    public void checkAccess(int classId) throws AccessDeniedException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Teacher teacher = iTeacherService.findTeacherByAccountEmail(authentication.getName());
        Classroom classroom = iClassroomService.findClassroomById(classId);
            if (!securityUtils.hasPrincipal() && classroom.getTeacher()!=teacher && checkAccessClassroom.hasNotAccessClassroom(classroom.getId(), teacher)) {
                String mess = "Lỗi quyền truy cập khi xem danh sách học sinh với tên lớp: "+classroom.getClassName()+", và người dùng: "+authentication.getName();
                Log.error(mess);
                throw new AccessDeniedException(mess);
            }
    }

    @Before(value = "execution(* vn.springboot.QuanLyHocSinh.rest.StudentController.getStudentByName(..)) && args(..,studentName,classId)", argNames = "studentName,classId")
    public void checkAccess(String studentName,int classId) throws AccessDeniedException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Teacher teacher = iTeacherService.findTeacherByAccountEmail(authentication.getName());
        Classroom classroom = iClassroomService.findClassroomById(classId);
        if (classroom.getTeacher()!=teacher && !securityUtils.hasPrincipal() && checkAccessClassroom.hasNotAccessClassroom(classroom.getId(), teacher)) {
            String mess = "Lỗi quyền truy cập khi tìm kiếm học sinh theo tên: "+studentName+" ,lớp: "+classroom.getClassName()+", và người dùng: "+authentication.getName();
            Log.error(mess);
            throw new AccessDeniedException(mess);
        }
    }

    @Before(value = "execution(* vn.springboot.QuanLyHocSinh.rest.StudentController.getStudentByGender(..)) && args(..,selectGender,classId)", argNames = "selectGender,classId")
    public void checkAccessSearchGender(String selectGender,int classId) throws AccessDeniedException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Teacher teacher = iTeacherService.findTeacherByAccountEmail(authentication.getName());
        Classroom classroom = iClassroomService.findClassroomById(classId);
        if (classroom.getTeacher()!=teacher && !securityUtils.hasPrincipal() && checkAccessClassroom.hasNotAccessClassroom(classroom.getId(), teacher)) {
            String mess = "Lỗi quyền truy cập khi tìm kiếm học sinh theo giới tính: "+selectGender+" ,lớp: "+classroom.getClassName()+", và người dùng: "+authentication.getName();
            Log.error(mess);
            throw new AccessDeniedException(mess);
        }
    }

    @Before(value = "(execution(* vn.springboot.QuanLyHocSinh.rest.StudentController.changeInfoAccount(..)) || execution(* vn.springboot.QuanLyHocSinh.rest.StudentController.saveInfoAccount(..))) && args(..,studentId)")
    public void checkAccessSearchGender(String studentId) throws AccessDeniedException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();
        Teacher teacher = iTeacherService.findTeacherByAccountEmail(email);
        Student student = iStudentService.findStudentByAccountEmail(email);
        Student studentFind = iStudentService.findStudentById(studentId);
        Classroom classroom = studentFind.getClassroom();

        if ((teacher != null && !securityUtils.hasPrincipal())|| (student!=null &&studentFind!=student )) {
               String mess = "Lỗi quyền truy cập khi thay đổi thông tin tài khoản học sinh, mã học sinh: "+studentFind.getStudentId()+" ,tên học sinh: "+studentFind.getStudentName()+" ,lớp: "+classroom.getClassName()+", và người dùng: "+email;
               Log.error(mess);
               throw new AccessDeniedException(mess);
        }
    }

}
