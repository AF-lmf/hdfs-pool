package com.lmf.hdfs.poll;

import com.lmf.hdfs.config.HdfsPoolConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


/**
 * HDFS 客户端操作类 调用该类实现各种hdfs操作
 * 目前提供了文件上传 下载 删除 查看目录 四个基本功能
 *
 * @author lmf
 * @date 2020/4/14
 */
@Slf4j
public class HdfsClient {


    private HdfsPool hdfsPool;

    @Autowired
    private HdfsPoolConfig hdfsPoolConfig;
    @Autowired
    private HdfsFactory hdfsFactory;

    public HdfsClient() {
    }

    public void init() {
        hdfsPool = new HdfsPool(hdfsFactory, hdfsPoolConfig);
    }

    public void stop() {
        hdfsPool.close();
    }

    public void FileUpload(String localPath, String targetPaths) {
        Hdfs hdfs = null;
        try {
            hdfs = hdfsPool.borrowObject();
            hdfs.FileUpload(localPath, targetPaths);
        } catch (Exception e) {
            log.error("上传文件失败！", e);
        } finally {
            if (null != hdfs) {
                hdfsPool.returnObject(hdfs);
            }
        }
    }

    public void download(String sourcePath, String targetPath) {
        Hdfs hdfs = null;
        try {
            hdfs = hdfsPool.borrowObject();
            hdfs.download(sourcePath, targetPath);
        } catch (Exception e) {
            log.error("下载文件：" + sourcePath + " 失败！", e);
        } finally {
            if (null != hdfs) {
                hdfsPool.returnObject(hdfs);
            }
        }
    }

    public void deleteFile(String targetPath) {
        Hdfs hdfs = null;
        try {
            hdfs = hdfsPool.borrowObject();
            hdfs.deleteFile(targetPath);
        } catch (Exception e) {
            log.error("删除文件：" + targetPath + " 失败！", e);
        } finally {
            if (null != hdfs) {
                hdfsPool.returnObject(hdfs);
            }
        }
    }

    public List<String> catFolder(String hPath) {
        Hdfs hdfs = null;
        try {
            hdfs = hdfsPool.borrowObject();
            List<String> list = hdfs.catFolder(hPath);
            return list;
        } catch (Exception e) {
            log.error("查看文件目录：" + hPath + " 失败！", e);
        } finally {
            if (null != hdfs) {
                hdfsPool.returnObject(hdfs);
            }
        }
        return new ArrayList<>();
    }


}
