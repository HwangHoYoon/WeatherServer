package com.jagiya.main.dto.login;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MsgEntity {

    private String id;
    private AppleDTO result;

    public MsgEntity(String id, AppleDTO result) {
        this.id = id;
        this.result  = result;
    }

}
