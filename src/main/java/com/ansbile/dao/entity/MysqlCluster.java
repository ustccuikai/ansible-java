package com.ansbile.dao.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Table(name = "mysql_cluster")
@Data
@Entity
@DynamicUpdate
@DynamicInsert
public class MysqlCluster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //mysql集群id，安装时自动生成
    @Column(name = "cluster_id")
    private String clusterId;

    @Column(name = "primary_host")
    private String primaryHost;

    @Column(name = "all_hosts")
    private String allHosts;

    @Column(name = "status")
    private String status;

    @Column(name = "create_by")
    private Integer createBy;

    @CreatedDate
    @Column(name = "create_time", insertable = false, updatable = false)
    private Date createTime;

    @LastModifiedDate
    @Column(name = "update_time", insertable = false, updatable = false)
    private Date updateTime;
}