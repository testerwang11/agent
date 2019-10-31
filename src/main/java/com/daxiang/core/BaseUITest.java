package com.daxiang.core;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.daxiang.core.web.WebDriverService;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;

public class BaseUITest {

    public RemoteWebDriver driver;
    public String vidoName = "chrome_" + DateUtil.beginOfSecond(new DateTime());
    public RemoteWebDriver startDriver() {
        try {
            this.driver = new WebDriverService().startDriver(vidoName);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return driver;
    }

    public RemoteWebDriver getDriver() {
        return driver;
    }

    public String getVideoName() {
        return vidoName;
    }

}
