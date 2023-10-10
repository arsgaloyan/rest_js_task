package com.example.secyrity.service;

import com.example.secyrity.configs.WebSecurityConfig;
import com.example.secyrity.dao.UserDao;
import com.example.secyrity.model.Role;
import com.example.secyrity.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {

   private final UserDao userDao;

   private final PasswordEncoder bCryptPasswordEncoder = WebSecurityConfig.passwordEncoder();

   public UserServiceImp(UserDao userDao) {
      this.userDao = userDao;
   }

   @Transactional
   public User getUserByUsername(String username) {
      return userDao.getUserByUsername(username);
   }

   @Transactional
   public User getUserByEmail(String email) {
      return userDao.getUserByEmail(email);
   }

   @Transactional
   public User getUserById(int id) {
      return userDao.getUserById(id);
   }

   @Transactional
   public List<User> getListOfUsers() {
      return userDao.getListOfUsers();
   }

   @Transactional
   public void addUser(User user) {
      user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
      userDao.addUser(user);
   }

   @Transactional
   public void deleteUserById(int id) {
      userDao.deleteUserById(id);
   }

   @Transactional
   public void updateUser(User user) {
      user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
      userDao.updateUser(user);
   }

   @Override
   @Transactional
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      User user = getUserByEmail(username);
      if (user == null) {
         throw new UsernameNotFoundException(String.format("User '%s' not found", username));
      }
      return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
              mapRolesToAuthorities(user.getRoles()));
   }

   private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
      return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
   }
}
