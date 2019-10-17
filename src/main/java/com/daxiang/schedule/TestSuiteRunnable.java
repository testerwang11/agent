package com.daxiang.schedule;

import com.daxiang.api.MasterApi;
import com.daxiang.core.MobileDevice;
import com.daxiang.core.MobileDeviceHolder;
import com.daxiang.model.devicetesttask.DeviceTestTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TestSuiteRunnable implements Runnable{

    @Autowired
    private MasterApi masterApi;

    @Override
    public void run() {
        // 在线闲置的设备
        List<MobileDevice> idleMobileDevices = MobileDeviceHolder.getAll().stream()
                .filter(MobileDevice::isIdle)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(idleMobileDevices)) {
            return;
        }

        idleMobileDevices.stream().parallel().forEach(idleMobileDevice -> {
            // 获取最早的一个未开始的设备测试任务
            DeviceTestTask unStartDeviceTestTask = masterApi.getFirstUnStartDeviceTestTask(idleMobileDevice.getId());
            if (unStartDeviceTestTask != null) {
                idleMobileDevice.getDeviceTestTaskExecutor().commitTestTask(unStartDeviceTestTask);
                log.info("[自动化测试][{}]提交测试任务: {}", idleMobileDevice.getId(), unStartDeviceTestTask.getTestTaskName());
            }
        });
    }
}
