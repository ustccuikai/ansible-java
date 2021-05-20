package com.ansbile.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Table(name = "task_member")
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@DynamicInsert
public class TaskMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_id")
    private Long taskId;

    @Column(name = "task_member_name")
    private String taskMemberName;

    @Column(name = "inventory_json")
    private String inventoryJson;

    @Column(name = "become_password")
    private String becomePassword;

    @Column(name = "playbook_name")
    private String playbookName;

    @Column(name = "playbook_tags")
    private String playbookTags;

    @Column(name = "executor_param", columnDefinition = "blob COMMENT '任务执行参数'")
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
    @Column(name = "output_msg", columnDefinition = "blob COMMENT '任务日志'")
    private String outputMsg;

    /**
     * 子任务的错误日志
     */
    @Column(name = "error_msg", columnDefinition = "blob COMMENT '任务错误日志'")
    private String errorMsg;

    @Column(name = "create_time", insertable = false, updatable = false)
    private Date createTime;

    @Column(name = "update_time", insertable = false, updatable = false)
    private Date updateTime;
}