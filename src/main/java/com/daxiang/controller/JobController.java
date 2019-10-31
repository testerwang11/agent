package com.daxiang.controller;

import com.daxiang.model.Response;
import com.daxiang.model.request.ActionDebugRequest;
import com.daxiang.model.request.JobRequest;
import com.daxiang.service.ActionService;
import com.daxiang.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by jiangyitao.
 */
@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    private TaskService taskService;

    /**
     * 添加任务
     */
    @PostMapping("/add")
    public Response add(@Valid @RequestBody JobRequest request) {
        return taskService.add(request.getCron(), request.getType());
    }

    /**
     * 删除任务
     * @param request
     * @return
     */
    public Response del(@Valid @RequestBody JobRequest request) {
        return taskService.del(request.getId());
    }
}