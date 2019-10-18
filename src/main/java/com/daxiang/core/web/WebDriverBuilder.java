package com.daxiang.core.web;

import com.daxiang.App;
import com.daxiang.api.MasterApi;
import com.daxiang.core.PortProvider;
import com.daxiang.model.Device;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

@Slf4j
public class WebDriverBuilder {

    private String basedir = System.getProperty("user.dir") + "/vendor/driver/win/";
    private String chromeDriver = basedir + "chromedriver.exe";

    public void init() throws IOException {

        log.info("启动chrome浏览器");
        System.setProperty("webdriver.chrome.driver", chromeDriver);
        int port = PortProvider.getWebServerAvailablePort();
        ChromeDriverService service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(chromeDriver))
                .usingPort(PortProvider.getWebServerAvailablePort())
                .build();
        service.start();

        RemoteWebDriver driver = new RemoteWebDriver(service.getUrl(), DesiredCapabilities.chrome());

        driver.manage().window().maximize();

        //HttpCommandExecutor executor = (HttpCommandExecutor) driver.getCommandExecutor();
        //URL url = executor.getAddressOfRemoteServer();
        SessionId session_id = driver.getSessionId();
        driver.get("http://www.sijibao.com");
        Device device = new Device();
        device.setStatus(Device.IDLE_STATUS);
        device.setCpuInfo("");
        device.setMemSize("");
        device.setId(session_id.toString());
        device.setName("Chrome");
        device.setPlatform(3);
        device.setAgentIp(App.getProperty("server.address"));
        device.setAgentPort(port);
        device.setSystemVersion(driver.getCapabilities().getVersion());
        device.setLastOnlineTime(new Date());
        device.setCreateTime(new Date());

        String imgUrl= MasterApi.getInstance().uploadFile(driver.getScreenshotAs(OutputType.FILE));
        device.setImgUrl(imgUrl);
        MasterApi.getInstance().saveDevice(device);
    }

    public static void snapshot(TakesScreenshot drivername, String filename) {
        File scrFile = drivername.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, new File("E:\\"+filename));
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            System.out.println("screen shot finished");
        }
    }
}
