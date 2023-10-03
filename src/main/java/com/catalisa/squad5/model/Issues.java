package com.catalisa.squad5.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    private String url;

    @Column(name = "name_company", length = 255)
    private String nameCompany;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "task", length = 1)
    private String task;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "time")
    private LocalTime time;
    @ManyToOne
    @JoinColumn(name = "manager")
    private Users manager;
}


