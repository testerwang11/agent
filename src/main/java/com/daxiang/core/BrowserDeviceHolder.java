package com.daxiang.core;

import com.daxiang.core.web.WebDevice;
import com.daxiang.model.Device;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jiangyitao.
 */
public class BrowserDeviceHolder {

    private static final Map<String, Device> BROWSER_DEVICE_HOLDER = new ConcurrentHashMap<>();

    public static void add(String deviceId, Device device) {
        BROWSER_DEVICE_HOLDER.put(deviceId, device);
    }

    public static Device get(String deviceId) {
        return BROWSER_DEVICE_HOLDER.get(deviceId);
    }

    public static List<Device> getAll() {
        return new ArrayList<>(BROWSER_DEVICE_HOLDER.values());
    }


}