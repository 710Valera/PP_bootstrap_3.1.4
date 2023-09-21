package ru.opolonina.kataPP.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.opolonina.kataPP.dao.RoleDao;
import ru.opolonina.kataPP.model.Role;
import ru.opolonina.kataPP.service.RoleService;

import java.util.ArrayList;
import java.util.List;

@Service
@Component
public class RoleServiceImp implements RoleService {
    private RoleDao roleDao;

    @Autowired
    public RoleServiceImp(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public List<Role> findAll() {
        return roleDao.allRoles();
    }

    @Override
    public Role getRoleById(int id) {
        return roleDao.getRoleById(id);
    }

    @Override
    public List<Role> parseRoleList(List<Role> rawRoleList) {
        List<Role> roleList = new ArrayList<>();
        Role role = rawRoleList.stream().findFirst().get();
        String roleName = role.getRole();
        if (roleName.equals("ROLE_ADMIN")) {
            role.setId(1);
        } else role.setId(2);
        roleList.add(role);
        return roleList;
    }
}
