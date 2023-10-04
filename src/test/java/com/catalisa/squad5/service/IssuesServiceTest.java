package com.catalisa.squad5.service;

import com.catalisa.squad5.dtos.IssueDTO;
import com.catalisa.squad5.exceptions.IssueIdNotFound;
import com.catalisa.squad5.model.Issues;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.catalisa.squad5.repository.IssuesRepository;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
class IssuesServiceTest {

    @InjectMocks
    private IssuesService issuesService;

    @Mock
    private IssuesRepository issuesRepository;

    public IssuesServiceTest() {
        MockitoAnnotations.initMocks(this);
    }

    //registro de falha
    @Test
    public void testRegisterIssue() {
        Issues mockIssue = new Issues();

        when(issuesRepository.save(any(Issues.class))).thenReturn(mockIssue);

        Issues savedIssue = issuesService.registerIssue(mockIssue);

        assertEquals(mockIssue, savedIssue);
        verify(issuesRepository, times(1)).save(any(Issues.class));
    }

    //busca falha por id
    @Test
    public void testGetById() {
        long id = 1;
        Issues mockIssue = new Issues();
        mockIssue.setId(id);

        when(issuesRepository.findById(id)).thenReturn(Optional.of(mockIssue));

        Issues retrievedIssue = issuesService.getById(id);

        assertEquals(mockIssue, retrievedIssue);
        verify(issuesRepository, times(1)).findById(id);
    }

    //busca falha por id nao encontrado
    @Test
    public void testGetByIdNotFound() {
        when(issuesRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IssueIdNotFound.class, () -> {
            issuesService.getById(1L);
        });
    }

    @Test
    public void testUpdateIssue() {
        Long id = 1L;
        IssueDTO issueDTO = new IssueDTO();
        issueDTO.setUrl("https://example.com");
        issueDTO.setNameCompany("Example Company");
        issueDTO.setDescription("Description");
        issueDTO.setDate(LocalDate.now());
        issueDTO.setTime(LocalTime.now());

        Issues existingIssue = new Issues();
        existingIssue.setId(id);
        existingIssue.setUrl("https://oldurl.com");
        existingIssue.setNameCompany("Old Company");
        existingIssue.setDescription("Old Description");
        existingIssue.setDate(LocalDate.now().minusDays(1));
        existingIssue.setTime(LocalTime.now().minusHours(1));

        when(issuesRepository.findById(id)).thenReturn(Optional.of(existingIssue));
        when(issuesRepository.save(any(Issues.class))).thenReturn(existingIssue);

        Issues updatedIssue = issuesService.updateIssue(id, issueDTO);

        // Verificar se os campos foram atualizados corretamente
        assertEquals(issueDTO.getUrl(), updatedIssue.getUrl());
        assertEquals(issueDTO.getNameCompany(), updatedIssue.getNameCompany());
        assertEquals(issueDTO.getDescription(), updatedIssue.getDescription());
        assertEquals(issueDTO.getDate(), updatedIssue.getDate());
        assertEquals(issueDTO.getTime(), updatedIssue.getTime());
    }

}