package ru.opolonina.kataPP.dao.imp;

import org.springframework.stereotype.Repository;
import ru.opolonina.kataPP.dao.RoleDao;
import ru.opolonina.kataPP.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public class RoleDaoImp implements RoleDao {
    @PersistenceContext
    public EntityManager entityManager;

    @Override
    public List<Role> allRoles() {
        return entityManager.createQuery("from Role", Role.class).getResultList();
    }
    @Override
    public Role getRoleById(int id) {
        return entityManager.find(Role.class, id);
    }
}
