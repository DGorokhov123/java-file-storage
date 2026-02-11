package ru.dgorokhov.filestorage.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class MainController {

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

    @Value("${spring.servlet.multipart.max-request-size}")
    private String maxRequestSize;

    @Value("${server.tomcat.max-part-count}")
    private String maxPartCount;

    @ModelAttribute("maxFileSize")
    public String getMaxFileSize() {
        return maxFileSize;
    }

    @ModelAttribute("maxRequestSize")
    public String getMaxRequestSize() {
        return maxRequestSize;
    }

    @ModelAttribute("maxPartCount")
    public String getMaxPartCount() {
        return maxPartCount;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

}