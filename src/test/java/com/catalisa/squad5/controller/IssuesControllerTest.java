package com.catalisa.squad5.controller;

import com.catalisa.squad5.dtos.IssuesDTO;
import com.catalisa.squad5.exceptions.IssueIdNotFound;
import com.catalisa.squad5.model.Issues;
import com.catalisa.squad5.model.Users;
import com.catalisa.squad5.service.IssuesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
    @WithMockUser(username = "admin", password = "12345", roles = "USER")
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
    @WithMockUser(username = "admin", password = "12345", roles = "USER")
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
    @WithMockUser(username = "admin", password = "12345", roles = "USER")
    public void testGetIssueByIdNotFound() throws Exception {
        when(issuesService.getById(9999L)).thenThrow(IssueIdNotFound.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/issues/9999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", password = "12345", roles = {"USER", "ADMIN"})
    public void testUpdateIssue() throws Exception {
        Long id = 1L;
        IssuesDTO issueDTO = new IssuesDTO();
        issueDTO.setUrlDto("https://example.com");
        issueDTO.setNameCompanyDto("Example Company");
        issueDTO.setDescriptionDto("Description");
        issueDTO.setDateDto(LocalDate.now());
        issueDTO.setTimeDto(LocalTime.now());

        Issues updatedIssue = new Issues();
        updatedIssue.setId(id);
        updatedIssue.setUrl(issueDTO.getUrlDto());
        updatedIssue.setNameCompany(issueDTO.getNameCompanyDto());
        updatedIssue.setDescription(issueDTO.getDescriptionDto());
        updatedIssue.setDate(issueDTO.getDateDto());
        updatedIssue.setTime(issueDTO.getTimeDto());

        when(issuesService.updateIssue(id, issueDTO)).thenReturn(updatedIssue);

        mockMvc.perform(put("/issues/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(issueDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Issue atualizada com sucesso."));
    }

    @Test
    @WithMockUser(username = "admin", password = "12345", roles = {"USER", "ADMIN"})
    public void testFindAll() throws Exception {
        List<IssuesDTO> issueDTOList = Arrays.asList(new IssuesDTO(), new IssuesDTO(), new IssuesDTO());

        List<Issues> issueList = Arrays.asList(new Issues(), new Issues(), new Issues());

        when(issuesService.findAll()).thenReturn(issueList);

        mockMvc.perform(get("/issues")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(issueDTOList.size())));

    }

    @Test
    @WithMockUser(username = "admin", password = "12345", roles = {"USER", "ADMIN"})
    public void testUpdateIssueWithEntityNotFoundException() throws Exception {
        Long id = 1L;
        IssuesDTO issueDTO = new IssuesDTO();

        when(issuesService.updateIssue(id, issueDTO)).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(put("/issues/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(issueDTO)))
                .andExpect(status().isNotFound());

    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
 //
}