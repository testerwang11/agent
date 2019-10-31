package com.daxiang.core;

import com.daxiang.api.MasterApi;
import com.daxiang.model.Device;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

/**
 * Created by jiangyitao.
 */
@Slf4j
public class AgentCloseListener implements ApplicationListener<ContextClosedEvent> {

    /**
     * 停止运行agent，有些启动的appium server没有自动关闭，在此处再处理一下关闭
     *
     * @param contextClosedEvent
     */
    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        log.info("[agent]closing...");
        //关闭移动端
        MobileDeviceHolder.getAll().forEach(device -> {
            log.info("[{}]quit appium driver", device.getId());
            device.quitAppiumDriver();
            log.info("[{}]stop appium server", device.getId());
            device.getAppiumServer().stop();
            log.info("[{}]stop browser server", device.getId());
        });

    }
}
