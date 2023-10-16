package com.catalisa.squad5.service;


import com.catalisa.squad5.dtos.IssuesDTO;
import com.catalisa.squad5.exceptions.IssueIdNotFound;
import com.catalisa.squad5.model.Issues;
import com.catalisa.squad5.repository.IssuesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


@Service
public class IssuesService {
    @Autowired
    IssuesRepository issuesRepository;

    public Issues registerIssue(Issues updateIssues) {

        updateIssues.setDate(LocalDate.now());
        updateIssues.setTime(LocalTime.now());
        updateIssues.setTask(0);

        return issuesRepository.save(updateIssues);
    }

    public List<Issues> findAll() {
        return issuesRepository.findAll();
    }

    public Issues getById(Long id) {
        return issuesRepository.findById(id)
                .orElseThrow(() -> new IssueIdNotFound("Issue with ID: " + id + " not found."));
    }

    public Issues updateIssue(Long id, IssuesDTO issuesDTO) {
        Issues existingIssue = issuesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Issue not found."));

        existingIssue.setUrl(issuesDTO.getUrlDto());
        existingIssue.setNameCompany(issuesDTO.getNameCompanyDto());
        existingIssue.setDescription(issuesDTO.getDescriptionDto());
        existingIssue.setDate(issuesDTO.getDateDto());
        existingIssue.setTime(issuesDTO.getTimeDto());

        return issuesRepository.save(existingIssue);
    }
    public Issues updateTask(Long id, Integer newTask) {
        Optional<Issues> optionalIssue = issuesRepository.findById(id);
        Issues existingIssue = optionalIssue.orElseThrow(() -> new IssueIdNotFound("Issue with ID: " + id + " not found."));

        existingIssue.setTask(newTask);

        return issuesRepository.save(existingIssue);
    }
}
