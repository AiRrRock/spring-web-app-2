package com.geekbrains.webapp.controllers;

import com.geekbrains.webapp.configs.UselessAspect;
import com.geekbrains.webapp.dtos.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/statistics")
@RequiredArgsConstructor
public class StatisticsController {
    private final UselessAspect aspect;

    @GetMapping
    public String showStatistics() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String,Long> single: aspect.getDurations().entrySet()) {
            sb.append(single.getKey() + single.getValue() + "\n");
        }
        return sb.toString();
    }
}
