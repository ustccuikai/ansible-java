package com.ansbile.dao.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Table(name = "task")
@Data
@Entity
@DynamicUpdate
@DynamicInsert
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //mysql集群id，安装时自动生成
    @Column(name = "mysql_cluster_id")
    private String mysqlClusterId;

    /**
     * 对应的任务类型，比如
     * SemiSync:MySQL主从（半同步）+ Orchestor（三节点）
     * SemiSyncHA:MySQL主从（半同步）+ Orchestor（三节点）+HAproxy（多节点）+Consul（三节点）
     * MGR: MGR + Consul（三节点）
     */
    @Column(name = "task_type")
    private String taskType;

    @Column(name = "inventory_json", columnDefinition = "blob COMMENT 'JSON形式的inventory信息'")
    private String inventoryJson;

    @Column(name = "task_param", columnDefinition = "blob COMMENT '任务执行参数'")
    private String taskParam;

    /**
     * 复用orchestrator集群的id
     */
    @Column(name = "orch_cluster_id")
    private String orchClusterId;

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

    @CreatedDate
    @Column(name = "create_time", insertable = false, updatable = false)
    private Date createTime;

    @LastModifiedDate
    @Column(name = "update_time", insertable = false, updatable = false)
    private Date updateTime;
}