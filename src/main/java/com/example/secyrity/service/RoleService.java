package com.example.secyrity.service;

import com.example.secyrity.model.Role;

import java.util.List;
public interface RoleService {
    Role getRoleByName(String name);
    List<Role> getListOfRoles();
}
