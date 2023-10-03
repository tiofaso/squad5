package com.catalisa.squad5.service;

import com.catalisa.squad5.exceptions.IssueIdNotFound;
import com.catalisa.squad5.model.Issues;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.catalisa.squad5.repository.IssuesRepository;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
class IssuesServiceTest {

    @InjectMocks
    private IssuesService issuesService;

    @Mock
    private IssuesRepository issuesRepository;

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
}