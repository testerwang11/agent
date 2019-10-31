package com.daxiang.core.web;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class WebDriverService {

    private String basedir = System.getProperty("user.dir") + "/vendor/driver/win/";
    private String chromeDriver = basedir + "chromedriver.exe";

    private RemoteWebDriver driver;

    @Value("${server.address}")
    private String serverIp;
    @Value("${server.port}")
    private String serverPort;


    private String nodeURL = "http://192.168.0.196:4444/wd/hub";


    public RemoteWebDriver startDriver(String vidoName) throws MalformedURLException {
        System.setProperty("webdriver.chrome.driver", chromeDriver);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--lang=" + "zh-CN");//指定浏览器语言
        options.addArguments("--test-type", "--ignore-certificate-errors", "no-default-browser-check");//忽略错误证书,不检查是否默认浏览器
        //options.addArguments("user-data-dir=C:/Users/002715/AppData/Local/Google/Chrome/UserData");
        //options.setHeadless(true);
        Map<String, Object> prefs = new HashMap<>();
        //prefs.put("profile.managed_default_content_settings.images",2); //禁止下载加载图片
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("disable-gpu");
        DesiredCapabilities dc = DesiredCapabilities.chrome();

        dc.setCapability(ChromeOptions.CAPABILITY, options);
        //dc.setCapability("screenResolution", "1280x720");

        //dc.setCapability("build", temp);
        //dc.setCapability("name", temp);
        dc.setCapability("testFileNameTemplate", vidoName);
        driver = new RemoteWebDriver(new URL(nodeURL), dc);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }

    public RemoteWebDriver getDriver() {
        return driver;
    }
}
