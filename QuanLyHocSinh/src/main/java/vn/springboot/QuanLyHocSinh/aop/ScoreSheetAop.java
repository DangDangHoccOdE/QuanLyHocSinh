package vn.springboot.QuanLyHocSinh.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import vn.springboot.QuanLyHocSinh.entity.Classroom;
import vn.springboot.QuanLyHocSinh.entity.ScoreSheet;
import vn.springboot.QuanLyHocSinh.entity.Teacher;
import vn.springboot.QuanLyHocSinh.service.inter.IClassroomService;
import vn.springboot.QuanLyHocSinh.service.inter.IScoreSheetService;
import vn.springboot.QuanLyHocSinh.service.inter.ITeacherService;
import vn.springboot.QuanLyHocSinh.utils.CheckAccessClassroom;
import vn.springboot.QuanLyHocSinh.utils.Log;
import vn.springboot.QuanLyHocSinh.utils.SecurityUtils;

import java.nio.file.AccessDeniedException;

@Aspect
@Component
public class ScoreSheetAop {
    private final SecurityUtils securityUtils;
    private final ITeacherService iTeacherService;
    private final IClassroomService iClassroomService;
    private final CheckAccessClassroom checkAccessClassroom;
    private final IScoreSheetService iScoreSheetService;

    public ScoreSheetAop(SecurityUtils securityUtils, ITeacherService iTeacherService, IClassroomService iClassroomService, CheckAccessClassroom checkAccessClassroom, IScoreSheetService iScoreSheetService) {
        this.securityUtils = securityUtils;
        this.iTeacherService = iTeacherService;
        this.iClassroomService = iClassroomService;
        this.checkAccessClassroom = checkAccessClassroom;
        this.iScoreSheetService = iScoreSheetService;
    }

    @Before(value = "execution(* vn.springboot.QuanLyHocSinh.rest.ScoreSheetController.showScoreSheetOfClassroom(..)) && args(..,classId,semesterYear)", argNames = "classId,semesterYear")
    public void checkAccess(int classId,String semesterYear) throws Exception{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Teacher teacher = iTeacherService.findTeacherByAccountEmail(authentication.getName());
        Classroom classroom = iClassroomService.findClassroomById(classId);
        ScoreSheet scoreSheet = iScoreSheetService.findScoreSheetBySemesterYearAndClassId(semesterYear,classId);

        if ((classroom.getTeacher() != teacher && !securityUtils.hasPrincipal() && checkAccessClassroom.hasNotAccessClassroom(classId,teacher))) {
            String mess = "Lỗi quyền truy cập khi xem bảng điểm với lớp: "+classId+" , học kỳ: "+semesterYear+", với người dùng: "+authentication.getName();
                Log.error(mess);
                throw new AccessDeniedException(mess);
            }

        if(scoreSheet==null){
            String mess = "Bảng điểm trống không thể xem,lớp : "+classId+" , học kỳ: "+semesterYear+", với người dùng: "+authentication.getName();
            Log.error(mess);
            throw new NullPointerException(mess);
        }
    }


}
