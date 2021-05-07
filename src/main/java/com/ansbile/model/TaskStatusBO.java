package com.ansbile.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TaskStatusBO {

    @Builder.Default
    private Integer finalized = 1;
    private Integer stopType;
    @Builder.Default
    private Integer exitValue = 1; // 错误
    @Builder.Default
    private String taskStatus = TaskStatus.FINALIZED.getStatus();

    private String taskResult;

}
