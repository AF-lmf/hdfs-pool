package com.lmf.hdfs.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "hadoop.hdfs.pool")
public class HDFSPoolProperties {


    private String hdfsDefaultName;
    /**
     * hdfs-site.xml文件全路径
     */
    private String hdfsSitePath;

    private int maxTotal;

    private int maxIdle;

    private int minIdle;

    private int maxWaitMillis;

    private boolean testWhileIdle;

    private long minEvictableIdleTimeMillis = 60000;

    private long timeBetweenEvictionRunsMillis = 30000;

    private int numTestsPerEvictionRun = -1;

}
