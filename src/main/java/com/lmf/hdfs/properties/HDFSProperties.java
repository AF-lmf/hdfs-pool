package com.lmf.hdfs.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * HDFS 连接池配置
 *
 * @author lmf.
 * @Date 2020/4/21
 */
@Data
@EnableConfigurationProperties
@Configuration
@ConfigurationProperties(prefix = "hadoop.hdfs")
public class HDFSProperties {

    /**
     * krb认证相关配置
     */
    private HDFSKrb5Properties krb5 = new HDFSKrb5Properties();

    /**
     * 连接池配置
     */
    private HDFSPoolProperties pool = new HDFSPoolProperties();

}
