import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;
import java.math.MathContext;
import java.math.BigDecimal;
import java.io.IOException;

public class PiTest{
  private static App app;
  private static BigDecimal realPi;

  @BeforeClass
  public static void setUp() throws IOException {
    app = new App();
    realPi = Pi.getPi();
  }

  @Test
  public void trivialTest(){
    assertEquals(4,4);
  }

  @Test
  public void testLowPrecision(){
    assertEquals(app.pi(2).toString(), "3.1");
    assertEquals(app.pi(3).toString(), "3.1416");
    assertEquals(app.pi(4).toString(), "3.14159265359");
  }

  @Test
  public void testEdgesLow(){
    assertEquals(app.pi(0).toString(), "0");
    assertEquals(app.pi(-1).toString(), "0");
    assertEquals(app.pi(Integer.MIN_VALUE).toString(), "0");
  }

  @Test(expected = java.lang.ArithmeticException.class)
  public void testEdgesHigh(){
    assertEquals(app.pi(Integer.MAX_VALUE).toString(), "0");
  }

  @Test(expected = java.lang.ArithmeticException.class)
  public void testHighPrecision(){
    // would result in over a billion digits, and we are not capable of that
    app.pi(30).toString();
  }

  // will reliably work up to 20 (though that last one takes a very long time)
  @Test
  public void exhaustiveTestFast(){
    for(int i = 1; i < 12; i++){
      BigDecimal myPi = app.pi(i);
      BigDecimal otherPi = realPi.round(new MathContext((int)Math.pow(2, i) - i));
      assertEquals(myPi, otherPi);
    }
  }
  @Test
  public void exhaustiveTestExtended(){
    for(int i = 12; i < 21; i++){
      BigDecimal myPi = app.pi(i);
      BigDecimal otherPi = realPi.round(new MathContext((int)Math.pow(2, i) - i));
      int digits = myPi.toString().length();
      System.out.println("---" + i + "---");
      System.out.println(myPi.toString().substring(Math.max(0, digits - 10)));
      System.out.println(otherPi.toString().substring(Math.max(0, digits - 10)));
      assertEquals(myPi, otherPi);
    }
  }
}