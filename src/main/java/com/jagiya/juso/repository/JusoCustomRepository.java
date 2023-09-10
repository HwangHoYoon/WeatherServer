package com.jagiya.juso.repository;

import com.jagiya.juso.entity.Juso;

import java.util.List;

public interface JusoCustomRepository  {
    public List<Juso> selectJusoGroupByLocation();
}
