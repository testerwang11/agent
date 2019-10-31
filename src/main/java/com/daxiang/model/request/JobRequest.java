package com.daxiang.model.request;

import cn.hutool.cron.task.Task;
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
public class JobRequest {

    private String id;
    @NotNull(message = "cron不能为空")
    private String cron;
    @NotNull(message = "任务类型不能为空")
    private int type;


}
