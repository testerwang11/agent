package com.daxiang.core.web;

import cn.hutool.system.SystemUtil;
import com.alibaba.fastjson.JSONObject;
import com.daxiang.App;
import com.daxiang.api.MasterApi;
import com.daxiang.core.BrowserDeviceHolder;
import com.daxiang.model.Device;
import com.daxiang.utils.NetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.remote.http.W3CHttpCommandCodec;
import org.openqa.selenium.remote.http.W3CHttpResponseCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Collections;
import java.util.Date;

@Slf4j
@Component
public class WebDriverBuilder {

    private String basedir = System.getProperty("user.dir") + "/vendor/driver/";
    private String chromeDriver = basedir + getSystemType();
    @Autowired
    private MasterApi masterApi;
    @Value("${server.address}")
    private String serverIp;
    @Value("${server.port}")
    private Integer serverPort;

    private static String getSystemType() {
        String name = SystemUtil.getOsInfo().getName().toLowerCase();
        if (name.startsWith("win")) {
            return "win/chromedriver.exe";
        } else {
            return "mac/chromedriver";
        }
    }

    public static RemoteWebDriver createDriverFromSession(final SessionId sessionId, URL command_executor) {
        CommandExecutor executor = new HttpCommandExecutor(command_executor) {
            @Override
            public Response execute(Command command) throws IOException {
                Response response = null;
                if (command.getName() == "newSession") {
                    response = new Response();
                    response.setSessionId(sessionId.toString());
                    response.setStatus(0);
                    response.setValue(Collections.<String, String>emptyMap());
                    try {
                        Field commandCodec = null;
                        commandCodec = this.getClass().getSuperclass().getDeclaredField("commandCodec");
                        commandCodec.setAccessible(true);
                        commandCodec.set(this, new W3CHttpCommandCodec());

                        Field responseCodec = null;
                        responseCodec = this.getClass().getSuperclass().getDeclaredField("responseCodec");
                        responseCodec.setAccessible(true);
                        responseCodec.set(this, new W3CHttpResponseCodec());
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                } else {
                    response = super.execute(command);
                }
                return response;
            }
        };
        return new RemoteWebDriver(executor, new DesiredCapabilities());
    }

    private Boolean checkPortAvailable(int port) {
        if (NetUtil.isPortAvailable(port)) {
            return true;
        }
        return false;
    }

    public void init() throws IOException {
        //int port = PortProvider.getWebServerAvailablePort();
        int port = 29001;
        log.info("开始删除残留浏览器");
        JSONObject data = new JSONObject();
        data.put("agentIp", serverIp);
        data.put("agentPort", Integer.parseInt(App.getProperty("server.port")));
        data.put("name", "Chrome");
        data.put("platform", 3);
        masterApi.getInstance().delDevice(data);

        log.info("启动chrome浏览器");
        System.setProperty("webdriver.chrome.driver", chromeDriver);

        ChromeDriverService service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(chromeDriver))
                .usingPort(port)
                .build();
        service.start();

        DesiredCapabilities capabilities =  DesiredCapabilities.chrome();
        capabilities.setAcceptInsecureCerts(false);
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        options.setAcceptInsecureCerts(false);
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);

        RemoteWebDriver driver = new RemoteWebDriver(service.getUrl(), capabilities);
        driver.manage().window().maximize();

        HttpCommandExecutor executor = (HttpCommandExecutor) driver.getCommandExecutor();
        URL url = executor.getAddressOfRemoteServer();
        SessionId session_id = driver.getSessionId();
        driver.get("http://www.sijibao.com");
        String deviceId = session_id.toString();
        Device device = new Device();
        device.setStatus(Device.IDLE_STATUS);
        device.setCpuInfo("");
        device.setMemSize("");
        device.setId(deviceId);
        device.setName("Chrome");
        device.setPlatform(3);
        device.setAgentIp(App.getProperty("server.address"));
        device.setAgentPort(Integer.parseInt(App.getProperty("server.port")));
        device.setSystemVersion(driver.getCapabilities().getVersion());
        device.setLastOnlineTime(new Date());
        device.setCreateTime(new Date());
        device.setStatus(2);
        String imgUrl = MasterApi.getInstance().uploadFile(driver.getScreenshotAs(OutputType.FILE));
        device.setImgUrl(imgUrl);

        masterApi.getInstance().saveDevice(device);
        log.info("sessionId:" + session_id);
        BrowserDeviceHolder.add(deviceId, device);

    }

    public static void snapshot(TakesScreenshot drivername, String filename) {
        File scrFile = drivername.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, new File("E:\\" + filename));
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            System.out.println("screen shot finished");
        }
    }

    public static void startDriver() {

    }
}
