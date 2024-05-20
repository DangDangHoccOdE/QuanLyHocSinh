package vn.springboot.QuanLyHocSinh.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.springboot.QuanLyHocSinh.dao.RolesDao;
import vn.springboot.QuanLyHocSinh.entity.Roles;
import vn.springboot.QuanLyHocSinh.service.inter.IRolesService;

@Service
public class RolesService implements IRolesService {
    private final RolesDao rolesDao;

    @Autowired
    public RolesService(RolesDao rolesDao){
        this.rolesDao = rolesDao;
    }

    @Transactional
    @Override
    public Roles save(Roles roles) {
        return rolesDao.save(roles);
    }

    @Override
    public Roles findRolesByName(String rolesName) {
        return rolesDao.findRolesByRoleName(rolesName);
    }
}
