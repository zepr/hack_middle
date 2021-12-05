package org.hack.infra;

import org.hack.domain.bean.Previsions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AleaLoader {

    private Map<String, List<Previsions>> map = new HashMap<>();

    public AleaLoader(@Value("classpath:alea.csv") Resource r) throws IOException {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(r.getInputStream(), "UTF-8"));) {

            String line;
            String[] items;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                items = line.split(";");
                if (items.length > 2 && !firstLine) {
                    String code = items[1];
                    String annee = items[2].substring(1, 5);
                    String val = code + ":" + annee;

                    double eau = Double.parseDouble(items[3].replace(',', '.'));
                    double secheresse = Double.parseDouble(items[4].replace(',', '.'));
                    Previsions prev = new Previsions(eau, secheresse);

                    if (!map.containsKey(val)) {
                        map.put(val, new ArrayList<>());
                    }
                    map.get(val).add(prev);
                }
                firstLine = false;
            }
        }
    }

    public List<Previsions> getPrevisions(String codePostal, String annee) {
        return map.get(codePostal + ":" + annee);
    }
}
