package com.daxiang.action;

import com.daxiang.action.appium.BasicAction;
import com.daxiang.core.testng.listener.DebugActionTestListener;
import com.daxiang.core.web.WebDevice;
import com.daxiang.utils.UUIDUtil;
import io.appium.java_client.AppiumDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.File;

/**
 * Created by jiangyitao.
 */
public class ActionDebugger2 {

    private RemoteWebDriver driver;
    private BasicAction $;

    @BeforeClass
    public void beforeClass() {
        driver = new WebDevice().getBrowser();
    }

    @Test
    public void test() {
        // 以下为要测试的代码
        driver.get("https://www.baidu.com");
        System.out.println(driver.getPageSource());
    }

/*    @Test
    public void testcase_0() throws Throwable {
        action_0();
    }*/



    private void print(Object o) {
        //DebugActionTestListener.printMsg.set(String.valueOf(o));
    }

    /**
     * 开发者调试代码
     * 1. 在平台上使用一台设备
     * 2. 将设备id，赋值给final String deviceId
     * 3. 运行main方法即可
     *
     * 提示: final String filePath / final String url 根据实际情况修改
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        final String deviceId = "22649fcf63ae397ef8ae91099c517d1112";
        final String filePath = "D:/work/workspace2/agent2/src/main/java/com/daxiang/action/ActionDebugger2.java";
        final String url = "http://192.168.12.122:10005/action/developer/debug";

        // 1. 读取ActionDebugger.java
        String code = FileUtils.readFileToString(new File(filePath), "UTF-8");
        String className = "ActionDebugger2_" + UUIDUtil.getUUID();
        code = code.replaceAll("ActionDebugger2", className).replaceAll("DEVICE_ID", deviceId);

        // 2.发送到ActionController @PostMapping("/developer/debug")执行
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("className", "com.daxiang.action." + className);
        params.add("code", code);
        String response = restTemplate.postForObject(url, params, String.class);
        System.out.println("response => " + response);
    }
}
