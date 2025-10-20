package com.petproject.workflow.api.dtos;

import com.petproject.workflow.store.AbsencePolicy;
import org.springframework.stereotype.Component;

@Component
public class AbsencePolicyMapper {

    public AbsencePolicy mapToAbsencePolicy(AbsencePolicyDto policyDto) {
        return new AbsencePolicy(
                policyDto.getId(),
                policyDto.getType(),
                policyDto.getMaxDurationDays(),
                policyDto.getRequiresApproval(),
                policyDto.getCreatedAt(),
                policyDto.getUpdatedAt()
        );
    }

    public AbsencePolicyDto mapToAbsencePolicyDto(AbsencePolicy policy) {
        return new AbsencePolicyDto(
                policy.getId(),
                policy.getType(),
                policy.getMaxDurationDays(),
                policy.getRequiresApproval(),
                policy.getCreatedAt(),
                policy.getUpdatedAt()
        );
    }
}