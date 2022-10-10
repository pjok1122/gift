package org.study.gift.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.study.gift.api.repository.SampleEntityRepository;
import org.study.gift.core.entity.SampleEntity;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final SampleEntityRepository sampleEntityRepository;

    @GetMapping("/sample-entity")
    public List<SampleEntity> getSamples() {
        return sampleEntityRepository.findAll();
    }

    @PostMapping("/sample-entity")
    public SampleEntity insertSample() {
        return sampleEntityRepository.save(SampleEntity.builder()
                                                       .col1("test")
                                                       .camelName("camelName")
                                                       .build());
    }
}
