package com.xiaofei.li.event;

import com.netflix.appinfo.InstanceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.eureka.server.event.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Author: xiaofei
 * Date: 2022-05-17, 14:07
 * Description:
 */
@Component
public class EurekaListenerEvents {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @EventListener
    public void listen(EurekaInstanceRegisteredEvent event){
        InstanceInfo instanceInfo = event.getInstanceInfo();
        String ip = instanceInfo.getIPAddr();
        String instanceId = instanceInfo.getInstanceId();
        logger.info("id: "+instanceId+" has already registered on Eureka server IP: "+ip);
    }
    @EventListener
    public void listen(EurekaInstanceRenewedEvent event){
        InstanceInfo instanceInfo = event.getInstanceInfo();
        String instanceId = instanceInfo.getInstanceId();
        logger.info(">>>>>>>>"+instanceId+" renewal activated<<<<<<<<");
    }
    @EventListener
    public void listen(EurekaRegistryAvailableEvent event){
        logger.info(">>>>>>>>Registry Center initiate event activation<<<<<<<<");
    }
    @EventListener
    public void listen(EurekaServerStartedEvent event){
        logger.info(">>>>>>>>Registry Center initiated<<<<<<<<");
    }
    @EventListener
    public void listen(EurekaInstanceCanceledEvent event){
        String serverId = event.getServerId();
        logger.info(">>>>>>>>"+serverId+" unregistered from Eureka server<<<<<<");
    }

}
