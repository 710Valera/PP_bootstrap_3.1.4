package ru.opolonina.kataPP.service;

import ru.opolonina.kataPP.model.Role;

import java.util.List;

public interface RoleService {
    public List<Role> findAll();
    public Role getRoleById(int id);
    public List<Role> parseRoleList(List<Role> rawRoleList);
    }

