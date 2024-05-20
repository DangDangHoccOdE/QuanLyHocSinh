package vn.springboot.QuanLyHocSinh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.springboot.QuanLyHocSinh.dao.AccountDao;
import vn.springboot.QuanLyHocSinh.utils.MyAccount;
import vn.springboot.QuanLyHocSinh.entity.Account;
import vn.springboot.QuanLyHocSinh.entity.Roles;

import java.util.*;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final AccountDao accountDao;

    @Autowired
    public CustomUserDetailService(AccountDao accountDao){
        this.accountDao = accountDao;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountDao.findAccountByEmail(email);

        if(account == null){
            throw new UsernameNotFoundException("Account not found");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        String fullName ;

        if(account.getTeachers() !=null){
            for(Roles roles : account.getTeachers().getRoles()){
                authorities.add(new SimpleGrantedAuthority(roles.getRoleName()));
            }
            fullName = account.getTeachers().getTeacherName();
        }else{
            authorities.add(new SimpleGrantedAuthority(account.getStudent().getRoles().getRoleName()));
            fullName = account.getStudent().getStudentName();
        }

        MyAccount myAccount = new MyAccount(account.getEmail(),account.getPassword(),authorities);
        myAccount.setFullName(fullName);

        return myAccount;
    }
}
