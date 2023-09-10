package com.jagiya.weather.repository;

import com.jagiya.weather.entity.Juso;

import java.util.List;

public interface JusoCustomRepository  {
    public List<Juso> selectJusoGroupByLocation();
}
