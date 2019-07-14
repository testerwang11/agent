package com.daxiang.action.android;

import com.daxiang.android.AndroidDeviceHolder;
import com.daxiang.android.AndroidUtil;
import io.appium.java_client.AppiumDriver;
import org.springframework.util.Assert;

/**
 * Created by jiangyitao.
 */
public class ExcuteAdbShellCommond {

    private AppiumDriver driver;

    public ExcuteAdbShellCommond(AppiumDriver driver) {
        this.driver = driver;
    }

    public void excute(Object cmd) throws Exception {
        Assert.notNull(cmd, "命令不能为空");
        String _cmd = (String) cmd;

        AndroidUtil.executeShellCommand(AndroidDeviceHolder.get(driver).getIDevice(), _cmd);
    }
}
