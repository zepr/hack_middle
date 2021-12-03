package org.hack.infra;

import lombok.extern.slf4j.Slf4j;
import org.hack.domain.ModelLoader;
import org.hack.domain.bean.ModelIn;
import org.hack.domain.bean.ModelOut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class MockModel implements ModelLoader {

    private List<ModelOut> modelOut;

    public MockModel(@Value("${mock.file}") String mockPath) {
        try {
            modelOut = ModelReader.read(new File(mockPath));
        } catch (IOException ioe) {
            log.info("Echec dde lecture du fichier de mock", ioe);
        }
    }

    @Override
    public List<ModelOut> getData(ModelIn in) {
        return modelOut;
    }
}
