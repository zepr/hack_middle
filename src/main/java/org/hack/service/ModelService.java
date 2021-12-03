package org.hack.service;

import lombok.RequiredArgsConstructor;
import org.hack.domain.ModelLoader;
import org.hack.domain.bean.ModelIn;
import org.hack.domain.bean.ModelOut;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/")
@RequiredArgsConstructor
public class ModelService {
    private final ModelLoader loader;

    @GetMapping(value = "/model", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ModelOut> call() {
        return loader.getData(new ModelIn());
    }
}