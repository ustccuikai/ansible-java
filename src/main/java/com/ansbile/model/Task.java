package com.ansbile.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Table(name = "task")
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 对应的任务类型，比如
     * MySQL主从（半同步）+ Orchestor（三节点）
     * MySQL主从（半同步）+ Orchestor（三节点）+HAproxy（多节点）+Consul（三节点）
     * MGR + Consul（三节点）
     */
    @Column(name = "task_type")
    private String taskType;

    @Column(name = "task_param")
    private String taskParam;

    /**
     * 子任务的个数，对应安装能拆分的步骤的数量
     */
    @Column(name = "task_size")
    private Integer taskSize;

    /**
     * 是否完成
     */
    private Integer finalized;

    /**
     * 终止任务类型
     */
    @Column(name = "stop_type")
    private Integer stopType;

    /**
     * 任务状态
     */
    @Column(name = "task_status")
    private String taskStatus;

    @Column(name = "create_by")
    private Integer createBy;

    @Column(name = "create_time", insertable = false, updatable = false)
    private Date createTime;

    @Column(name = "update_time", insertable = false, updatable = false)
    private Date updateTime;
}