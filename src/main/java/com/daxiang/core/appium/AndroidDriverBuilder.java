package com.daxiang.core.appium;

import com.daxiang.core.MobileDevice;
import com.daxiang.core.AgentStartRunner;
import com.daxiang.core.MobileDevice;
import com.daxiang.core.PortProvider;
import com.daxiang.core.android.AndroidDevice;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Optional;

/**
 * Created by jiangyitao.
 */
public class AndroidDriverBuilder implements AppiumDriverBuilder {

    @Override
    public AppiumDriver build(MobileDevice mobileDevice, boolean isFirstBuild) {
        // http://appium.io/docs/en/writing-running-appium/caps/
        DesiredCapabilities capabilities = createDesiredCapabilities(mobileDevice);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
        capabilities.setCapability(AndroidMobileCapabilityType.UNICODE_KEYBOARD, false); // 切换到appium输入法
        capabilities.setCapability(AndroidMobileCapabilityType.RESET_KEYBOARD, false);
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 1800);

        if (((AndroidDevice) mobileDevice).canUseUiautomator2()) {
            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2); // UIAutomation2 is only supported since Android 5.0 (Lollipop)
        } else {
            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UIAutomator1");
        }

        capabilities.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT, PortProvider.getUiautomator2ServerAvailablePort());
        capabilities.setCapability("chromedriverPort", PortProvider.getChromeDriverAvailablePort());
        //capabilities.setCapability("showChromedriverLog", true);
        //capabilities.setCapability("extractChromeAndroidPackageFromContextName", true);
        //capabilities.setCapability(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE, "vendor\\driver\\mobile\\chromedriver.exe");

        Optional<String> chromedriverFilePath = ((AndroidDevice) mobileDevice).getChromedriverFilePath();
        if (chromedriverFilePath.isPresent()) {
            capabilities.setCapability(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE, chromedriverFilePath.get());
        }

        //capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, APP_PACKAGE);
        //capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, APP_ACTIVITY);
        capabilities.setCapability(AndroidMobileCapabilityType.NO_SIGN, true);

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("androidProcess", "com.tencent.mm:tools");
        options.setExperimentalOption("androidPackage", "com.tencent.mm");
        options.setExperimentalOption("androidUseRunningApp", true);
        options.setExperimentalOption("androidActivity", ".plugin.webview.ui.tools.WebViewUI");

        options.setCapability("browserName","");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        //capabilities.setCapability("browserName","");
        //capabilities.setCapability("recreateChromeDriverSessions", true);
        //capabilities.setCapability("autoWebview", true);

        if (!isFirstBuild) {
            // 不是第一次初始化appium，skip掉一些操作，可以提升初始化driver的速度
            capabilities.setCapability("skipServerInstallation", true);
            capabilities.setCapability("skipDeviceInitialization", true);
            capabilities.setCapability("skipUnlock", true);
        }

        capabilities.setCapability("skipLogcatCapture", true);

        return new AndroidDriver(mobileDevice.getAppiumServer().getUrl(), capabilities);
    }
}
