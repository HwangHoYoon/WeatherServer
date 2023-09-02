package com.jagiya.main.dto.login;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
@Schema(description = "ApplePublicKey")
public class ApplePublicKey {

    @Schema(description = "kty")
    private String kty;

    @Schema(description = "kid")
    private String kid;

    @Schema(description = "use")
    private String use;

    @Schema(description = "alg")
    private String alg;

    @Schema(description = "n")
    private String n;

    @Schema(description = "e")
    private String e;

}
