package com.daxiang.service;

import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;
import com.daxiang.api.MasterApi;
import com.daxiang.core.DeviceTestTaskExecutor_Web;
import com.daxiang.core.MobileDevice;
import com.daxiang.core.MobileDeviceHolder;
import com.daxiang.model.Response;
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

    /**
     * 启动任务
     */
    public void startJob() {
        log.info("启动定时任务");
        CronUtil.start(true);
    }

    /**
     * 添加任务
     *
     * @param cron
     * @param type
     */
    public Response add(String cron, int type) {
        log.info("添加任务" + cron + ";" + type);
        String taskId;
        if (type == 3) {
            taskId = CronUtil.schedule(cron, new Task() {
                @Override
                public void execute() {
                    addWeb();
                }
            });
        } else {
            taskId = CronUtil.schedule(cron, new Task() {
                @Override
                public void execute() {
                    addMobileTask();
                }
            });
        }
        return Response.success(taskId);
    }

    /**
     * 删除任务
     *
     * @param id
     */
    public Response del(String id) {
        log.info("删除任务");
        try {
            CronUtil.remove(id);
        } catch (Exception e) {
            log.error(e.getMessage());
            Response.fail("删除任务失败");
        }
        return Response.success("{}任务已删除", id);
    }
}
