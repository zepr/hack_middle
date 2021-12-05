package org.hack.domain;

import org.hack.domain.bean.ModelOut;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SimpleModifier implements ModelModifier {

    private final static double[] RATIO = { 0.984, 0.966, 0.957 };

    @Override
    public List<ModelOut> alter(List<ModelOut> input, int debut, int fin, int intensity) {

        double currentRatioRendement = 1;
        double currentRatioMS = 1;
        double previousRendement = 0;
        double previousMS = 0;

        intensity = Math.max(0, Math.min(2, intensity));

        List<ModelOut> altered = new ArrayList<>();

        for (int i = 0; i < input.size(); i++) {
            ModelOut currentRef = input.get(i);
            if (i >= debut && i <= fin) {
                currentRatioRendement *= RATIO[intensity];
                currentRatioMS *= RATIO[intensity];
            }
            double currentRendement = currentRef.getRendement() * currentRatioRendement;
            if (currentRendement < previousRendement) {
                currentRatioRendement = previousRendement / currentRef.getRendement();
                currentRendement = previousRendement;
            }
            double currentMS = currentRef.getMatiereSeche() * currentRatioMS;
            if (currentMS < previousMS) {
                currentRatioMS = previousMS / currentRef.getMatiereSeche();
                currentMS = previousMS;
            }

            altered.add(new ModelOut(currentRendement, currentMS));

            previousRendement = currentRendement;
            previousMS = currentMS;
        }

        return altered;
    }
}
