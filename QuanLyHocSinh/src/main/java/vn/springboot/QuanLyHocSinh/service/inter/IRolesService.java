package vn.springboot.QuanLyHocSinh.service.inter;

import vn.springboot.QuanLyHocSinh.entity.Roles;

public interface IRolesService {
     Roles save(Roles roles);

     Roles findRolesByName(String rolesName);
}
