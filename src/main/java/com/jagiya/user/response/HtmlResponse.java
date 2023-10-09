package com.jagiya.user.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class HtmlResponse {

    @Schema(description = "html소스", example = "<!DOCTYPE html><html><head>    <meta charset='UTF-8'>    <title>Title</title></head><body>body 입니다.</body></html>", name = "html")
    private String html;
}
