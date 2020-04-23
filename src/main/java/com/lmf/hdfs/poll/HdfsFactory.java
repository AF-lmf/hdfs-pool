package com.lmf.hdfs.poll;

import com.lmf.hdfs.properties.HDFSProperties;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.io.IOException;

/**
 *
 * HDFS 工厂类
 *
 * @date 2020/4/14
 * @author lmf
 */
public class HdfsFactory implements PooledObjectFactory<Hdfs> {
    private HDFSProperties hdfsProperties;
    private final String url;
    public HdfsFactory(String url,HDFSProperties hdfsProperties){
        this.url = url;
        this.hdfsProperties = hdfsProperties;
    }

    @Override
    public PooledObject<Hdfs> makeObject() throws Exception {
        Hdfs hdfs = new Hdfs(url,hdfsProperties);
        hdfs.open();
        return new DefaultPooledObject<Hdfs>(hdfs);
    }

    @Override
    public void destroyObject(PooledObject<Hdfs> pooledObject) throws Exception {
        Hdfs hdfs = pooledObject.getObject();
        hdfs.close();
    }

    @Override
    public boolean validateObject(PooledObject<Hdfs> pooledObject) {
        Hdfs hdfs = pooledObject.getObject();
        try {
            return  hdfs.isConnected();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void activateObject(PooledObject<Hdfs> pooledObject) throws Exception {

    }

    @Override
    public void passivateObject(PooledObject<Hdfs> pooledObject) throws Exception {

    }
}
