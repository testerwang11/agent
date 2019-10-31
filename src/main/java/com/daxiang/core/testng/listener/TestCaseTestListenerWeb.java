package com.daxiang.core.testng.listener;

import com.daxiang.api.MasterApi;
import com.daxiang.core.BaseUITest;
import com.daxiang.core.BrowserDeviceHolder;
import com.daxiang.core.web.WebDevice;
import com.daxiang.core.web.WebDriverService;
import com.daxiang.model.action.Step;
import com.daxiang.model.devicetesttask.DeviceTestTask;
import com.daxiang.model.devicetesttask.Testcase;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by jiangyitao.
 */
@Slf4j
public class TestCaseTestListenerWeb extends TestListenerAdapter {

    private static final ThreadLocal<WebDevice> TL_WEB_DEVICE = new ThreadLocal<>();
    private static final ThreadLocal<Integer> TL_DEVICE_TEST_TASK_ID = new ThreadLocal<>();
    private static final ThreadLocal<Integer> TL_TEST_CASE_ID = new ThreadLocal<>();

    /**
     * 每个设备开始测试调用的方法，这里可能有多个线程同时调用
     *
     * @param testContext
     */
    @Override
    public void onStart(ITestContext testContext) {
        // deviceId_deviceTestTaskId_testcaseId
        String[] testDesc = testContext.getAllTestMethods()[0].getDescription().split("_");
        Integer deviceTestTaskId = Integer.parseInt(testDesc[1]);
        BrowserDeviceHolder.get("");
        log.info("[Web端自动化测试]onStart, deviceTestTaskId：{}", deviceTestTaskId);

        //TL_WEB_DEVICE.set(mobileDevice);
        TL_DEVICE_TEST_TASK_ID.set(deviceTestTaskId);

        DeviceTestTask deviceTestTask = new DeviceTestTask();
        deviceTestTask.setId(deviceTestTaskId);
        deviceTestTask.setStartTime(new Date());
        deviceTestTask.setStatus(DeviceTestTask.RUNNING_STATUS);
        MasterApi.getInstance().updateDeviceTestTask(deviceTestTask);
    }

    /**
     * 每个设备结束测试调用的方法，这里可能有多个线程同时调用
     *
     * @param testContext
     */
    @Override
    public void onFinish(ITestContext testContext) {
        Integer deviceTestTaskId = TL_DEVICE_TEST_TASK_ID.get();
        log.info("[Web端自动化测试]onFinish, deviceTestTaskId: {}", deviceTestTaskId);
        DeviceTestTask deviceTestTask = new DeviceTestTask();
        deviceTestTask.setId(deviceTestTaskId);
        deviceTestTask.setEndTime(new Date());
        deviceTestTask.setStatus(DeviceTestTask.FINISHED_STATUS);
        //testcase.setVideoUrl(getVideoDownloadUrl());
        MasterApi.getInstance().updateDeviceTestTask(deviceTestTask);
    }

    /**
     * 每个设备执行每条测试用例前调用的方法，这里可能有多个线程同时调用
     * 目前设计的：每条用例单独录制视频
     *
     * @param tr
     */
    @Override
    public void onTestStart(ITestResult tr) {
        Integer deviceTestTaskId = TL_DEVICE_TEST_TASK_ID.get();
        Integer testcaseId = Integer.parseInt(tr.getMethod().getDescription().split("_")[2]);
        TL_TEST_CASE_ID.set(testcaseId);
        log.info("[Web端自动化测试]onTestStart, testcaseId: {}", testcaseId);
        Testcase testcase = new Testcase();
        testcase.setId(testcaseId);
        testcase.setStartTime(new Date());
        MasterApi.getInstance().updateTestcase(deviceTestTaskId, testcase);
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        Integer testcaseId = TL_TEST_CASE_ID.get();
        log.info("[Web端自动化测试]onTestSuccess, testcaseId: {}", testcaseId);
        Testcase testcase = new Testcase();
        testcase.setId(testcaseId);
        testcase.setEndTime(new Date());
        testcase.setStatus(Testcase.PASS_STATUS);
        //testcase.setVideoUrl(getVideoDownloadUrl());
        MasterApi.getInstance().updateTestcase(TL_DEVICE_TEST_TASK_ID.get(), testcase);
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        WebDriver driver = ((BaseUITest) tr.getInstance()).getDriver();


        Integer testcaseId = TL_TEST_CASE_ID.get();
        log.error("[Web端自动化测试][{}]onTestFailure, testcaseId: {}", testcaseId, tr.getThrowable());
        Testcase testcase = new Testcase();
        testcase.setId(testcaseId);
        testcase.setEndTime(new Date());
        testcase.setStatus(Testcase.FAIL_STATUS);
        testcase.setFailImgUrl(getScreenshotDownloadUrl(driver));
        testcase.setFailInfo(tr.getThrowable().getMessage());
        //testcase.setVideoUrl(getVideoDownloadUrl());
        MasterApi.getInstance().updateTestcase(TL_DEVICE_TEST_TASK_ID.get(), testcase);
    }

    /**
     * 当@BeforeClass或@BeforeMethod抛出异常时，所有@Test都不会执行，且所有Test都会先调用onTestStart然后直接调用onTestSkipped，且onTestSkipped tr.getThrowable为null
     * Test抛出的SkipException，tr.getThrowable不为空，能获取到跳过的原因
     *
     * @param tr
     */
    @Override
    public void onTestSkipped(ITestResult tr) {
        Integer testcaseId = TL_TEST_CASE_ID.get();
        log.warn("[Web端自动化测试][{}]onTestSkipped, testcaseId: {}", testcaseId, tr.getThrowable());
        Object currentClass = tr.getInstance();
        WebDriver driver = ((WebDriverService) currentClass).getDriver();
        Testcase testcase = new Testcase();
        testcase.setId(testcaseId);
        testcase.setEndTime(new Date());
        testcase.setStatus(Testcase.SKIP_STATUS);
        testcase.setFailImgUrl(getScreenshotDownloadUrl(driver));
        if (tr.getThrowable() == null) { // @BeforeClass或@BeforeMethod抛出异常导致的case跳过
            testcase.setFailInfo("前置任务执行失败");
        } else {
            testcase.setFailInfo(tr.getThrowable().getMessage());
        }
        //testcase.setVideoUrl(getVideoDownloadUrl());
        MasterApi.getInstance().updateTestcase(TL_DEVICE_TEST_TASK_ID.get(), testcase);
    }

    private String getScreenshotDownloadUrl(WebDriver driver) {
        File scrFile =  ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        return MasterApi.getInstance().uploadFile(scrFile);
    }

    private String getVideoDownloadUrl() {
        String downloadUrl = "http://192.168.12.122:8887/chrome_2019_10_25_11_09_23.mp4";
        return downloadUrl;
    }

    /**
     * 提供给actions.ftl调用，记录用例步骤的执行开始/结束时间
     */
    public static void recordTestCaseStepTime(Integer actionId, String startOrEnd, Integer stepNumber) {
        Integer testcaseId = TL_TEST_CASE_ID.get();
        // 只记录当前正在执行的测试用例里的步骤
        if (!actionId.equals(testcaseId)) {
            return;
        }

        Step step = new Step();
        if ("start".equals(startOrEnd)) {
            step.setStartTime(new Date());
        } else if ("end".equals(startOrEnd)) {
            step.setEndTime(new Date());
        }
        step.setNumber(stepNumber);
        Testcase testcase = new Testcase();
        testcase.setId(testcaseId);
        testcase.setSteps(Arrays.asList(step));
        MasterApi.getInstance().updateTestcase(TL_DEVICE_TEST_TASK_ID.get(), testcase);
    }
}