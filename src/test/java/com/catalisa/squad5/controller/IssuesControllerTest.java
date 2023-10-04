package com.catalisa.squad5.controller;

import com.catalisa.squad5.dtos.IssueDTO;
import com.catalisa.squad5.model.Issues;
import com.catalisa.squad5.model.Users;
import com.catalisa.squad5.repository.IssuesRepository;
import com.catalisa.squad5.service.IssuesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class IssuesControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private IssuesService issuesService;


    @Autowired
    private ObjectMapper objectMapper;


    //registro de falha
    @Test
    public void testRegisterIssue() throws Exception {
        Issues issues = new Issues();
        issues.setId(1L);
        issues.setUrl("http://example.com");
        issues.setNameCompany("Example Company");
        issues.setDescription("Issue description");
        issues.setTask("Task details");
        issues.setDate(LocalDate.now());
        issues.setTime(LocalTime.now());
        Users manager = new Users();
        manager.setId(2L);
        issues.setManager(manager);

        when(issuesService.registerIssue(Mockito.any(Issues.class)))
                .thenReturn(issues);

        mockMvc.perform(MockMvcRequestBuilders.post("/issues")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(issues)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.url").value("http://example.com"))
                .andExpect(jsonPath("$.nameCompany").value("Example Company"))
                .andExpect(jsonPath("$.description").value("Issue description"))
                .andExpect(jsonPath("$.task").value("Task details"));
    }


    //registro de falhas com campo em branco ou nulo

    //busca falha por id existente
    @Test
    public void testGetIssueByIdExists() throws Exception {
        Issues issue = new Issues();
        issue.setId(1L);
        issue.setUrl("http://example.com");
        issue.setNameCompany("Example Company");
        issue.setDescription("Issue description");
        issue.setTask("Task details");
        issue.setDate(LocalDate.now());
        issue.setTime(LocalTime.now());

        when(issuesService.getById(1L)).thenReturn(issue);

        mockMvc.perform(MockMvcRequestBuilders.get("/issues/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.url").value("http://example.com"))
                .andExpect(jsonPath("$.nameCompany").value("Example Company"))
                .andExpect(jsonPath("$.description").value("Issue description"))
                .andExpect(jsonPath("$.task").value("Task details"));
    }

    //busca falha por id inexistente
    @Test
    public void testGetIssueByIdNotExists() throws Exception {
        when(issuesService.getById(2L)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/issues/2"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testFindAll() throws Exception {
        List<Issues> issuesList = Arrays.asList(
                new Issues(1L, "https://example.com", "Company A", "Description A", "A", LocalDate.now(), LocalTime.now(), new Users()),
                new Issues(2L, "https://example.com", "Company B", "Description B", "B", LocalDate.now(), LocalTime.now(), new Users())
        );

        when(issuesService.findAll()).thenReturn(issuesList);

        mockMvc.perform(get("/issues"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nameCompany").value("Company A"))
                .andExpect(jsonPath("$[1].nameCompany").value("Company B"));

        // Verifique se o serviço foi chamado corretamente
        verify(issuesService, times(1)).findAll();
    }

    @Test
    public void testUpdateIssue() throws Exception {
        Long id = 1L;
        IssueDTO issueDTO = new IssueDTO();
        issueDTO.setUrl("https://example.com");
        issueDTO.setNameCompany("Example Company");
        issueDTO.setDescription("Description");
        issueDTO.setDate(LocalDate.now());
        issueDTO.setTime(LocalTime.now());

        Issues updatedIssue = new Issues();
        updatedIssue.setId(id);
        updatedIssue.setUrl(issueDTO.getUrl());
        updatedIssue.setNameCompany(issueDTO.getNameCompany());
        updatedIssue.setDescription(issueDTO.getDescription());
        updatedIssue.setDate(issueDTO.getDate());
        updatedIssue.setTime(issueDTO.getTime());

        when(issuesService.updateIssue(id, issueDTO)).thenReturn(updatedIssue);

        mockMvc.perform(put("/issues/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(issueDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Falha atualizada com sucesso."));
    }
}