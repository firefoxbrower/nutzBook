package net.wendal.nutzbook;

import net.wendal.nutzbook.bean.User;

import net.wendal.nutzbook.service.UserService;
import net.wendal.nutzbook.service.impl.UserServiceImpl;
import org.apache.commons.mail.HtmlEmail;
import org.nutz.dao.Dao;
import org.nutz.dao.util.Daos;
import org.nutz.integration.quartz.NutQuartzCronJobFactory;
import org.nutz.ioc.Ioc;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;
import org.quartz.Scheduler;

import java.util.Date;

/**
 * Created by Administrator on 2018/9/5.
 */
public class MainSetup  implements Setup {
    @Override
    public void init(NutConfig nc) {
        
        Ioc ioc = nc.getIoc();
        
        Dao dao = ioc.get(Dao.class);

        // 如果提示没有createTablesInPackage方法,请确认用了最新版的nutz,且老版本的nutz已经删除干净
        Daos.createTablesInPackage(dao, "net.wendal.nutzbook", false);

        // 初始化默认根用户
        if (dao.count(User.class) == 0) {
            /*User user = new User();
            user.setName("admin");
            user.setPassword("123456");
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            dao.insert(user);*/

            UserService us = ioc.get(UserServiceImpl.class);
            us.add("admin", "123456");
        }

        /**
         * 获取quartz的Scheduler,这样就自动触发了计划任务的启动 
         * */   
        //ioc.get(Scheduler.class);
        
        /**
         * NutIoc中的Bean是完全懒加载模式的,不获取就不生成,不初始化,所以,为了触发计划任务的加载
         *  获取NutQuartzCronJobFactory从而触发计划任务的初始化与启动
         * */
        ioc.get(NutQuartzCronJobFactory.class);
        
        
        // 测试发送邮件
        try {
            HtmlEmail email = ioc.get(HtmlEmail.class);
            email.setSubject("测试NutzBook");
            email.setMsg("This is a test mail ... :-)" + System.currentTimeMillis());
            // 收件人邮箱
            //email.addTo("173343491@qq.com");//请务必改成您自己的邮箱啊!!!
            email.addTo("173343494@qq.com");//请务必改成您自己的邮箱啊!!!
            email.buildMimeMessage();
            email.sendMimeMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }

      // 表结构自动修改
        Daos.migration(dao, User.class, true, false, false);
    }

    @Override
    public void destroy(NutConfig nc) {
          // webapp销毁之前执行的逻辑
         // 这个时候依然可以从nc取出ioc, 然后取出需要的ioc 对象进行操作
 
    }
}
