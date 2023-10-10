package com.example.secyrity.service;

import com.example.secyrity.dao.RoleDao;
import com.example.secyrity.model.Role;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleServiceImp implements RoleService {

    private final RoleDao roleDao;

    public RoleServiceImp(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Role getRoleByName(String name) {
        return roleDao.getRoleByName(name);
    }

    @Override
    public List<Role> getListOfRoles() {
        return roleDao.getListOfRoles();
    }

}

