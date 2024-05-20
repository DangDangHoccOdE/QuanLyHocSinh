package vn.springboot.QuanLyHocSinh.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.springboot.QuanLyHocSinh.annotation.CheckTeacherRole;
import vn.springboot.QuanLyHocSinh.entity.*;
import vn.springboot.QuanLyHocSinh.service.inter.IClassroomService;
import vn.springboot.QuanLyHocSinh.service.inter.IScoreSheetService;
import vn.springboot.QuanLyHocSinh.service.inter.ISubjectService;
import vn.springboot.QuanLyHocSinh.service.inter.ITeacherService;
import vn.springboot.QuanLyHocSinh.utils.Log;
import vn.springboot.QuanLyHocSinh.utils.ScoreMediumCalculator;
import vn.springboot.QuanLyHocSinh.utils.SecurityUtils;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/scoreSheet")
public class ScoreSheetController {
    private final IClassroomService iClassroomService;
    private final IScoreSheetService iScoreSheetService;
    private final ITeacherService iTeacherService;
    private final ISubjectService iSubjectService;
    private final ScoreMediumCalculator scoreMediumCalculator;
    private final SecurityUtils securityUtils;

    @Autowired
    public ScoreSheetController(IClassroomService iClassroomService, IScoreSheetService iScoreSheetService, ITeacherService iTeacherService, ISubjectService iSubjectService, ScoreMediumCalculator scoreMediumCalculator, SecurityUtils securityUtils) {
        this.iClassroomService = iClassroomService;
        this.iScoreSheetService = iScoreSheetService;
        this.iTeacherService = iTeacherService;
        this.iSubjectService = iSubjectService;
        this.scoreMediumCalculator = scoreMediumCalculator;
        this.securityUtils = securityUtils;
    }

    @CheckTeacherRole
    @GetMapping("/showScoreSheet")
    public String showScoreSheetOfClassroom(Model model, @RequestParam("classId") int classId, @RequestParam("semesterYear") String semesterYear) {
        Classroom classroom = iClassroomService.findClassroomById(classId);
        model.addAttribute("classroom", classroom);
        model.addAttribute("securityUtils",securityUtils);

        List<Student> students = classroom.getStudents();
        model.addAttribute("students", students);

        ScoreSheet scoreSheet = iScoreSheetService.findScoreSheetBySemesterYearAndClassId(semesterYear,classId);
        model.addAttribute("scoreSheet",scoreSheet);

        String teacherEmail = iTeacherService.getEmailTeacher();
        Teacher teacher = iTeacherService.findTeacherByAccountEmail(teacherEmail);
        model.addAttribute("teacher",teacher);
        List<Subject> subjects = iSubjectService.getSubjectByRole(classroom,teacher);

        model.addAttribute("subjects",subjects);

        Map<Student,Float> studentFloatMap = scoreMediumCalculator.scoreMediumCalculatorOfSemester(scoreSheet,students);
        model.addAttribute("studentFloatMap",studentFloatMap);

        Log.info("Xem bảng điểm, lớp: "+classroom.getClassName()+" ,ngưởi sử dụng: "+teacherEmail);
        return "/scoreSheet/showScoreSheetOfClassroom";
    }
}
