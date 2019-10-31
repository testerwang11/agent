/*
package com.daxiang.schedule;

import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;
import com.daxiang.api.MasterApi;
import com.daxiang.core.MobileDevice;
import com.daxiang.core.MobileDeviceHolder;
import com.daxiang.mbg.mapper.TestTaskMapper;
import com.daxiang.mbg.po.TestTask;
import com.daxiang.mbg.po.TestTaskExample;
import com.daxiang.model.devicetesttask.DeviceTestTask;
import com.daxiang.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

*/
/**
 * Created by jiangyitao.
 *//*

@Slf4j
@Component
public class ScheduledTaskExcutor_Test {

    @Autowired
    private MasterApi masterApi;
    @Value("${web}")
    private Boolean isWeb;

    @Autowired
    private TestTaskMapper testTaskMapper;


    */
/**
     * 定时检测设备的测试任务
     *//*

    public void commitDeviceTestTask() {
        log.info("开始执行任务");
        if (!isWeb) {
            // 在线闲置的设备
            List<MobileDevice> idleMobileDevices = MobileDeviceHolder.getAll().stream()
                    .filter(MobileDevice::isIdle)
                    .collect(Collectors.toList());
            if (CollectionUtils.isEmpty(idleMobileDevices)) {
                //log.info("[无移动端自动化测试任务]");
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

    */
/**
     * 查询待执行的测试任务
     *
     * @return
     *//*

    private List<com.daxiang.mbg.po.TestTask> queryReadyTask() {
        TestTaskExample testTaskExample = new TestTaskExample();
        TestTaskExample.Criteria criteria = testTaskExample.createCriteria();
        criteria.andStatusEqualTo(0);
        return testTaskMapper.selectByExample(testTaskExample);
    }

    //@Scheduled(fixedRate = 30000)
    public void scanTask() {
        List<com.daxiang.mbg.po.TestTask> taskList = queryReadyTask();
        if (taskList.size() != 0) {
            for (TestTask testTask : taskList) {
                if (!StringUtil.isEmpty(testTask.getTimeConfig())) {
                    log.info("配置时间:" + testTask.getTimeConfig());
                    String taskId = CronUtil.schedule(testTask.getTimeConfig(), new Task() {
                        @Override
                        public void execute() {
                            commitDeviceTestTask();
                        }
                    });
                    //添加完毕后，将任务状态修改为2
                    testTask.setStatus(2);
                    testTaskMapper.updateByPrimaryKey(testTask);
                }
            }
            checkIsStart();
        } else {
            log.info("没有未执行的定时任务");
        }
        */
/*try {

            String cron = test.getTimeConfig();
            int id = test.getId();
            log.info("定任务时间配置:" + test.getName() + test.getCommitTime());
            log.info("定任务ID:" + id);
            *//*
*/
/*if (!StringUtils.isEmpty(cron) && !cron.equals("null")) {
                String taskId = CronUtil.schedule(cron, new Task() {
                    @Override
                    public void execute() {
                        commitDeviceTestTask();
                        log.info("我好了");
                    }
                });
                log.info("定时任务ID:" + taskId);
            }
            checkIsStart();
            //修改任务状态
            cronMapper.changTaskStatus(test);*//*
*/
/*
        } catch (Exception e) {
        }*//*

    }

    private void checkIsStart() {
        try {
            CronUtil.start(true);
        } catch (Exception e) {
        }
    }


}
*/
