package com.ansbile.dao.reposity;

import com.ansbile.dao.entity.TaskMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by cuikai on 2021/5/20.
 */
public interface TaskMemberRepository extends JpaRepository<TaskMember, Long>, JpaSpecificationExecutor<TaskMember> {
}
