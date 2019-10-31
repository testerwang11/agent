package com.daxiang.core.web;

import com.daxiang.api.MasterApi;
import com.daxiang.core.BrowserDeviceHolder;
import com.daxiang.model.Device;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.remote.http.W3CHttpCommandCodec;
import org.openqa.selenium.remote.http.W3CHttpResponseCodec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;


/**
 * Created by jiangyitao.
 */
@Slf4j
@Component
public class WebDevice  {

    private static WebDevice webDevice;

    @Value("${server.address}")
    private String serverIp;

    public static WebDevice getInstance() {
        if (webDevice == null) {
            synchronized (WebDevice.class) {
                if (webDevice == null) {
                    log.info("初始化");
                    webDevice = new WebDevice();
                }
            }
        }
        return webDevice;
    }

    public final static String TMP_FOLDER = "/data/local/tmp/";


    public RemoteWebDriver getBrowser() {
        RemoteWebDriver driver = null;
        try {
            //Device device = MasterApi.getInstance().getDeviceByIpAndPort(serverIp, "29001", "3");
            Device device = BrowserDeviceHolder.getAll().get(0);
            log.info("sessionId222:" + device.getId());
            log.info("url2222:" + "http://127.0.0.1:29001");
            driver = createDriverFromSession(new SessionId(device.getId()), new URL("http://127.0.0.1:29001"));
        } catch (MalformedURLException e) {
           log.info(e.getMessage());
        }
        return driver;
    }


    public RemoteWebDriver createDriverFromSession(final SessionId sessionId, URL command_executor) {
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


}
