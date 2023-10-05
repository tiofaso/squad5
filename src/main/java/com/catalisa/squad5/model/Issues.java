package com.catalisa.squad5.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "tb_issues")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Issues {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url", length = 255)
    @NotBlank(message = "A URL não pode estar em branco")
    private String url;

    @Column(name = "name_company", length = 255)
    @NotBlank(message = "O nome da empresa não pode estar em branco")
    private String nameCompany;

    @Column(name = "description", columnDefinition = "text")
    @NotBlank(message = "A descrição da falha de acessibilidade não pode estar em branco")
    private String description;

    @Column(name = "task", length = 1)
    @NotBlank(message = "A tarefa não pode estar em branco")
    private String task;

    @Column(name = "date")
    @NotNull(message = "A data não pode ser nula")
    private LocalDate date;

    @Column(name = "time")
    @NotNull(message = "O horário não pode ser nulo")
    private LocalTime time;

    @ManyToOne
    @JoinColumn(name = "manager")
    @NotNull(message = "O responsável não pode ser nulo")
    private Users manager;
}


