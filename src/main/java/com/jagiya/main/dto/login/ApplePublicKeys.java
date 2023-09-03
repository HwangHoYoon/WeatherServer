package com.jagiya.main.dto.login;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Getter
@Setter
@Data
@Schema(description = "ApplePublicKeys")
public class ApplePublicKeys {

    @Schema(description = "getKeys")
    private List<ApplePublicKey> keys;

}
