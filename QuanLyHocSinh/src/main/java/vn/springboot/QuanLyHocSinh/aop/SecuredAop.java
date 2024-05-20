package vn.springboot.QuanLyHocSinh.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.springboot.QuanLyHocSinh.utils.Log;
import vn.springboot.QuanLyHocSinh.utils.SecurityUtils;

import java.nio.file.AccessDeniedException;

@Aspect
@Component
public class SecuredAop {
    private final SecurityUtils securityUtils;

    @Autowired
    public SecuredAop(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    @Before("@annotation(vn.springboot.QuanLyHocSinh.annotation.SecuredCheck)")
    public void checkHasAccess() throws AccessDeniedException{
            if(!securityUtils.hasPrincipal()){
                String mess="Không có quyền truy cập!";
                Log.error(mess);
                throw new AccessDeniedException(mess);
            }
    }
}


