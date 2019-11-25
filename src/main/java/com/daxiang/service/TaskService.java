package com.daxiang.service;

import com.daxiang.api.MasterApi;
import com.daxiang.core.DeviceTestTaskExecutor_Web;
import com.daxiang.core.MobileDevice;
import com.daxiang.core.MobileDeviceHolder;
import com.daxiang.model.devicetesttask.DeviceTestTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TaskService {

    @Autowired
    private MasterApi masterApi;

    /**
     * 添加移动任务
     */
    public void addMobileTask() {
        // 在线闲置的设备
        List<MobileDevice> idleMobileDevices = MobileDeviceHolder.getAll().stream()
                .filter(MobileDevice::isIdle)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(idleMobileDevices)) {
            return;
        }
        idleMobileDevices.stream().parallel().forEach(idleMobileDevice -> {
            DeviceTestTask unStartDeviceTestTask = masterApi.getFirstUnStartDeviceTestTask(idleMobileDevice.getId());
            if (unStartDeviceTestTask != null) {
                idleMobileDevice.getDeviceTestTaskExecutor().commitTestTask(unStartDeviceTestTask);
                log.info("[自动化测试][{}]提交测试任务: {}", idleMobileDevice.getId(), unStartDeviceTestTask.getTestTaskName());
            }
        });
    }

    /**
     * 添加web自动化测试任务
     */
    public void addWeb() {
        //查询项目为web的最早未执行的任务
        DeviceTestTask unStartDeviceTestTask = masterApi.getFirstUnStartDeviceTestTask();
        if (unStartDeviceTestTask != null) {
            log.info("[Web端自动化--开始执行]:{}", unStartDeviceTestTask.getTestTaskName());
            DeviceTestTaskExecutor_Web deviceTestTaskExecutor = new DeviceTestTaskExecutor_Web();
            deviceTestTaskExecutor.commitTestTask(unStartDeviceTestTask);
        } else {
            log.info("[无Web端自动化测试任务]");
        }
    }


}
