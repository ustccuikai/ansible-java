package com.ansbile.service.impl;

import com.ansbile.dao.entity.MysqlGroup;
import com.ansbile.dao.reposity.MysqlGroupRepository;
import com.ansbile.service.MysqlGroupService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class MysqlGroupServiceImpl implements MysqlGroupService {

    @Resource
    private MysqlGroupRepository repository;

    @Override
    public void add(MysqlGroup mysqlGroup) {
        repository.save(mysqlGroup);
    }

    @Override
    public List<MysqlGroup> queryList() {
        return null;
    }
}
