package vn.springboot.QuanLyHocSinh.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import vn.springboot.QuanLyHocSinh.annotation.CheckTeacherRole;
import vn.springboot.QuanLyHocSinh.annotation.SecuredCheck;
import vn.springboot.QuanLyHocSinh.dto.SubjectDto;
import vn.springboot.QuanLyHocSinh.entity.Classroom;
import vn.springboot.QuanLyHocSinh.entity.Subject;
import vn.springboot.QuanLyHocSinh.entity.Teacher;
import vn.springboot.QuanLyHocSinh.service.inter.IClassroomService;
import vn.springboot.QuanLyHocSinh.service.inter.ISubjectService;
import vn.springboot.QuanLyHocSinh.service.inter.ITeacherService;
import vn.springboot.QuanLyHocSinh.utils.Log;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/api/subject")
public class SubjectController {
    private final ISubjectService iSubjectService;
    private final ITeacherService iTeacherService;
    private final IClassroomService iClassroomService;

    @Autowired
    public SubjectController(ISubjectService iSubjectService, ITeacherService iTeacherService, IClassroomService iClassroomService) {
        this.iSubjectService = iSubjectService;
        this.iTeacherService = iTeacherService;
        this.iClassroomService = iClassroomService;
    }

    @SecuredCheck
    @GetMapping("/showAllSubjectByPrincipal")
    public String showAllSubjectByPrincipal(Model model) {
        List<Subject> subjects = iSubjectService.showAllSubject();
        model.addAttribute("subjects", subjects);

        Log.info("Xem danh sách môn học cả trường"+", người sử dụng: "+iTeacherService.getEmailTeacher());
        return "/subject/allSubjectByPrincipal";
    }

    @CheckTeacherRole
    @GetMapping("/showAllSubject")
    public String showAllSubject(Model model, @RequestParam("classId") int classId){
        Classroom classroom = iClassroomService.findClassroomById(classId);

        model.addAttribute("classroom", classroom);
        model.addAttribute("classId", classId);
        List<Subject> subjects = classroom.getSubjects();
        subjects.sort(Comparator.comparing(Subject::getSubId));
        model.addAttribute("subjects", subjects);

        Log.info("Xem danh sách môn học,tên lớp: "+classroom.getClassName()+", người sử dụng: "+iTeacherService.getEmailTeacher());
        return "/subject/allSubject";
    }

    @SecuredCheck
    @GetMapping("/create")
    public String createSubject(Model model)  {
        SubjectDto subjectDto = new SubjectDto();
        List<Teacher> teachers = iTeacherService.findTeacherByTeachRole();
        model.addAttribute("teachers", teachers);
        model.addAttribute("subjectDto", subjectDto);

        Log.info("Thêm môn học"+", người sử dụng: "+iTeacherService.getEmailTeacher());
        return "/subject/createSubject";
    }

    @SecuredCheck
    @PostMapping("/create")
    public String saveSubject(Model model, @Valid @ModelAttribute SubjectDto subjectDto, BindingResult result)  {
        List<Teacher> teachers = iTeacherService.findTeacherByTeachRole();
        model.addAttribute("teachers", teachers);

        if (iSubjectService.findSubjectByName(subjectDto.getName()) != null) {
            result.addError(new FieldError("subjectDto", "name", "Tên môn học đã tồn tại"));
        }

        if (result.hasErrors()) {
            return "subject/createSubject";
        }

        iSubjectService.saveSubject(subjectDto, new Subject());
        Log.info("Đã thêm môn học thành công, môn: "+subjectDto.getName()+", người sử dụng: "+iTeacherService.getEmailTeacher());
        return "redirect:/api/subject/showAllSubjectByPrincipal";
    }

    @SecuredCheck
    @GetMapping("/edit")
    public String editSubject(Model model, @RequestParam("subId") int subId) {
        Subject subject = iSubjectService.findSubjectById(subId);

        model.addAttribute("subject", subject);

        SubjectDto subjectDto = iSubjectService.getSubjectDto(subject);

        List<Teacher> teachers = iTeacherService.findTeacherByTeachRole();
        model.addAttribute("teachers", teachers);
        model.addAttribute("subjectDto", subjectDto);

        Log.info("Chỉnh sửa môn học,mã môn: "+subject.getSubId()+", người sử dụng: "+iTeacherService.getEmailTeacher());
        return "/subject/editSubject";
    }

    @SecuredCheck
    @PostMapping("/edit")
    public String saveEditSubject(Model model, @Valid @ModelAttribute SubjectDto subjectDto, BindingResult result, @RequestParam("subId") int subId) {
        Subject subject = iSubjectService.findSubjectById(subId);

        List<Teacher> teachers = iTeacherService.findTeacherByTeachRole();
        model.addAttribute("teachers", teachers);
        model.addAttribute("subject", subject);

        if (iSubjectService.findSubjectByName(subjectDto.getName()) != null && !subjectDto.getName().equals(subject.getSubName())) {
            result.addError(new FieldError("subjectDto", "name", "Tên môn học đã tồn tại"));
        }

        if (result.hasErrors()) {
            return "/subject/editSubject";
        }

        iSubjectService.saveSubject(subjectDto, subject);
        Log.info("Chỉnh sửa môn học thành công,mã môn: "+subject.getSubId()+", người sử dụng: "+iTeacherService.getEmailTeacher());
        return "redirect:/api/subject/showAllSubjectByPrincipal";
    }

    @SecuredCheck
    @GetMapping("/delete")
    public String deleteTeacher(@RequestParam("subId") int subId) {
        Subject subject = iSubjectService.findSubjectById(subId);

        iSubjectService.delete(subject);
        Log.info("Đã xóa môn học thành công, tên môn học: "+subject.getSubName()+" ,người sử dụng: "+iTeacherService.getEmailTeacher());
        return "redirect:/api/subject/showAllSubjectByPrincipal";
    }

}
