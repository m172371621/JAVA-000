### 第13课作业
##### 2、（必做）按自己设计的表结构，插入100万订单模拟数据，测试不同方式的插入效率。
- Mybatis Batch: 1万 ：2m4s47ms
```

public void batchInsert(List<MemberInfo> listData) {
    SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
    MemberInfoMapper sqlStatement = sqlSession.getMapper(MemberInfoMapper.class);
    for (MemberInfo listDatum : listData) {
        sqlStatement.insert(listDatum);
    }
    sqlSession.commit();
    sqlSession.close();
}
```

- JDBC 1万：5s
- JDBC 100万：3m45s39ms
```
public void batchInsertByJDBC(List<MemberInfo> listData) throws SQLException {
    String sql = "insert into t_member_info(user_code,user_nick_name,phone,deleted,activity,add_time,last_login_time)\n" +
            " values(?,?,?,?,?,?,?)";
    Connection connection = dataSource.getConnection();
    connection.setAutoCommit(false);
    PreparedStatement prest = connection.prepareStatement(sql,
            ResultSet.TYPE_SCROLL_SENSITIVE,
            ResultSet.CONCUR_READ_ONLY);
    for (MemberInfo memberInfo : listData) {
        prest.setString(1, memberInfo.getUserCode());
        prest.setString(2, memberInfo.getUserNickname());
        prest.setString(3, memberInfo.getPhone());
        prest.setBoolean(4, memberInfo.isDeleted());
        prest.setBoolean(5, memberInfo.isActivity());
        prest.setLong(6, memberInfo.getAddTime().getTime());
        prest.setLong(7, memberInfo.getLastLoginTime().getTime());
        prest.addBatch();
    }

    prest.executeBatch();
    connection.commit();
    connection.close();
}
```

### 第14课作业
##### 1、（选做）配置一遍异步复制，半同步复制、组复制。
由于用的是mac电脑
- 异步复制：
```
# 第一步：解压mysql文件 mysql-5.7.31-macos10.14-x86_64.tar
# 第二步：初始化mysql
./bin/mysqld --datadir=/Applications/Server/mysql/mysql-cluster/master/data  --initialize --initialize-insecure
./bin/mysqld --datadir=/Applications/Server/mysql/mysql-cluster/slave/data  --initialize --initialize-insecure
# 第三步：配置cluster.conf

[mysqld_multi]
mysqld     = /Applications/Server/mysql/bin/mysqld
mysqladmin = /Applications/Server/mysql/bin/mysqladmin
user       = root
password   = root

[mysqld3307]
server-id=3307
port=3307
log-bin=mysql-bin
log-error=/Applications/Server/mysql/mysql-cluster/master/mysqld.log
tmpdir=/Applications/Server/mysql/mysql-cluster/master
slow_query_log=on
slow_query_log_file =/Applications/Server/mysql/mysql-cluster/master/mysql-slow.log
long_query_time=1
socket=/Applications/Server/mysql/mysql-cluster/master/mysql_3307.sock
pid-file=/Applications/Server/mysql/mysql-cluster/master/mysql.pid
basedir=/Applications/Server/mysql/mysql-cluster/master
datadir=/Applications/Server/mysql/mysql-cluster/master/data

[mysqld3308]
server-id=3308
port=3308
log-bin=mysql-bin
log-error=/Applications/Server/mysql/mysql-cluster/slave/mysqld.log
tmpdir=/Applications/Server/mysql/mysql-cluster/slave
slow_query_log=on
slow_query_log_file =/Applications/Server/mysql/mysql-cluster/slave/mysql-slow.log
long_query_time=1
socket=/Applications/Server/mysql/mysql-cluster/slave/mysql_3308.sock
pid-file=/Applications/Server/mysql/mysql-cluster/slave/mysql.pid
basedir=/Applications/Server/mysql/mysql-cluster/slave
datadir=/Applications/Server/mysql/mysql-cluster/slave/data
read_only=1

[mysqld]
character_set_server=utf8

# 第四步：mysqld_multi启动多个实例
./bin/mysqld_multi --defaults-file=/Applications/Server/mysql/cluster.conf start

# 第五步：slave配置
CHANGE MASTER TO
    MASTER_HOST='localhost',  
    MASTER_PORT = 3307,
    MASTER_USER='repl',      
    MASTER_PASSWORD='123456',   
    MASTER_LOG_FILE='mysql-bin.000001',
    MASTER_LOG_POS=2489;
start slave;
stop slave;
```
<font color=red>总结：</font><br/>
1、主从复制因为是同步的binlog，有网络延迟容易引起主从数据步一致情况<br/>
2、最开始设置，如果主从的数据库结构不一致，启动同步会出现slave_sql_running状态未No的情况

- 半同步复制
```
# 第一步：主数据库安装插件
install plugin rpl_semi_sync_master soname 'semisync_master.so';
# 第二步：主数据设置参数
rpl_semi_sync_master_enabled = 1
rpl_semi_sync_master_timeout = 2000

# 第三步：从数据库安装插件
install plugin rpl_semi_sync_slave soname 'semisync_slave.so';
# 第四步：从数据库设置插件
rpl_semi_sync_slave_enabled = 1
```
常用命令 与参数 ：
```
show plugins;
show global variables like '%semi%';
stop slave io_thread;
start slave io_thread;
show status like '%Rpl_semi%';

rpl_semi_sync_master_wait_for_slave_count=1;设置同步成功多少个从库往前返回成功
```

##### 2、（必做）读写分离-动态切换数据源版本1.0
- 第一步：声明注解
```
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DynamicSwitchDataSource {
    String dataSource() default "";
}
```
- 第二步：使用ThreadLocal保存数据源
```
public class MyDataSourceHandler {
    private static ThreadLocal<String> handlerThredLocal = new ThreadLocal<String>();

    public static void putDataSource(String dataSource) {
        handlerThredLocal.set(dataSource);
    }

    public static String getDataSource() {
        return handlerThredLocal.get();
    }

    public static void remove() {
        handlerThredLocal.remove();
    }
}
```
- 第三步：实现接口AbstractRoutingDataSource
```
public class MyRoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return MyDataSourceHandler.getDataSource();
    }
}
```
- 第四步：Aop拦截DynamicSwitchDataSource注解
```java
@Aspect
@Component
@Order(1)
public class MyDataSourceAop {
    private static final Logger logger = LoggerFactory.getLogger(MyDataSourceAop.class);

    //@within在类上设置
    //@annotation在方法上进行设置
    @Pointcut("@annotation(com.lsf.studymybatis.config.DynamicSwitchDataSource)")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void doBefore(JoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        DynamicSwitchDataSource annotationClass = method.getAnnotation(DynamicSwitchDataSource.class);//获取方法上的注解
        if (annotationClass == null) {
            annotationClass = joinPoint.getTarget().getClass().getAnnotation(DynamicSwitchDataSource.class);//获取类上面的注解
            if (annotationClass == null) return;
        }
        //获取注解上的数据源的值的信息
        String dataSourceKey = annotationClass.dataSource();
        if (dataSourceKey != null) {
            //给当前的执行SQL的操作设置特殊的数据源的信息
            MyDataSourceHandler.putDataSource(dataSourceKey);
        }
        logger.info("AOP动态切换数据源，className" + joinPoint.getTarget().getClass().getName() + "methodName" + method.getName() + ";dataSourceKey:" + dataSourceKey == "" ? "默认数据源" : dataSourceKey);
    }

    @After("pointcut()")
    public void after(JoinPoint point) {
        //清理掉当前设置的数据源，让默认的数据源不受影响
        MyDataSourceHandler.remove();
    }
}

```
- 第五步：数据源声明
```
@Configuration
public class DatasourceConfig {

    @Bean(name = "dsmaster")
    @ConfigurationProperties(prefix = "spring.shardingsphere.datasource.dsmaster")
    @Primary
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "dsslave0")
    @ConfigurationProperties(prefix = "spring.shardingsphere.datasource.dsslave0")
    public DataSource secondaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Autowired
    @Qualifier("dsmaster")
    private DataSource dsMasterDataSource;

    @Autowired
    @Qualifier("dsslave0")
    private DataSource dsSlave0DataSource;

    @Bean
    public MyRoutingDataSource dataSource(){

        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("dsmaster", dsMasterDataSource);
        targetDataSources.put("dsslave0", dsSlave0DataSource);

        MyRoutingDataSource dataSource = new MyRoutingDataSource();
        //设置数据源映射
        dataSource.setTargetDataSources(targetDataSources);
        //设置默认数据源，当无法映射到数据源时会使用默认数据源
        dataSource.setDefaultTargetDataSource(dsMasterDataSource);
        dataSource.afterPropertiesSet();

        return dataSource;
    }
}
```
- 第六步：使用
```
@DynamicSwitchDataSource(dataSource = "dsmaster")
public List<MemberInfo> findAll(MemberInfo memberInfo) {
    return memberInfoMapper.findAll(memberInfo);
}

@DynamicSwitchDataSource(dataSource = "dsslave0")
public List<MemberInfo> findAllSlave(MemberInfo memberInfo) {
    return memberInfoMapper.findAll(memberInfo);
}
```
##### 3、（必做）读写分离-数据库框架版本2.0
- 第一步：添加shardingsphere引用
```
<dependency>
    <groupId>org.apache.shardingsphere</groupId>
    <artifactId>sharding-jdbc-spring-boot-starter</artifactId>
    <version>${shardingspere.version}</version>
</dependency>
```
- 第二步：shardingshpere数据库配置
```
spring.shardingsphere.datasource.names=dsmaster,dsslave0
spring.shardingsphere.datasource.dsmaster.type=com.zaxxer.hikari.HikariDataSource
spring.shardingsphere.datasource.dsmaster.driver-class-name=com.mysql.jdbc.Driver
spring.shardingsphere.datasource.dsmaster.jdbc-url=jdbc:mysql://localhost:3307/order_db
spring.shardingsphere.datasource.dsmaster.username=root
spring.shardingsphere.datasource.dsmaster.password=
spring.shardingsphere.datasource.dsslave0.type=com.zaxxer.hikari.HikariDataSource
spring.shardingsphere.datasource.dsslave0.driver-class-name=com.mysql.jdbc.Driver
spring.shardingsphere.datasource.dsslave0.jdbc-url=jdbc:mysql://localhost:3308/order_db
spring.shardingsphere.datasource.dsslave0.username=root
spring.shardingsphere.datasource.dsslave0.password=

spring.shardingsphere.masterslave.name=ds_ms
spring.shardingsphere.masterslave.masterDataSourceName=dsmaster
spring.shardingsphere.masterslave.slaveDataSourceNames=dsslave0

spring.shardingsphere.props.sql.show=true
spring.main.allow-bean-definition-overriding=true
```

### 未完成作业
### 第13课作业（UnDo)
1、（选做）用今天课上学习的知识，分析自己系统的SQL和表结构
3、（选做）按自己设计的表结构，插入1000万订单模拟数据，测试不同方式的插入效率。
4、（选做）使用不同的索引或组合，测试不同方式查询效率。
5、（选做）调整测试数据，使得数据尽量均匀，模拟1年时间内的交易，计算一年的销售报表：销售总额，订单数，客单价，每月销售量，前十的商品等等（可以自己设计更多指标）。
6、（选做）尝试自己做一个ID生成器（可以模拟Seq或Snowflake）。
7、（选做）尝试实现或改造一个非精确分页的程序。
### 第14课作业(UnDo)
1、（选做）配置一遍异步复制，半同步复制、组复制。
4、（选做）读写分离-数据库中间件版本3.0
5、（选做）配置MHA，模拟master宕机
6、（选做）配置MGR，模拟master宕机
7、（选做）配置Orchestrator，模拟master宕机，演练UI调整拓扑结构

