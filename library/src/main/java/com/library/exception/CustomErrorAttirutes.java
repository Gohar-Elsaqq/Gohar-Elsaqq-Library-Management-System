package com.library.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;

@Component
public class CustomErrorAttirutes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions errorAttributeOptions) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, errorAttributeOptions);
        errorAttributes.put("locale",webRequest.getLocale().toString());
        errorAttributes.put("success",Boolean.FALSE);
        errorAttributes.put("exception",errorAttributes.get("message"));
        errorAttributes.put("status",errorAttributes.get("error"));
        errorAttributes.put("details", Arrays.asList(errorAttributes.get("message")));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        errorAttributes.put("timestamp", LocalDateTime.now().format(formatter));
        errorAttributes.remove("error");
        errorAttributes.remove("path");
        errorAttributes.remove("trace");
        return errorAttributes;
    }


}
