import java.math.BigDecimal;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Pi {
  static BigDecimal getPi() throws IOException {
    String piString = Files.readString(Path.of("pi.dat"));
    BigDecimal val = new BigDecimal(piString);
    return val;
  }
}