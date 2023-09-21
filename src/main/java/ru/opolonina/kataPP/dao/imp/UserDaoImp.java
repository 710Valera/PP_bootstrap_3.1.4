package ru.opolonina.kataPP.dao.imp;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.opolonina.kataPP.dao.UserDao;
import ru.opolonina.kataPP.model.Role;
import ru.opolonina.kataPP.model.User;
import ru.opolonina.kataPP.service.RoleService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {




    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserDaoImp(RoleService roleService) {
        this.roleService = roleService;
    }

    RoleService roleService;
    @PersistenceContext
    public EntityManager entityManager;


    @Override
    @Transactional
    public List<User> getAllUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    @Transactional
    public void addUser(User user) {
        if (user.getPassword() != null){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        List<Role> roles = roleService.parseRoleList(user.getRoles());
        user.setRoles(roles);
        entityManager.persist(user);
        entityManager.flush();
    }

    @Override
    @Transactional
    public User getUserById(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    @Transactional
    public void updateUser(User updateUser, int id) {
        User user_from_DB = entityManager.find(User.class, id);
        user_from_DB.setUsername(updateUser.getUsername());
        user_from_DB.setLastname(updateUser.getLastname());
        user_from_DB.setAge(updateUser.getAge());
        user_from_DB.setEmail(updateUser.getEmail());
        user_from_DB.setRoles(updateUser.getRoles());
        if (user_from_DB.getPassword().equals(updateUser.getPassword())) {
            addUser(user_from_DB);
        } else {
            user_from_DB.setPassword(passwordEncoder.encode(updateUser.getPassword()));
            addUser(user_from_DB);
        }

        addUser(user_from_DB);
    }

    @Override
    @Transactional
    public void deleteUserById(int id) {
        entityManager.remove(entityManager.find(User.class, id));
    }

    @Override
    public User findByUsername(String name) {
        String queryString = "Select u from User u left join fetch u.roles where u.username=:name";
        TypedQuery<User> query = entityManager.createQuery(queryString, User.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }
}
