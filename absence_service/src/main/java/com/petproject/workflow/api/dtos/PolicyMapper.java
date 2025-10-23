package com.petproject.workflow.api.dtos;

import com.petproject.workflow.store.Policy;
import org.springframework.stereotype.Component;

@Component
public class PolicyMapper {

    public Policy mapToPolicy(PolicyDto policyDto) {
        Policy policy = new Policy(
                policyDto.getId(),
                policyDto.getType(),
                policyDto.getMaxDurationDays(),
                policyDto.getRequiresApproval(),
                null // canApprovePositionIds будет установлено отдельно
        );
        policy.setCanApprovePositionIds(policyDto.getCanApprovePositionIds());
        return policy;
    }

    public PolicyDto mapToPolicyDto(Policy policy) {
        return new PolicyDto(
                policy.getId(),
                policy.getType(),
                policy.getMaxDurationDays(),
                policy.getRequiresApproval(),
                policy.getCanApprovePositionIds()
        );
    }
}