package com.petproject.workflow.api.dtos;

import com.petproject.workflow.store.Approval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApprovalMapper {

    private final AbsenceMapper absenceMapper;

    @Autowired
    public ApprovalMapper(AbsenceMapper absenceMapper) {
        this.absenceMapper = absenceMapper;
    }

    public Approval mapToApproval(ApprovalDto approvalDto) {
        return new Approval(
                approvalDto.getId(),
                absenceMapper.mapToAbsence(approvalDto.getAbsence()),
                approvalDto.getApprover().getId(), // Берем ID из EmployeeDto
                approvalDto.getDescription()
        );
    }

    public ApprovalDto mapToApprovalDto(
            Approval approval,
            EmployeeDto employee,
            EmployeeDto createdBy,
            EmployeeDto approver) {
        return new ApprovalDto(
                approval.getId(),
                absenceMapper.mapToAbsenceDto(approval.getAbsence(), employee, createdBy),
                approver,
                approval.getDescription()
        );
    }
}