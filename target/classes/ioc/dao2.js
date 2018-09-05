
var ioc = {
    dataSource : {
        type : "com.alibaba.druid.pool.DruidDataSource",
        events : {
            create : "init",
            depose : 'close'
        },
        fields : {
            driverClassName : "oracle.jdbc.driver.OracleDriver",
            url : "jdbc:oracle:thin:@localhost:1521:orcl",
            username : "jsgat",
            password : "jsgat",
            testWhileIdle : true, // 非常重要,预防mysql的8小时timeout问题
            validationQuery : "select 1 from dual" , // Oracle的话需要改成 select 1 from dual
            maxActive : 100

        }
    },
    dao : {
        type : "org.nutz.dao.impl.NutDao",
        args : [{refer:"dataSource"}]
    }
};