package com.daxiang.core.web;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.remote.http.W3CHttpCommandCodec;
import org.openqa.selenium.remote.http.W3CHttpResponseCodec;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;

public class Demo2 {

    public static RemoteWebDriver createDriverFromSession(final SessionId sessionId, URL command_executor){
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


    public static void main(String[] args) throws IOException {
        /*String basedir = System.getProperty("user.dir") + "/vendor/driver/";
        String chromeDriver = basedir + "chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", chromeDriver);
        ChromeDriverService service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(chromeDriver))
                .usingPort(1212)
                .build();
        service.start();
        RemoteWebDriver driver = new RemoteWebDriver(service.getUrl(),
                DesiredCapabilities.chrome());
        HttpCommandExecutor executor = (HttpCommandExecutor) driver.getCommandExecutor();
        URL url = executor.getAddressOfRemoteServer();
        SessionId session_id = driver.getSessionId();*/
        URL url = new URL("http://localhost:9515");
        /*RemoteWebDriver driver = new RemoteWebDriver(url,
                DesiredCapabilities.chrome());*/
        //SessionId session_id = driver.getSessionId();
        SessionId session_id = new SessionId("1675179a0cda9e2db257b602468dcaf5");
        //URL url = new URL("http://192.168.12.122:8817");

        System.out.println(session_id);
        System.out.println(url);

        RemoteWebDriver driver2 = createDriverFromSession(session_id, url);
        driver2.get("http://www.qq.com");
        //driver2.quit();

    }

}
