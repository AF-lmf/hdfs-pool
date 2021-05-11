> 连接hdfs需要本地具有hadoop环境变量

## 使用commons-pool2 实现的HDFS连接池

#### 配置：

krb认证相关配置
```properties 
#是否开启krb5认证 
hadoop.hdfs.krb5.used-krb=true
#keytab文件路径
hadoop.hdfs.krb5.hdfs-key-type-path=/Users/lmf./home/lb/gio-script/hdfs.keytab
#krb5.config 文件路径
hadoop.hdfs.krb5.krb5-config-path=/Users/lmf./home/lb/gio-script/krb5.conf
#krb5 User
hadoop.hdfs.krb5.kerUser=hdfs/m01@TDH
```

连接池相关配置
```properties
hadoop.hdfs.pool.hdfsDefaultName=hdfs://nameservice1/
hadoop.hdfs.pool.hdfs-site-path=/Users/lmf./home/lb/gio-script/hdfs-site.xml
hadoop.hdfs.pool.maxTotal=10
hadoop.hdfs.pool.maxIdle=5
hadoop.hdfs.pool.minIdle=3
hadoop.hdfs.pool.maxWaitMillis=600000
hadoop.hdfs.pool.testWhileIdle=true
hadoop.hdfs.pool.minEvictableIdleTimeMillis= 82800000
hadoop.hdfs.pool.timeBetweenEvictionRunsMillis=30000
hadoop.hdfs.pool.numTestsPerEvictionRun=-1
```

####扫包
####随后注入HdfsClient进行对hdfs的操作，现有1.0版本提供了上传，下载，删除以及查看文件目录四个基本操作。
