package com.jagiya.main.service.Impl;

import com.jagiya.main.dto.TestRes;
import com.jagiya.main.entity.Temp;
import com.jagiya.main.repository.TempRepository;
import com.jagiya.main.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TestService {
 
    private final TestRepository testRepository;

    private final TempRepository tempRepository;

    public TestRes findById(Long id) {
        return new TestRes(testRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("no such data")));
    }

    public List<Temp> findByFld06AndFld08(String Fld06, String Fld08) {
        return tempRepository.findByFld06AndFld08(Fld06, Fld08);
    }

    public List<Temp> findByFld06(String Fld06) {
        return tempRepository.findByFld06(Fld06);
    }

    public void save(Temp t) {
        tempRepository.save(t);
    }

}
