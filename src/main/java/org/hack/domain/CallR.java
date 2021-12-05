package org.hack.domain;

import org.hack.domain.bean.ModelIn;
import org.hack.domain.bean.ModelOut;

import java.io.IOException;
import java.util.List;

public interface CallR {
    void setInit(String uuid, ModelIn in) throws IOException;
    void setSimu(String uuid, ModelIn in) throws IOException;
    void call(String uuid) throws IOException;
    List<ModelOut> getOut(String uuid) throws IOException;
}
