package org.hack.domain;

import org.hack.domain.bean.ModelIn;
import org.hack.domain.bean.ModelOut;

import java.util.List;

public interface ModelLoader {
    List<ModelOut> getData(ModelIn in);
}
