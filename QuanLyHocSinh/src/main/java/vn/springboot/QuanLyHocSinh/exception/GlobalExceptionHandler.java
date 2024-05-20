package vn.springboot.QuanLyHocSinh.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import vn.springboot.QuanLyHocSinh.utils.Log;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDenied(){
        return "/error/accessDenied";
    }

    @ExceptionHandler({NoResourceFoundException.class, NullPointerException.class,EntityNotFoundException.class})
    public String handleNoResourceFoundException(Exception e) {
        Log.info("NoResourceFoundException occurred: " + e.getMessage());
        return "/error/NoResourceFound";
    }
}

