package com.jagiya.juso.response;

import lombok.Data;

import java.util.List;

@Data
public class JusoResult {
    private JusoCommon common;
    private List<JusoData> juso;
}
