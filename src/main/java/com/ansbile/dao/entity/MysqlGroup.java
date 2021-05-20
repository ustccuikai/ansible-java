package com.ansbile.dao.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Table(name = "mysql_group")
@Data
@Entity
@DynamicUpdate
@DynamicInsert
public class MysqlGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //mysql集群id，安装时自动生成
    @Column(name = "mysql_group_id")
    private String mysqlGroupId;

    @Column(name = "primary_host")
    private String primaryHost;

    @Column(name = "all_hosts")
    private String allHosts;

    @Column(name = "status")
    private String status;

    @Column(name = "create_by")
    private Integer createBy;

    @Column(name = "create_time", insertable = false, updatable = false)
    private Date createTime;

    @Column(name = "update_time", insertable = false, updatable = false)
    private Date updateTime;
}