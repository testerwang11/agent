package com.daxiang.config;

import com.daxiang.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(99)
public class JobConfig implements CommandLineRunner {
    @Autowired
    private TaskService taskService;

    @Override
    public void run(String... args) throws Exception {
        //taskService.startJob();
    }
}
