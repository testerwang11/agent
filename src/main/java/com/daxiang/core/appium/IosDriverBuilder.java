package com.daxiang.core.appium;

import com.daxiang.App;
import com.daxiang.core.MobileDevice;
import com.daxiang.core.PortProvider;
import com.daxiang.core.ios.IosUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.util.StringUtils;

/**
 * Created by jiangyitao.
 */
public class IosDriverBuilder implements AppiumDriverBuilder {

    private static final String BUNDLE_ID = "com.apple.mobilesafari";

    @Override
    public AppiumDriver build(MobileDevice mobileDevice, boolean isFirstBuild) {
        // https://github.com/appium/appium-xcuitest-driver
        DesiredCapabilities capabilities = createDesiredCapabilities(mobileDevice);
        capabilities.setCapability(MobileCapabilityType.NO_RESET, true); // http://appium.io/docs/en/writing-running-appium/other/reset-strategies/index.html
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60000);

        capabilities.setCapability(IOSMobileCapabilityType.WDA_LOCAL_PORT, PortProvider.getWdaLocalAvailablePort());
        capabilities.setCapability(IOSMobileCapabilityType.WDA_STARTUP_RETRY_INTERVAL, 60000);
        capabilities.setCapability("mjpegServerPort", PortProvider.getWdaMjpegServerAvailablePort());
        capabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, BUNDLE_ID);
        capabilities.setCapability(IOSMobileCapabilityType.LAUNCH_TIMEOUT, 60000);
        capabilities.setCapability(IOSMobileCapabilityType.WDA_LAUNCH_TIMEOUT, 60000);
        capabilities.setCapability(IOSMobileCapabilityType.WDA_CONNECTION_TIMEOUT, 60000);
        capabilities.setCapability(IOSMobileCapabilityType.COMMAND_TIMEOUTS, 60000);

        capabilities.setCapability("waitForQuiescence", false);
        capabilities.setCapability("skipLogCapture", true);
        // Get JSON source from WDA and parse into XML on Appium server. This can be much faster, especially on large devices.
        capabilities.setCapability("useJSONSource", true);
        // http://appium.io/docs/en/advanced-concepts/settings/
        capabilities.setCapability("mjpegServerFramerate", Integer.parseInt(App.getProperty("mjpegServerFramerate")));
        capabilities.setCapability("webkitDebugProxyPort", PortProvider.getWebkitDebugProxyAvalilablePort());

        // https://github.com/appium/appium-xcuitest-driver/blob/master/docs/real-device-config.md
        String xcodeOrgId = App.getProperty("xcodeOrgId");
        if (!StringUtils.isEmpty(xcodeOrgId)) {
            capabilities.setCapability("xcodeOrgId", xcodeOrgId);
        }
        String xcodeSigningId = App.getProperty("xcodeSigningId");
        if (!StringUtils.isEmpty(xcodeSigningId)) {
            capabilities.setCapability("xcodeSigningId", xcodeSigningId);
        }
        String updatedWDABundleId = App.getProperty("updatedWDABundleId");
        if (!StringUtils.isEmpty(updatedWDABundleId)) {
            capabilities.setCapability("updatedWDABundleId", updatedWDABundleId);
        }

        IOSDriver iosDriver = new IOSDriver(mobileDevice.getAppiumServer().getUrl(), capabilities);
        IosUtil.pressHome(iosDriver);
        return iosDriver;
    }
}
