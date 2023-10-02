package com.jagiya.common.config;

import com.jagiya.common.exception.ExceptionCode;
import com.jagiya.common.response.MessageCode;
import com.jagiya.common.response.CommonResponse;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.*;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;

import java.util.Map;

@Configuration
public class ApiDocsOperationCustomizer implements OperationCustomizer {

    @Override
    public Operation customize(Operation operation,
                               HandlerMethod handlerMethod) {
        ApiResponses apiResponses = operation.getResponses();
        apiResponses.forEach((code, apiResponse) -> {
            Content content = operation.getResponses().get(code).getContent();
            if (content != null) {
                content.keySet().forEach(mediaTypeKey -> {
                    final MediaType mediaType = content.get(mediaTypeKey);
                    mediaType.schema(customizeSchema(code, mediaType.getSchema()));
                });
            }
        });
        return operation;
    }

    private Schema<?> customizeSchema(String code, Schema<?> objSchema) {
        ModelConverters mc= ModelConverters.getInstance();
        Map<String, Schema> read = mc.read(CommonResponse.class);
        Schema<?> wrapperSchema = read.get("CommonResponse");

        String responseCode;
        String responseMessage;

        if (StringUtils.equals(code, String.valueOf(HttpStatus.OK.value()))) {
            responseCode = String.valueOf(HttpStatus.OK.value());
            responseMessage = HttpStatus.OK.getReasonPhrase();
        } else {
            ExceptionCode exceptionCode = ExceptionCode.resolve(code);
            responseCode = exceptionCode.getCode();
            responseMessage = exceptionCode.getMessage();
        }
        wrapperSchema.addProperties("data", objSchema);
        wrapperSchema.addProperty("code", new StringSchema()._default(responseCode));
        wrapperSchema.addProperty("message", new StringSchema()._default(responseMessage));
        return  wrapperSchema;
    }

}


