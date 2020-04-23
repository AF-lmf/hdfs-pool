package com.lmf.hdfs.config;

import com.lmf.hdfs.poll.HdfsClient;
import com.lmf.hdfs.poll.HdfsFactory;
import com.lmf.hdfs.properties.HDFSProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * HDFS 连接池配置
 *
 * @author lmf
 * @date 2020/4/14
 */
@Configuration
public class HdfsConfig {

    private HDFSProperties hdfsProperties;

    public HdfsConfig(HDFSProperties hdfsProperties) {
        this.hdfsProperties = hdfsProperties;
    }


    @Bean(initMethod = "init", destroyMethod = "stop")
    public HdfsClient HdfsClient() {
        HdfsClient client = new HdfsClient();
        return client;
    }

    /**
     * TestWhileConfig - 在空闲时检查有效性, 默认false
     * MinEvictableIdleTimeMillis - 逐出连接的最小空闲时间
     * TimeBetweenEvictionRunsMillis - 逐出扫描的时间间隔(毫秒) 如果为负数则不运行逐出线程，默认-1
     * NumTestsPerEvictionRun - 每次逐出检查时 逐出的最大数目
     */
    @Bean
    public HdfsPoolConfig HdfsPoolConfig() {
        HdfsPoolConfig hdfsPoolConfig = new HdfsPoolConfig();
        hdfsPoolConfig.setTestWhileIdle(hdfsProperties.getPool().isTestWhileIdle());
        hdfsPoolConfig.setMinEvictableIdleTimeMillis(hdfsProperties.getPool().getMinEvictableIdleTimeMillis());
        hdfsPoolConfig.setTimeBetweenEvictionRunsMillis(hdfsProperties.getPool().getTimeBetweenEvictionRunsMillis());
        hdfsPoolConfig.setNumTestsPerEvictionRun(hdfsProperties.getPool().getNumTestsPerEvictionRun());
        hdfsPoolConfig.setMaxTotal(hdfsProperties.getPool().getMaxTotal());
        hdfsPoolConfig.setMaxIdle(hdfsProperties.getPool().getMaxIdle());
        hdfsPoolConfig.setMinIdle(hdfsProperties.getPool().getMinIdle());
        hdfsPoolConfig.setMaxWaitMillis(hdfsProperties.getPool().getMaxWaitMillis());
        return hdfsPoolConfig;
    }

    @Bean
    public HdfsFactory HdfsFactory() {
        return new HdfsFactory(hdfsProperties.getPool().getHdfsDefaultName(), hdfsProperties);
    }

}
