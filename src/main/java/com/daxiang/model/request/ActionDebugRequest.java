package com.daxiang.model.request;

import com.daxiang.model.action.Action;
import com.daxiang.model.action.GlobalVar;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by jiangyitao.
 */
@Data
public class ActionDebugRequest {

    @NotNull(message = "action不能为空")
    private Action action;
    private List<GlobalVar> globalVars;
    @NotBlank(message = "deviceId不能为空")
    private String deviceId;

    private Integer platform;

}
