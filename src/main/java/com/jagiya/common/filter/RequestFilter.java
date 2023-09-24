package com.jagiya.common.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class RequestFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);
        CustomRequestWrapper customRequestWrapper = new CustomRequestWrapper((HttpServletRequest) request);
        long start = System.currentTimeMillis();
        chain.doFilter(requestWrapper, responseWrapper);
        long end = System.currentTimeMillis();

        log.info("\n" +
                        "[REQUEST] {} - {} {} - {}\n" +
                        "Headers : {}\n" +
                        "Request : {}\n" +
                        "Response : {}\n",
                ((HttpServletRequest) request).getMethod(),
                ((HttpServletRequest) request).getRequestURI(),
                responseWrapper.getStatus(),
                (end - start) / 1000.0,
                getHeaders((HttpServletRequest) request),
                buildAccessLog(customRequestWrapper),
                getResponseBody(responseWrapper));
    }

    private Map getHeaders(HttpServletRequest request) {
        Map headerMap = new HashMap<>();

        Enumeration headerArray = request.getHeaderNames();
        while (headerArray.hasMoreElements()) {
            String headerName = (String) headerArray.nextElement();
            headerMap.put(headerName, request.getHeader(headerName));
        }
        return headerMap;
    }

    private String getResponseBody(final HttpServletResponse response) throws IOException {
        String payload = null;
        ContentCachingResponseWrapper wrapper =
                WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                payload = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
                wrapper.copyBodyToResponse();
            }
        }
        return null == payload ? " - " : payload;
    }

    private String buildAccessLog(CustomRequestWrapper customRequestWrapper) {

        String requestURL = getRequestURL(customRequestWrapper);
        String remoteAddr = getRemoteAddr(customRequestWrapper);
        String method = getMethod(customRequestWrapper);
        String queryString = getQueryString(customRequestWrapper);
        String requestBody = getRequestBody(customRequestWrapper);

        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (requestURL != null) {
            sb
                    .append("\"").append("requestURL").append("\"")
                    .append(":")
                    .append("\"").append(requestURL).append("\"");
        }
        if (remoteAddr != null) {
            sb
                    .append(",")
                    .append("\"").append("remoteAddr").append("\"")
                    .append(":")
                    .append("\"").append(remoteAddr).append("\"");
        }
        if (method != null) {
            sb
                    .append(",")
                    .append("\"").append("method").append("\"")
                    .append(":")
                    .append("\"").append(method).append("\"");
        }
        if (queryString != null) {
            sb
                    .append(",")
                    .append("\"").append("queryString").append("\"")
                    .append(":")
                    .append("\"").append(queryString).append("\"");
        }
        if (requestBody != null && requestBody.length() > 0) {
            sb
                    .append(",")
                    .append("\"").append("body").append("\"")
                    .append(":")
                    .append("\"").append(requestBody).append("\"");
        }
        sb.append("}");
        return sb.toString();
    }

    private String getRequestBody(CustomRequestWrapper customRequestWrapper) {
        String content = null;
        String method = customRequestWrapper.getMethod().toLowerCase();

        // POST, PUT + application/json
        if (method.startsWith("p")) {
            if (customRequestWrapper.getContentType().toLowerCase().indexOf("json") > 0) {
                try {
                    content = new String(customRequestWrapper.getBody(), customRequestWrapper.getCharacterEncoding());
                } catch (UnsupportedEncodingException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return content;
    }

    private String getQueryString(CustomRequestWrapper customRequestWrapper) {
        String queryString = null;
        if (customRequestWrapper.getQueryString() != null) {
            queryString = customRequestWrapper.getQueryString();
        }
        return queryString;
    }

    private String getMethod(CustomRequestWrapper customRequestWrapper) {
        return customRequestWrapper.getMethod();
    }

    private String getRemoteAddr(CustomRequestWrapper customRequestWrapper) {
        return customRequestWrapper.getRemoteAddr();
    }

    private String getRequestURL(CustomRequestWrapper customRequestWrapper) {
        return customRequestWrapper.getRequestURL().toString();
    }
}