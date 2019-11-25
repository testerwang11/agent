package com.daxiang.core;

import com.daxiang.core.web.WebDriverService;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;

public class BaseUITest {

    public RemoteWebDriver driver;

    //public String vidoName = "chrome_" + DateUtil.beginOfSecond(new DateTime());
    public String vidoName = "chrome_" + RandomStringUtils.random(20);
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
