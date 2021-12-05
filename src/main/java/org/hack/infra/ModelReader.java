package org.hack.infra;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hack.domain.bean.ModelOut;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ModelReader {

    private ModelReader() {}

    public static List<ModelOut> read(Resource r) throws IOException {
        List<ModelOut> out = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(r.getInputStream(), "UTF-8"));) {
            String line;
            String[] items;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                items = line.split(";");
                if (items.length > 1 && !firstLine) {
                    out.add(new ModelOut(
                       parse(items[0]),
                       parse(items[1])//,
                       //parse(items[2])
                    ));
                }
                firstLine = false;
            }
            // Complément à 365
            ModelOut latest = out.get(out.size() - 1);
            while (out.size() < 365) {
                out.add(new ModelOut(latest.getRendement(), latest.getMatiereSeche()));
            }
        }

        return out;
    }

    private static double parse(String s) {
        return Double.parseDouble(s.replace(',', '.'));
    }

}
