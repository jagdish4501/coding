package cpjava;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Shell {
    public static void cmd() {
        try {
            String command = Io.nextLine();
            ProcessBuilder builder = new ProcessBuilder("bash", "-ic", command);
            builder.redirectErrorStream(true);
            Process process = builder.start();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                Io.outln(line);
            }
            int exitCode = process.waitFor();
            Io.outln("Exited with code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
