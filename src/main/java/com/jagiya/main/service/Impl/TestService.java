package com.jagiya.main.service.Impl;

import com.jagiya.main.dto.TestRes;
import com.jagiya.main.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TestService {
 
    private final TestRepository testRepository;

    public TestRes findById(Long id) {
        return new TestRes(testRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("no such data")));
    }

}
