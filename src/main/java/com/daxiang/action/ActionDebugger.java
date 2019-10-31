package com.daxiang.action;

import com.daxiang.core.MobileDeviceHolder;
import com.daxiang.utils.UUIDUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Set;

/**
 * Created by jiangyitao.
 */
public class ActionDebugger {

    private AppiumDriver appiumDriver;

    @BeforeClass
    public void beforeClass() {
        appiumDriver = MobileDeviceHolder.get("DEVICE_ID").getAppiumDriver();
    }

    @Test
    public void test() {
        // 以下为要测试的代码
        /*String context = appiumDriver.getContext();
        appiumDriver.switchTo("WEBVIEW_com.tencent.mm:tools")*/
        Set<String> contexts = appiumDriver.getContextHandles();
        System.out.println("contexts: {}" + contexts);
        //appiumDriver.findElement(By.id("com.yicai.sijibao.dd1:id/close_stock_tv")).click();
        System.out.println("===============================");
        //System.out.println(appiumDriver.getPageSource());
        new TouchAction(appiumDriver).tap(PointOption.point(118, 513)).perform().release();


        /*String str = "武汉市";
        String path = "new UiScrollable(new UiSelector().scrollable(true)).getChildByDescription(new UiSelector().className(\"android.widget.TextView\"), \"上海\", false)"; //获取滚动元素对象
        appiumDriver.findElement(MobileBy.AndroidUIAutomator(path)).click();*/
    }

    /**
     * 开发者调试代码
     * 1. 在平台上使用一台设备
     * 2. 将设备id，赋值给final String deviceId
     * 3. 运行main方法即可
     * <p>
     * 提示: final String filePath / final String url 根据实际情况修改
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        final String deviceId = "CUTOWSGM99999999";
        final String filePath = "D:/work/workspace2/agent2/src/main/java/com/daxiang/action/ActionDebugger.java";
        final String url = "http://192.168.12.122:10004/action/developer/debug";

        // 1. 读取ActionDebugger.java
        String code = FileUtils.readFileToString(
                new File(filePath),
                "UTF-8");
        String className = "ActionDebugger_" + UUIDUtil.getUUID();
        code = code.replaceAll("ActionDebugger", className).replaceAll("DEVICE_ID", deviceId);

        // 2.发送到ActionController @PostMapping("/developer/debug")执行
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("className", "com.daxiang.action." + className);
        params.add("code", code);
        String response = restTemplate.postForObject(url, params, String.class);
        System.out.println("response => " + response);
    }
}
