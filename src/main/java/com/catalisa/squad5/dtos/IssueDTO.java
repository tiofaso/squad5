package com.catalisa.squad5.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueDTO {

    private String url;
    private String nameCompany;
    private String description;
    private LocalDate date;
    private LocalTime time;
}
