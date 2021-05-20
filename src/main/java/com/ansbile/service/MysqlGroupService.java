package com.ansbile.service;

import com.ansbile.dao.entity.MysqlGroup;

import java.util.List;

public interface MysqlGroupService {

    void add(MysqlGroup mysqlGroup);

    List<MysqlGroup> queryList();
}
