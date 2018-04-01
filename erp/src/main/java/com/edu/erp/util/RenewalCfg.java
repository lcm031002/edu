package com.edu.erp.util;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

public class RenewalCfg {
    private static final Log logger = LogFactory.getLog(RenewalCfg.class);

    private Properties props = null;

    private long lastModifiedTime = 0;

    Resource res = null;

    private static final RenewalCfg INSTANCE = new RenewalCfg();

    public static RenewalCfg getInstance() {
        return INSTANCE;
    }

    private RenewalCfg() {
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        res = resourceResolver.getResource("classpath:/properties/renewal_cfg.properties");
        try {
            lastModifiedTime = res.lastModified();

            props = new Properties();
            props.load(res.getInputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error("read dxb_city_cfg.properties exception:" + e);
        }
    }

    final public String getConfigItem(String name, String defualtVal) {
        try {
            long newTime = res.lastModified();
            if (newTime > lastModifiedTime) {
                props.clear();
                props.load(res.getInputStream());
            }
            lastModifiedTime = newTime;
            String val = props.getProperty(name);

            if (val == null) {
                return defualtVal;
            }
        } catch (IOException e) {
            logger.error("getConfigItem exception:" + e);
        }
        return props.getProperty(name);
    }

    final public String getConfigItem(String name) {
        try {
            long newTime = res.lastModified();
            if (newTime > lastModifiedTime) {
                props.clear();
                props.load(res.getInputStream());
            }
            lastModifiedTime = newTime;

            return props.getProperty(name);
        } catch (IOException e) {
            logger.error("getConfigItem exception:" + e);
        }
        return props.getProperty(name);
    }

}
