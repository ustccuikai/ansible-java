package com.ansbile.dao.reposity;

import com.ansbile.dao.entity.MysqlGroup;
import com.ansbile.dao.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by cuikai on 2021/5/20.
 */
public interface MysqlGroupRepository extends JpaRepository<MysqlGroup, Long>, JpaSpecificationExecutor<MysqlGroup> {
}
