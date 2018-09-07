package net.wendal.nutzbook;

import org.nutz.mvc.annotation.*;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

/**
 * Created by Administrator on 2018/9/5.
 */

@SetupBy(value=MainSetup.class)
@IocBy(type=ComboIocProvider.class, args={"*js", "ioc/",
        "*anno", "net.wendal.nutzbook",
        "*tx", // 事务拦截 aop
        "*async",// 异步执行aop
        "*quartz" // 定时任务
         }) 
@Modules(scanPackage=true)
@Ok("json:full")
@Fail("jsp:jsp.500")
@Localization(value="msg/", defaultLocalizationKey="zh-CN")
@ChainBy(args="mvc/nutzbook-mvc-chain.js")
@SessionBy(ShiroSessionProvider.class) //使用Shiro的Session替换NutFilter作用域内的Session
public class MainModule {
    
    
    
}
