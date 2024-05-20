package vn.springboot.QuanLyHocSinh.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.springboot.QuanLyHocSinh.annotation.CheckTeacherRole;
import vn.springboot.QuanLyHocSinh.entity.AcademicTranscriptDetail;
import vn.springboot.QuanLyHocSinh.entity.Classroom;
import vn.springboot.QuanLyHocSinh.entity.Student;
import vn.springboot.QuanLyHocSinh.service.inter.IAcademicTranscriptDetailService;
import vn.springboot.QuanLyHocSinh.service.inter.IClassroomService;
import vn.springboot.QuanLyHocSinh.service.inter.IStudentService;
import vn.springboot.QuanLyHocSinh.utils.Log;

import java.util.*;

@Controller
@RequestMapping("/api/academicTranscript")
public class AcademicTranscriptController {
    private final IAcademicTranscriptDetailService iAcademicTranscriptDetailService;
    private final IStudentService iStudentService;
    private final IClassroomService iClassroomService;

    @Autowired
    public AcademicTranscriptController(IClassroomService iClassroomService, IAcademicTranscriptDetailService iAcademicTranscriptDetailService, IStudentService iStudentService){
        this.iAcademicTranscriptDetailService = iAcademicTranscriptDetailService;
        this.iStudentService = iStudentService;
        this.iClassroomService = iClassroomService;
    }

    @CheckTeacherRole
    @GetMapping("/showAcademicTranscript")
    public String showAcademicTranscript(Model model,@RequestParam("studentId") String studentId){
        List<AcademicTranscriptDetail> academicTranscriptDetails = iAcademicTranscriptDetailService.getAcademicTranscriptDetailByStudentId(studentId);
        model.addAttribute("academicTranscriptDetails",academicTranscriptDetails);

        Student student = iStudentService.findStudentById(studentId);
        model.addAttribute("student",student);

        Classroom classroom = iClassroomService.findClassroomById(student.getClassroom().getId());
        model.addAttribute("classroom",classroom);

        Log.info("Xem danh sách sổ học bạ, mã học sinh: "+studentId);
        return "/academicTranscript/showAcademicTranscript";
    }

}
