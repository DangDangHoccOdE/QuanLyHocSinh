package vn.springboot.QuanLyHocSinh.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.springboot.QuanLyHocSinh.dao.AccountDao;
import vn.springboot.QuanLyHocSinh.entity.Account;
import vn.springboot.QuanLyHocSinh.service.inter.IAccountService;

@Service
public class AccountService implements IAccountService {
    private final AccountDao accountDao;

    @Autowired
    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Transactional
    @Override
    public Account saveAccount(Account account) {
        return accountDao.save(account);
    }

    @Override
    public Account findAccountByEmail(String email) {
        return accountDao.findAccountByEmail(email);
    }

}
