package org.hack.infra;

import lombok.extern.slf4j.Slf4j;
import org.hack.domain.CallR;
import org.hack.domain.bean.ModelIn;
import org.hack.domain.bean.ModelOut;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Component
@Slf4j
public class CallAlan implements CallR {

    private File tmpDir;

    public CallAlan() {
        tmpDir = new File(System.getProperty("java.io.tmpdir"));
        log.info("Répertoire temporaire : " + tmpDir.getAbsolutePath());
    }

    @Override
    public void setInit(String uuid, ModelIn in) throws IOException {
        File initFile = new File(tmpDir, "init_" + uuid + ".csv");
        try (OutputStream out = new FileOutputStream(initFile)) {
            addLine(out, "annee_simule", "RDTmax_var", "ep_co", "Rumax", "PMGmax", "Tmaxplant", "Toptplant");
            addLine(out,
                    "2017",
                    in.getRendementMax(),
                    in.getEpSol(),
                    in.getReserveUtileEau(),
                    in.getPmgMax(),
                    in.getTempMax(),
                    in.getTempOptimale());
        }
    }

    @Override
    public void setSimu(String uuid, ModelIn in) throws IOException {

    }

    @Override
    public void call(String uuid) throws IOException {

    }

    @Override
    public List<ModelOut> getOut(String uuid) throws IOException {
        return null;
    }


    private void addLine(OutputStream out, String... data) throws IOException {
        StringBuilder sb = new StringBuilder(256);
        boolean first = true;
        for (String s : data) {
            if (first) {
                first = false;
            } else {
                sb.append(";");
            }
            sb.append(s);
        }
        sb.append("\r\n");

        out.write(sb.toString().getBytes("UTF-8"));
    }


    public static void main(String[] args) throws IOException {
        ModelIn in = new ModelIn();
        in.setRendementMax("120");
        in.setEpSol("300");
        in.setReserveUtileEau("100");
        in.setPmgMax("42");
        in.setTempMax("40");
        in.setTempOptimale("15");

        CallAlan a = new CallAlan();
        a.setInit("aaa", in);
    }
}
