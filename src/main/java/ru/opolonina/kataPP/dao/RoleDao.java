package ru.opolonina.kataPP.dao;

import ru.opolonina.kataPP.model.Role;

import java.util.List;


public interface RoleDao{
    public List<Role> allRoles();
    public Role getRoleById(int id);
}
