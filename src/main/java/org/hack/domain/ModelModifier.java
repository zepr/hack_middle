package org.hack.domain;

import org.hack.domain.bean.ModelOut;

import java.util.List;

public interface ModelModifier {
    public List<ModelOut> alter(List<ModelOut> input, int debut, int fin, int intensity);
}
