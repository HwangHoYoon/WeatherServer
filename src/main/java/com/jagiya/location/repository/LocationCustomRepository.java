package com.jagiya.location.repository;

import com.jagiya.location.entity.LocationGroup;

import java.util.List;

public interface LocationCustomRepository {

    public List<LocationGroup> selectLocationGroupByCityDo(List<String> cityDos);
}
