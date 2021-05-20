package com.ansbile.dao.reposity;

import com.ansbile.dao.entity.MiddleWareInfo;
import com.ansbile.dao.entity.MysqlCluster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by cuikai on 2021/5/20.
 */
public interface MiddleWareInfoRepository extends JpaRepository<MiddleWareInfo, Long>, JpaSpecificationExecutor<MiddleWareInfo> {
}
