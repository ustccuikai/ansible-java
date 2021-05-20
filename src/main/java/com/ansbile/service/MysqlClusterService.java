package com.ansbile.service;

import com.ansbile.dao.entity.MysqlCluster;

import java.util.List;

public interface MysqlClusterService {

    void add(MysqlCluster mysqlCluster);

    List<MysqlCluster> queryList();
}
