package com.catalisa.squad5.controller;

import com.catalisa.squad5.dtos.IssueDTO;
import com.catalisa.squad5.model.Issues;
import com.catalisa.squad5.service.IssuesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/issues")
public class IssuesController {

    @Autowired
    IssuesService issuesService;

    //Registra nova falha de acessibilidade
    @PostMapping
    public ResponseEntity<Issues> registerIssue(@RequestBody @Valid Issues issues) {
        try {
            Issues newIssue = issuesService.registerIssue(issues);
            return new ResponseEntity<>(newIssue, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

   /* @PostMapping
    public ResponseEntity<Issues> registerIssue(@RequestBody @Valid IssueDTO issueDTO) {
        Issues issues = new Issues();
        issues.setUrl(issueDTO.getUrl());
        issues.setNameCompany(issueDTO.getNameCompany());
        issues.setDescription(issueDTO.getDescription());
        issues.setDate(issueDTO.getDate());
        issues.setTime(issueDTO.getTime());

        try {
            Issues newIssue = issuesService.registerIssue(issues);
            return new ResponseEntity<>(newIssue, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }*/


        //Listar todas as falhas
        @GetMapping
        public List<Issues> findAll () {
            return issuesService.findAll();
        }

        //Buscar falha por id
        @GetMapping(path = "{id}")
        public ResponseEntity<Issues> getByIdId (@PathVariable Long id){
            Issues issue = issuesService.getById(id);
            return ResponseEntity.ok(issue);
        }

        //Atualizar Falha
        @PutMapping("/{id}")
        public ResponseEntity<String> updateIssue (@PathVariable Long id, @RequestBody IssueDTO issueDTO){
            try {
                Issues updatedIssue = issuesService.updateIssue(id, issueDTO);
                return ResponseEntity.ok("Issue atualizada com sucesso.");
            } catch (EntityNotFoundException e) {
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar a Falha.");
            }
        }
    }
