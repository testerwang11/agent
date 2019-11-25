package com.daxiang.core.testng;

import com.daxiang.core.testng.listener.DebugActionTestListener;
import com.daxiang.core.testng.listener.TestCaseTestListener;
import com.daxiang.core.testng.listener.TestCaseTestListenerWeb;
import com.daxiang.model.Response;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.util.CollectionUtils;
import org.testng.TestNG;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by jiangyitao.
 */
@Component
public class TestNGRunner {

    public static String getConfig(String key) {
        Properties properties = new Properties();
        InputStream in = TestNGRunner.class.getClassLoader().getResourceAsStream("application-dev.properties");
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(key);
    }

    /**
     * 运行测试用例
     */
    public static void runTestCases(Class[] classes) {
        if(Boolean.parseBoolean(getConfig("web"))){
            run(classes, Arrays.asList(TestCaseTestListenerWeb.class));
        }else {
            run(classes, Arrays.asList(TestCaseTestListener.class));
        }
    }

    /**
     * 调试action
     */
    public static Response debugAction(Class clazz) {
        TestNG testNG = run(new Class[]{clazz}, Arrays.asList(DebugActionTestListener.class));
        if (testNG.getStatus() != 0) {
            // 运行有错误
            String failMsg = DebugActionTestListener.failMsg.get();
            DebugActionTestListener.failMsg.remove();
            return Response.fail(failMsg);
        } else {
            // 运行成功
            List<String> printMsgList = DebugActionTestListener.printMsgList.get();
            DebugActionTestListener.printMsgList.remove();
            if (CollectionUtils.isEmpty(printMsgList)) {
                printMsgList = Arrays.asList("执行成功");
            }
            return Response.success(printMsgList);
        }
    }

    private static TestNG run(Class[] testClasses, List<Class> listenerClasses) {
        TestNG testNG = new TestNG();
        testNG.setTestClasses(testClasses);
        testNG.setListenerClasses(listenerClasses);
        testNG.setUseDefaultListeners(false); // 不用默认监听器,不会自动生成testng的默认报告
        //testNG.setOutputDirectory("D:\\test");//设置输出目录
        testNG.run();
        return testNG;
    }
}
