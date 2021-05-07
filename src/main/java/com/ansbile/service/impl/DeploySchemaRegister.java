package com.ansbile.service.impl;

import com.ansbile.service.DeploySchemaService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cuikai on 2021/5/7.
 */
@Service
public class DeploySchemaRegister implements InitializingBean, ApplicationContextAware {
    private Map<String, DeploySchemaService> serviceImpMap = new HashMap<>();

    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() {
        Map<String, DeploySchemaService> beanMap = applicationContext.getBeansOfType(DeploySchemaService.class);
        for (DeploySchemaService DeploySchemaService : beanMap.values()) {
            String name = DeploySchemaService.getClass().getSimpleName();
            serviceImpMap.put(name, DeploySchemaService);
        }
    }

    public DeploySchemaService getDeploySchemaService(String name) {
        return serviceImpMap.get(name);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
