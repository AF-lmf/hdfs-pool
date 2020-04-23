package com.lmf.hdfs.poll;

import com.lmf.hdfs.properties.HDFSProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * HDFS 连接实例
 *
 * @author lmf
 * @date 2020/4/14
 */
@Slf4j
class Hdfs {

    private HDFSProperties hdfsProperties;

    private FileSystem fs;
    private final String url;

    public Hdfs(String url, HDFSProperties hdfsProperties) {
        this.url = url;
        this.hdfsProperties = hdfsProperties;
    }

    /**
     * kerbers认证调用方式
     *
     * @param config
     */
    private void kerberosConfig(Configuration config) {
        //设置java安全krb5配置,其中krb5.conf文件可以从成功开启kerberos的集群任意一台节点/etc/krb5.conf拿到，放置本地
        System.setProperty("java.security.krb5.conf", hdfsProperties.getKrb5().getKrb5ConfigPath());
        //指定认证方式为Kerberos
        config.set("hadoop.security.authentication", "Kerberos");
        try {
            UserGroupInformation.setConfiguration(config);
            //对应kerberos principal的keytab文件,从服务器获取放置本地
            UserGroupInformation.loginUserFromKeytab(hdfsProperties.getKrb5().getKerUser(), hdfsProperties.getKrb5().getHdfsKeyTypePath());
        } catch (IOException e) {
            log.error("Kerbers认证异常 ！", e);
        }
    }

    public void open() {
        try {
            Configuration conf = new Configuration();
            //conf.set("fs.defaultFS", url);
            conf.set("fs.defaultFS", hdfsProperties.getPool().getHdfsDefaultName());
            conf.setBoolean("fs.hdfs.impl.disable.cache", true);
            conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
            conf.set("dfs.client.block.write.replace-datanode-on-failure.policy", "NEVER");
            //获取服务器hdfs-site.xml配置文件放置本地或者Resource目录下
            conf.addResource(new Path(hdfsProperties.getPool().getHdfsSitePath()));
            if (hdfsProperties.getKrb5().isUsedKrb()) {
                kerberosConfig(conf);
            }
            fs = FileSystem.get(conf);
            log.info("[Hadoop]创建实例成功");
        } catch (Exception e) {
            log.error("[Hadoop]创建实例失败", e);
        }
    }

    public void close() {
        try {
            if (null != fs) {
                fs.close();
                log.info("[Hadoop]关闭实例成功");
            }
        } catch (Exception e) {
            log.error("[Hadoop]关闭实例失败", e);
        }
    }

    public boolean isConnected() throws IOException {
        return fs.exists(new Path("/"));
    }

    /**
     * 上传文件
     *
     * @throws IOException
     */
    public void FileUpload(String localPath, String targetPaths) throws IOException {
        //本地文件上传到 hdfs
        fs.copyFromLocalFile(new Path(localPath), new Path(targetPaths));
    }

    /**
     * 删除文件
     *
     * @throws Exception
     */
    public void deleteFile(String targetPath) throws Exception {
        Path path = new Path(targetPath);
        boolean exists = fs.exists(path);
        if (exists) {
            boolean b = fs.delete(new Path(targetPath), true);
            log.debug("Whether to delete the file : " + b);
        } else {
            log.debug("Remove the HDFS file . path not exists .");
        }
    }

    /**
     * 文件下载
     *
     * @throws Exception
     */
    public void download(String sourcePath, String targetPath) throws IOException {
        Path srcpath = new Path(sourcePath);
        InputStream open = fs.open(srcpath);
        File file = new File(targetPath);
        File fileParent = file.getParentFile();
        if (!fileParent.exists()) {
            fileParent.mkdirs();// 能创建多级目录
        }
        if (!file.exists()) {
            file.createNewFile();//有路径才能创建文件
        }
        IOUtils.copyBytes(open, new FileOutputStream(file), 2048, true);
    }

    /**
     * 查看文件目录
     *
     * @throws Exception
     */
    public List<String> catFolder(String hPath) throws IOException {
        ArrayList<String> filenames = new ArrayList<>();
        Path path = new Path(hPath);
        if (fs.exists(path)) {
            FileStatus[] fileList = fs.listStatus(path);
            for (FileStatus f : fileList) {
                filenames.add(f.getPath().getName());
            }
        }
        return filenames;
    }


}
