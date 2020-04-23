package com.lmf.hdfs.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 *
 * HDFS 连接池配置
 *
 * @date 2020/4/14
 * @author lmf
 */
public class HdfsPoolConfig extends GenericObjectPoolConfig {

    public HdfsPoolConfig(){}

    /**
     * TestWhileConfig - 在空闲时检查有效性, 默认false
     * MinEvictableIdleTimeMillis - 逐出连接的最小空闲时间
     * TimeBetweenEvictionRunsMillis - 逐出扫描的时间间隔(毫秒) 如果为负数则不运行逐出线程，默认-1
     * NumTestsPerEvictionRun - 每次逐出检查时 逐出的最大数目
     * */
    public HdfsPoolConfig(boolean testWhileIdle, long minEvictableIdleTimeMillis, long timeBetweenEvictionRunsMillis, int numTestsPerEvictionRun){
        this.setTestWhileIdle(testWhileIdle);
        this.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        this.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        this.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
    }


}
