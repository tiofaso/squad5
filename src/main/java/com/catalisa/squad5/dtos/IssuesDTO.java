package com.catalisa.squad5.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssuesDTO {

    private String urlDto;
    private String nameCompanyDto;
    private String descriptionDto;
    private LocalDate dateDto;
    private LocalTime timeDto;

}
