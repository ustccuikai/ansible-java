package com.ansbile.dao.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Table(name = "middle_ware_info")
@Data
@Entity
@DynamicUpdate
@DynamicInsert
public class MiddleWareInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "all_hosts")
    private String allHosts;

    @Column(name = "type")
    private String type;

    @Column(name = "create_by")
    private Integer createBy;

    @CreatedDate
    @Column(name = "create_time", insertable = false, updatable = false)
    private Date createTime;

    @LastModifiedDate
    @Column(name = "update_time", insertable = false, updatable = false)
    private Date updateTime;
}