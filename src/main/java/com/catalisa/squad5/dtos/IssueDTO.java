package com.catalisa.squad5.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueDTO {
    @NotBlank(message = "A URL não pode estar em branco")
    private String url;

    @NotBlank(message = "O nome da empresa não pode estar em branco")
    private String nameCompany;

    @NotBlank(message = "A descrição da falha de acessibilidade não pode estar em branco")
    private String description;

    @NotNull(message = "A data não pode ser nula")
    private LocalDate date;

    @NotNull(message = "O horário não pode ser nulo")
    private LocalTime time;
}
