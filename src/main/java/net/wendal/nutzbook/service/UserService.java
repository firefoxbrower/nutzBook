package net.wendal.nutzbook.service;

import net.wendal.nutzbook.bean.User;


public interface UserService {
    
   User add(String name, String password);

   int fetch(String username, String password) ;
   
   void updatePassword(int userId, String password);
}
