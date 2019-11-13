package com.daxiang.model.devicetesttask;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.daxiang.model.action.Action;
import com.daxiang.model.action.GlobalVar;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by jiangyitao.
 */
@Data
public class DeviceTestTask {

    /**
     * 出错，无法运行
     */
    public static final int ERROR_STATUS = -1;
    /**
     * 未开始
     */
    public static final Integer UNSTART_STATUS = 0;
    /**
     * 运行中
     */
    public static final Integer RUNNING_STATUS = 1;
    /**
     * 完成
     */
    public static final Integer FINISHED_STATUS = 2;

    private Integer id;
    private Integer testTaskId;
    private String deviceId;
    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
    private List<GlobalVar> globalVars;
    private Action beforeClass;
    private Action beforeMethod;
    private Action afterClass;
    private Action afterMethod;
    private List<Testcase> testcases;
    private String code;
    private String errMsg;
}