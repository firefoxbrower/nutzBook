package net.wendal.nutzbook;

import net.wendal.nutzbook.bean.User;
import org.nutz.dao.Dao;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.Ioc;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

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
            User user = new User();
            user.setName("admin");
            user.setPassword("123456");
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            dao.insert(user);
        }
    }

    @Override
    public void destroy(NutConfig nutConfig) {
          // webapp销毁之前执行的逻辑
         // 这个时候依然可以从nc取出ioc, 然后取出需要的ioc 对象进行操作
    }
}
