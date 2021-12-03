package org.hack.infra;

import org.hack.domain.bean.ModelOut;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModelReader {

    private ModelReader() {}

    public static List<ModelOut> read(File f) throws IOException {
        List<ModelOut> out = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            String[] items;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                items = line.split(";");
                if (items.length == 3 && !firstLine) {
                    out.add(new ModelOut(
                       parse(items[0]),
                       parse(items[1]),
                       parse(items[2])
                    ));
                }
                firstLine = false;
            }
        }

        return out;
    }

    private static double parse(String s) {
        return Double.parseDouble(s.replace(',', '.'));
    }
}
