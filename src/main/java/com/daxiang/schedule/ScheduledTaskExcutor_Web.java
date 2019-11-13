package com.daxiang.schedule;

import com.daxiang.api.MasterApi;
import com.daxiang.core.DeviceTestTaskExecutor_Web;
import com.daxiang.model.devicetesttask.DeviceTestTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by jiangyitao.
 */
@Slf4j
@Component
public class ScheduledTaskExcutor_Web {

    @Autowired
    private MasterApi masterApi;

    @Value("${web}")
    private Boolean isWeb;
    /**
     * 定时检测设备的测试任务
     */
    @Scheduled(fixedRate = 10000)
    public void commitDeviceTestTask() {
        if(isWeb){
            //查询项目为web的最早未执行的任务
            DeviceTestTask unStartDeviceTestTask = masterApi.getFirstUnStartDeviceTestTask();
            if (unStartDeviceTestTask != null) {
                log.info("[Web端自动化--开始执行]:{}", unStartDeviceTestTask.getTestTaskName());
                DeviceTestTaskExecutor_Web deviceTestTaskExecutor = new DeviceTestTaskExecutor_Web();
                deviceTestTaskExecutor.commitTestTask(unStartDeviceTestTask);
            } else {
                //log.info("[无Web端自动化测试任务]");
            }
        }
    }
}
