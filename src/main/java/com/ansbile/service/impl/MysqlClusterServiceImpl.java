package com.ansbile.service.impl;

import com.ansbile.dao.entity.MysqlCluster;
import com.ansbile.dao.reposity.MysqlClusterRepository;
import com.ansbile.service.MysqlClusterService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class MysqlClusterServiceImpl implements MysqlClusterService {

    @Resource
    private MysqlClusterRepository repository;

    @Override
    public void add(MysqlCluster mysqlCluster) {
        repository.save(mysqlCluster);
    }

    @Override
    public List<MysqlCluster> queryList() {
        return null;
    }
}
