package com.catalisa.squad5.service;

import com.catalisa.squad5.exceptions.IssueNotFound;
import com.catalisa.squad5.model.Issues;
import com.catalisa.squad5.repository.IssuesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssuesService {
    @Autowired
    IssuesRepository issuesRepository;

    public Issues registerIssue(Issues updateIssues) {
        return issuesRepository.save(updateIssues);
    }

    public List<Issues> findAll() {
        return issuesRepository.findAll();
    }

    public Issues getById(Long id) {
        return issuesRepository.findById(id)
                .orElseThrow(() -> new IssueNotFound("Issue with ID: " + id + " not found."));
    }
}
