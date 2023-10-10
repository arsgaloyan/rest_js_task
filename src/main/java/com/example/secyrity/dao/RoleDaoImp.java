package com.example.secyrity.dao;

import com.example.secyrity.model.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class RoleDaoImp implements RoleDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Role getRoleByName(String name) {
        Query query = entityManager.createQuery("select role from Role role where role.name = :name", Role.class);
        query.setParameter("name", name);
        return (Role) query.getSingleResult();
    }

    @Override
    public List<Role> getListOfRoles() {
        return entityManager.createQuery("select role from Role role", Role.class).getResultList();
    }
}
