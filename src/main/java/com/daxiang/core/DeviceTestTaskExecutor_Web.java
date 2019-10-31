package com.daxiang.core;

import com.daxiang.core.javacompile.JavaCompiler;
import com.daxiang.core.testng.TestNGCodeConverter;
import com.daxiang.core.testng.TestNGRunner;
import com.daxiang.model.devicetesttask.DeviceTestTask;
import com.daxiang.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by jiangyitao.
 */
@Slf4j
public class DeviceTestTaskExecutor_Web {
    /**
     * 执行测试任务队列
     */
    private final BlockingQueue<DeviceTestTask> testTaskQueue = new LinkedBlockingQueue<>();
    /**
     * 执行测试任务线程
     */
    private Thread executeTestTaskThread;


    public DeviceTestTaskExecutor_Web() {

        executeTestTaskThread = new Thread(() -> {
            while (true) {
                DeviceTestTask deviceTestTask;
                try {
                    deviceTestTask = testTaskQueue.take(); // 没有测试任务，线程阻塞在此
                } catch (InterruptedException e) {
                    // 调用executeTestTaskThread.interrupt()可以执行到这里
                    log.info("[Web自动化测试]停止获取测试任务");
                    break;
                }
                try {
                    executeTestTask(deviceTestTask);
                } catch (Exception e) {
                    log.error("[Web自动化测试]执行测试任务出错: {}", deviceTestTask.getTestTaskName(), e);
                }
            }
        });
        executeTestTaskThread.start();
    }


    /**
     * 提交测试任务
     *
     * @param deviceTestTask
     */
    public void commitTestTask(DeviceTestTask deviceTestTask) {
        if (!testTaskQueue.offer(deviceTestTask)) {
            throw new RuntimeException("提交测试任务失败: " + deviceTestTask.getTestTaskName());
        }
    }

    /**
     * 执行测试任务
     *
     * @param deviceTestTask
     */
    private void executeTestTask(DeviceTestTask deviceTestTask) throws Exception {
        log.info("[Web自动化测试]开始执行测试任务: {}", deviceTestTask.getTestTaskName());
        try {
            String className = "WebTest_" + UUIDUtil.getUUID();
            String code = new TestNGCodeConverter()
                    .setDeviceTestTaskId(deviceTestTask.getId())
                    .setGlobalVars(deviceTestTask.getGlobalVars())
                    .setBeforeClass(deviceTestTask.getBeforeClass())
                    .setAfterClass(deviceTestTask.getAfterClass())
                    .setBeforeMethod(deviceTestTask.getBeforeMethod())
                    .setAfterMethod(deviceTestTask.getAfterMethod())
                    .convert(deviceTestTask.getDeviceId(), className, deviceTestTask.getTestcases(), "/codetemplate", "browser.ftl");
            log.info("[Web自动化测试]转换代码: {}", code);
            // todo 捕获到DynamicCompilerException即编译失败，通知master纠正用例，否则错误的用例会无限下发给agent执行
            Class clazz = JavaCompiler.compile(className, code);
            TestNGRunner.runTestCases(new Class[]{clazz});
        }finally {

        }
    }
}