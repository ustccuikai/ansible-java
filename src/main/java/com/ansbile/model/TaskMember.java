package com.ansbile.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Table(name = "task_member")
@Data
@Builder
public class TaskMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "task_id")
    private Integer taskId;

    @Column(name = "inventory_json")
    private String inventoryJson;

//    @Column(name = "hosts")
//    private String hosts;

//    @Column(name = "member_type")
//    private String memberType;

    @Column(name = "playbook_name")
    private String playbookName;

    @Column(name = "playbook_tags")
    private String playbookTags;

    @Column(name = "executor_param")
    private String executorParam;

    /**
     * 是否完成
     */
    private Integer finalized;

    /**
     * 终止任务
     */
    @Column(name = "stop_type")
    private Integer stopType;

    /**
     * 退出值
     */
    @Column(name = "exit_value")
    private Integer exitValue;

    /**
     * 任务状态
     */
    @Column(name = "task_status")
    private String taskStatus;

    @Column(name = "task_result")
    private String taskResult;

    /**
     * 子任务的正常日志
     */
    @Column(name = "output_msg")
    private String outputMsg;

    /**
     * 子任务的错误日志
     */
    @Column(name = "error_msg")
    private String errorMsg;

    @Column(name = "create_time", insertable = false, updatable = false)
    private Date createTime;

    @Column(name = "update_time", insertable = false, updatable = false)
    private Date updateTime;
}