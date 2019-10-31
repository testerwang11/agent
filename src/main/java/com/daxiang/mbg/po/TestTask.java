package com.daxiang.mbg.po;

import java.io.Serializable;
import java.util.Date;

public class TestTask implements Serializable {
    /**
     * 主键id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     * 所属项目
     *
     * @mbg.generated
     */
    private Integer projectId;

    /**
     * 所属测试计划
     *
     * @mbg.generated
     */
    private Integer testPlanId;

    /**
     * 测试计划名
     *
     * @mbg.generated
     */
    private String testPlanName;

    /**
     * 任务名
     *
     * @mbg.generated
     */
    private String name;

    /**
     * 任务描述
     *
     * @mbg.generated
     */
    private String description;

    /**
     * 运行模式 1:兼容模式 2:高效模式
     *
     * @mbg.generated
     */
    private Integer runMode;

    /**
     * 任务状态 0:未完成 1:已完成
     *
     * @mbg.generated
     */
    private Integer status;

    /**
     * 任务创建人
     *
     * @mbg.generated
     */
    private Integer creatorUid;

    /**
     * 测试通过用例数
     *
     * @mbg.generated
     */
    private Integer passCaseCount;

    /**
     * 测试失败用例数
     *
     * @mbg.generated
     */
    private Integer failCaseCount;

    /**
     * 测试跳过用例数
     *
     * @mbg.generated
     */
    private Integer skipCaseCount;

    /**
     * 任务提交时间
     *
     * @mbg.generated
     */
    private Date commitTime;

    /**
     * 任务完成时间
     *
     * @mbg.generated
     */
    private Date finishTime;

    /**
     * 时间表达式
     *
     * @mbg.generated
     */
    private String timeConfig;

    /**
     * 报告接收人
     *
     * @mbg.generated
     */
    private String userList;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getTestPlanId() {
        return testPlanId;
    }

    public void setTestPlanId(Integer testPlanId) {
        this.testPlanId = testPlanId;
    }

    public String getTestPlanName() {
        return testPlanName;
    }

    public void setTestPlanName(String testPlanName) {
        this.testPlanName = testPlanName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRunMode() {
        return runMode;
    }

    public void setRunMode(Integer runMode) {
        this.runMode = runMode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCreatorUid() {
        return creatorUid;
    }

    public void setCreatorUid(Integer creatorUid) {
        this.creatorUid = creatorUid;
    }

    public Integer getPassCaseCount() {
        return passCaseCount;
    }

    public void setPassCaseCount(Integer passCaseCount) {
        this.passCaseCount = passCaseCount;
    }

    public Integer getFailCaseCount() {
        return failCaseCount;
    }

    public void setFailCaseCount(Integer failCaseCount) {
        this.failCaseCount = failCaseCount;
    }

    public Integer getSkipCaseCount() {
        return skipCaseCount;
    }

    public void setSkipCaseCount(Integer skipCaseCount) {
        this.skipCaseCount = skipCaseCount;
    }

    public Date getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(Date commitTime) {
        this.commitTime = commitTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getTimeConfig() {
        return timeConfig;
    }

    public void setTimeConfig(String timeConfig) {
        this.timeConfig = timeConfig;
    }

    public String getUserList() {
        return userList;
    }

    public void setUserList(String userList) {
        this.userList = userList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", projectId=").append(projectId);
        sb.append(", testPlanId=").append(testPlanId);
        sb.append(", testPlanName=").append(testPlanName);
        sb.append(", name=").append(name);
        sb.append(", description=").append(description);
        sb.append(", runMode=").append(runMode);
        sb.append(", status=").append(status);
        sb.append(", creatorUid=").append(creatorUid);
        sb.append(", passCaseCount=").append(passCaseCount);
        sb.append(", failCaseCount=").append(failCaseCount);
        sb.append(", skipCaseCount=").append(skipCaseCount);
        sb.append(", commitTime=").append(commitTime);
        sb.append(", finishTime=").append(finishTime);
        sb.append(", timeConfig=").append(timeConfig);
        sb.append(", userList=").append(userList);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}