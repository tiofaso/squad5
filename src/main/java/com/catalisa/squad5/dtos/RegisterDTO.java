package com.catalisa.squad5.dtos;

import com.catalisa.squad5.model.Roles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterDTO {

    private String usernameDTO;

    private String passwordDTO;

    private Roles roles;
}
