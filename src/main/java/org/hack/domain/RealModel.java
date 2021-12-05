package org.hack.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hack.domain.bean.ModelIn;
import org.hack.domain.bean.ModelOut;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class RealModel implements ModelLoader{

    private final CallR callR;

    @Override
    public List<ModelOut> getData(ModelIn in) {
        List<ModelOut> out = null;
        String uuid = UUID.randomUUID().toString();
        try {
            callR.setInit(uuid, in);
            callR.call(uuid);
            out = callR.getOut(uuid);
        } catch (Exception e) {
            log.error("Erreur lors de la simulation", e);
            out = new ArrayList<>(); // Retourne une liste vide en cas d'erreur
        }
        return out;
    }
}
