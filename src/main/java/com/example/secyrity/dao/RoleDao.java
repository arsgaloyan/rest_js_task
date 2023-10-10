package com.example.secyrity.dao;

import com.example.secyrity.model.Role;

import java.util.List;

public interface RoleDao {
    Role getRoleByName(String name);
    List<Role> getListOfRoles();
}
