package com.catalisa.squad5.controller;

import com.catalisa.squad5.dtos.IssuesDTO;
import com.catalisa.squad5.exceptions.IssueIdNotFound;
import com.catalisa.squad5.mapper.IssuesMapper;
import com.catalisa.squad5.model.Issues;
import com.catalisa.squad5.service.IssuesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/issues")
public class IssuesController {

    @Autowired
    IssuesService issuesService;

    @Autowired
    IssuesMapper issuesMapper;

    //Registra nova falha de acessibilidade
    @PreAuthorize("permitAll()")
    @PostMapping
    public ResponseEntity<IssuesDTO> registerIssue(@RequestBody @Valid Issues issues) {
        Issues newIssue = issuesService.registerIssue(issues);
        return new ResponseEntity<>(issuesMapper.toDto(issues), HttpStatus.CREATED);
    }

    //Listar todas as falhas
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public List<Issues> findAll() {
        return issuesService.findAll();
    }

    //Buscar falha por id
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "{id}")
    public ResponseEntity<Issues> getByIdId(@PathVariable Long id) {
        try {
            Issues issue = issuesService.getById(id);
            return ResponseEntity.ok(issue);
        } catch (IssueIdNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    //Atualizar Falha
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateIssue(@PathVariable Long id, @RequestBody IssuesDTO issuesDTO) {
        try {
            Issues updatedIssue = issuesService.updateIssue(id, issuesDTO);
            return ResponseEntity.ok("Issue atualizada com sucesso.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar a Falha.");
        }
    }


}
