package com.lmf.hdfs.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * HDFS Krb5认证相关配置
 *
 * @author lmf.
 * @Date 2020/4/21
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "hadoop.hdfs.krb5")
public class HDFSKrb5Properties {

    /**
     * 是否启用krb5认证
     */
    private boolean usedKrb = false;
    /**
     * krb5认证用户
     */
    private String kerUser = "hdfs/m01@TDH";
    /**
     * krb5认证文件全路径
     */
    private String krb5ConfigPath = "/home/lb/gio-script/";
    private String hdfsKeyTypePath = "/home/lb/gio-script/";


}
