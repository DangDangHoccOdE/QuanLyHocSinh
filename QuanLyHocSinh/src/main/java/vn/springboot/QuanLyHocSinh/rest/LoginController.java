package vn.springboot.QuanLyHocSinh.rest;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import vn.springboot.QuanLyHocSinh.utils.Log;

@Controller
public class LoginController {
    @GetMapping("/showLoginPage")
    public String showLoginPage(){
        Log.info("Đang đăng nhập ");
        return "login";
    }
}
