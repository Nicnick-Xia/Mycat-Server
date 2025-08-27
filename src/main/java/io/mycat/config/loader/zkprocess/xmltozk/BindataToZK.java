package io.mycat.config.loader.zkprocess.xmltozk;

import com.google.common.io.Files;
import io.mycat.config.model.SystemConfig;
import io.mycat.util.ZKUtils;
import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Paths;

/**
 * Created by magicdoom on 2016/10/26.
 *  only for test
 */
public class BindataToZK {
    private static final Logger LOGGER = LoggerFactory.getLogger(BindataToZK.class);
    public static void main(String[] args) {
        File file = new File(SystemConfig.getHomePath()+ "/conf","ruledata" );
        if(file.exists()&&file.isDirectory())
        {
            File[] binFiles=file.listFiles();
            for (File binFile : binFiles) {

           String path=     ZKUtils.getZKBasePath()+"ruledata/"+binFile.getName();
                CuratorFramework zk= ZKUtils.getConnection();
                try {
                    zk.create().creatingParentsIfNeeded().forPath(path)  ;
                    zk.setData().forPath(path, Files.toByteArray(binFile)) ;
                } catch (Exception e) {
                    LOGGER.error("BindataToZK error creating/updating path", e);
                }

            }

        }
    }
}
