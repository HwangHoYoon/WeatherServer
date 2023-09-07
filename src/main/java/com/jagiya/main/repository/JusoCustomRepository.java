package com.jagiya.main.repository;

import com.jagiya.main.entity.Juso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JusoCustomRepository  {
    public List<Juso> selectJusoGroupByLocation();

    public Juso selectJusoWhereCode(String code);
}
