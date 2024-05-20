package vn.springboot.QuanLyHocSinh.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.springboot.QuanLyHocSinh.entity.Roles;

@Repository
public interface RolesDao extends JpaRepository<Roles,Integer> {
     Roles findRolesByRoleName(String name);
}
