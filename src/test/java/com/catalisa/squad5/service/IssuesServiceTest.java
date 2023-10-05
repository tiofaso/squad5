package com.catalisa.squad5.service;

import com.catalisa.squad5.dtos.IssuesDTO;
import com.catalisa.squad5.exceptions.IssueIdNotFound;
import com.catalisa.squad5.model.Issues;
import com.catalisa.squad5.model.Users;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.catalisa.squad5.repository.IssuesRepository;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
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
    public void testFindAll() {
        List<Issues> issuesList = Arrays.asList(
                new Issues(1L, "https://example.com", "Company A", "Description A", "A", LocalDate.now(), LocalTime.now(), new Users()),
                new Issues(2L, "https://example.com", "Company B", "Description B", "B", LocalDate.now(), LocalTime.now(), new Users())
        );

        when(issuesRepository.findAll()).thenReturn(issuesList);

        List<Issues> result = issuesService.findAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getNameCompany()).isEqualTo("Company A");
        assertThat(result.get(1).getNameCompany()).isEqualTo("Company B");

        verify(issuesRepository, times(1)).findAll();
    }
    @Test
    public void testUpdateIssue() {
        Long id = 1L;
        IssuesDTO issuesDTO = new IssuesDTO();
        issuesDTO.setUrl("https://example.com");
        issuesDTO.setNameCompany("Example Company");
        issuesDTO.setDescription("Description");
        issuesDTO.setDate(LocalDate.now());
        issuesDTO.setTime(LocalTime.now());

        Issues existingIssue = new Issues();
        existingIssue.setId(id);
        existingIssue.setUrl("https://oldurl.com");
        existingIssue.setNameCompany("Old Company");
        existingIssue.setDescription("Old Description");
        existingIssue.setDate(LocalDate.now().minusDays(1));
        existingIssue.setTime(LocalTime.now().minusHours(1));

        when(issuesRepository.findById(id)).thenReturn(Optional.of(existingIssue));
        when(issuesRepository.save(any(Issues.class))).thenReturn(existingIssue);

        Issues updatedIssue = issuesService.updateIssue(id, issuesDTO);

        // Verificar se os campos foram atualizados corretamente
        assertEquals(issuesDTO.getUrl(), updatedIssue.getUrl());
        assertEquals(issuesDTO.getNameCompany(), updatedIssue.getNameCompany());
        assertEquals(issuesDTO.getDescription(), updatedIssue.getDescription());
        assertEquals(issuesDTO.getDate(), updatedIssue.getDate());
        assertEquals(issuesDTO.getTime(), updatedIssue.getTime());
    }

}