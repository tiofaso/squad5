package com.catalisa.squad5.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationDTO {

    private String usernameDto;

    private String passwordDto;
}
