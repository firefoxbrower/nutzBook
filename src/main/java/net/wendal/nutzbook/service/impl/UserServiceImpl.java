package net.wendal.nutzbook.service.impl;

import net.wendal.nutzbook.bean.User;
import net.wendal.nutzbook.service.UserService;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.random.R;
import org.nutz.service.IdNameEntityService;

import java.util.Date;



@IocBean(fields="dao",name="UserService") //含义是需要注入父类的dao属性
public class UserServiceImpl extends IdNameEntityService<User> implements UserService {
    
    @Override
    public User add(String name, String password) {
        User user = new User();
        user.setName(name.trim());
        user.setSalt(R.UU16());
        //sha256加盐算法,将对应shiro.ini中配置 请不要使用明文存储密码.
        user.setPassword(new Sha256Hash(password, user.getSalt()).toHex()); 
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        return dao().insert(user);
    }

    @Override
    public int fetch(String username, String password) {
        User user = fetch(username);
        if (user == null) {
            return -1;
        }
        String _pass = new Sha256Hash(password, user.getSalt()).toHex();
        if(_pass.equalsIgnoreCase(user.getPassword())) {
            return user.getId();
        }
        return -1;
    }

    @Override
    public void updatePassword(int userId, String password) {
        User user = fetch(userId);
        if (user == null) {
            return;
        }
        user.setSalt(R.UU16());
        user.setPassword(new Sha256Hash(password, user.getSalt()).toHex());
        user.setUpdateTime(new Date());
        dao().update(user, "^(password|salt|updateTime)$");
    }
}
