package com.catalisa.squad5.controller;

import com.catalisa.squad5.model.Issues;
import com.catalisa.squad5.service.IssuesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/issues")
public class IssuesController {

    @Autowired
    IssuesService issuesService;

    //Registra nova falha de acessibilidade
    @PostMapping
    public ResponseEntity<Issues> registerIssue(@RequestBody Issues issues) {
        Issues newIssue = issuesService.registerIssue(issues);
        return new ResponseEntity<>(issues, HttpStatus.CREATED);
    }

    //Listar todas as falhas
    @GetMapping
    public List<Issues> findAll() {
        return issuesService.findAll();
    }

    //Buscar falha por id
    @GetMapping(path = "{id}")
    public ResponseEntity<Issues> getByIdId(@PathVariable Long id) {
        Issues issue = issuesService.getById(id);
        return ResponseEntity.ok(issue);
    }
}
