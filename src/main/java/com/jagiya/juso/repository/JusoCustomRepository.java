package com.jagiya.juso.repository;

import com.jagiya.juso.entity.Juso;
import com.jagiya.juso.entity.JusoGroup;

import java.util.List;

public interface JusoCustomRepository  {
    public List<Juso> selectJusoGroupByLocation();

    public List<JusoGroup> selectJusoGroupByCityDo(List<String> cityDos);
}
