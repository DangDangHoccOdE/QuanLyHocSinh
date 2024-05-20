package vn.springboot.QuanLyHocSinh.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import vn.springboot.QuanLyHocSinh.entity.Classroom;
import vn.springboot.QuanLyHocSinh.entity.Teacher;
import vn.springboot.QuanLyHocSinh.service.inter.IClassroomService;
import vn.springboot.QuanLyHocSinh.service.inter.ITeacherService;
import vn.springboot.QuanLyHocSinh.utils.CheckAccessClassroom;
import vn.springboot.QuanLyHocSinh.utils.Log;
import vn.springboot.QuanLyHocSinh.utils.SecurityUtils;

import java.nio.file.AccessDeniedException;

@Aspect
@Component
public class RegisterNotebookAop {
    private final IClassroomService iClassroomService;
    private final CheckAccessClassroom checkAccessClassroom;
    private final ITeacherService iTeacherService;
    private final SecurityUtils securityUtils;

    public RegisterNotebookAop( IClassroomService iClassroomService, CheckAccessClassroom checkAccessClassroom, ITeacherService iTeacherService, SecurityUtils securityUtils) {
        this.iClassroomService = iClassroomService;
        this.checkAccessClassroom = checkAccessClassroom;
        this.iTeacherService = iTeacherService;
        this.securityUtils = securityUtils;
    }

    @Before("execution(* vn.springboot.QuanLyHocSinh.rest.RegisterNotebookController.getRegisterNotebook(..)) && args(..,classId)")
    public void checkAccess(int classId) throws AccessDeniedException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Teacher teacher = iTeacherService.findTeacherByAccountEmail(authentication.getName());

        Classroom classroom = iClassroomService.findClassroomById(classId);

        if(teacher!=null && !securityUtils.hasPrincipal() && checkAccessClassroom.hasNotAccessClassroom(classId,teacher)
                                            && classroom.getTeacher()!=teacher){
            String mess = "Lỗi quyền truy cập khi xem sổ đầu bài với tên lớp: "+classroom.getClassName()+", người dùng: "+authentication.getName();
            Log.error(mess);
            throw new AccessDeniedException(mess);
        }

    }
}
