package org.hack.infra.proc;

import java.io.File;
import java.io.IOException;

public class Executor {

    private Executor() {}

    public static int run(String path, String command) throws IOException {
        ProcessBuilder builder =
                new ProcessBuilder()
                        .inheritIO()
                        .command("sh", "-c", command);
        if (path != null) {
            builder.directory(new File(path));
        }

        Process process = builder.start();

        int procReturnValue;
        try {
            procReturnValue = process.waitFor();
        } catch (InterruptedException ie) {
            throw new IOException("Interruption", ie);
        }

        return procReturnValue;
    }
}
