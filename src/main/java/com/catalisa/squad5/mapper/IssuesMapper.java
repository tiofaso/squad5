package com.catalisa.squad5.mapper;

import com.catalisa.squad5.dtos.IssuesDTO;
import com.catalisa.squad5.model.Issues;
import org.springframework.stereotype.Component;

@Component
public class IssuesMapper {
    public IssuesDTO toDto(Issues issues) {
        IssuesDTO dto = new IssuesDTO();

        dto.setUrlDto(issues.getUrl());
        dto.setNameCompanyDto(issues.getNameCompany());
        dto.setDescriptionDto(issues.getDescription());
        dto.setTimeDto(issues.getTime());
        dto.setDateDto(issues.getDate());

        return dto;
    }
}
