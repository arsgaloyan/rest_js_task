package com.example.secyrity.dao;

import com.example.secyrity.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


@Repository
public class UserDaoImp implements UserDao {

   @PersistenceContext
   private EntityManager entityManager;

   @Override
   public void addUser(User user) {
      entityManager.merge(user);
   }

   @Override
   public void deleteUserById(int id) {
      entityManager.remove(getUserById(id));
   }

   @Override
   public void updateUser(User user) {
      entityManager.merge(user);
   }

   @Override
   public User getUserById(int id) {
      return entityManager.find(User.class, id);
   }

   @Override
   public User getUserByUsername(String username) {
      Query query = entityManager.createQuery("select user from User user where user.username = :username", User.class);
      query.setParameter("username", username);
      return (User) query.getSingleResult();
   }

   @Override
   public User getUserByEmail(String email) {
      Query query = entityManager.createQuery("select user from User user join fetch user.roles where user.email = :email", User.class);
      query.setParameter("email", email);
      return (User) query.getSingleResult();
   }

   @Override
   public List<User> getListOfUsers() {
      return entityManager.createQuery("select user from User user", User.class).getResultList();
   }
}
