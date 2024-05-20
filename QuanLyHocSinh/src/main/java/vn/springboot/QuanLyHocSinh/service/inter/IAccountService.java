package vn.springboot.QuanLyHocSinh.service.inter;

import vn.springboot.QuanLyHocSinh.entity.Account;

public interface IAccountService {
     Account saveAccount(Account account);

     Account findAccountByEmail(String email);
}
