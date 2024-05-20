package vn.springboot.QuanLyHocSinh.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.springboot.QuanLyHocSinh.entity.Account;

@Repository
public interface AccountDao extends JpaRepository<Account, String> {
     Account findAccountByEmail(String email);
}
