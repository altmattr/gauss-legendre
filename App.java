import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.io.IOException;

// why use arrays instead of functions?  Java doesn't have tail recursion, so we run out of stack space.  Heaps of memory avaialable though.  It is also wayyyyyyy slower

public class App{
  static int prec = 100000;
  static MathContext up = MathContext.UNLIMITED;

  public static void main(String[] args) throws IOException {
    App app = new App();
    BigDecimal rpi = Pi.getPi();
    System.out.println(app.pi(20));
    for(int i = 1; i < 12; i++){
      System.out.println("----" + i + "-----");
      // System.out.println(pid);
      System.out.println(app.pi(i));
      // System.out.println(app.pi(precision));
      System.out.println(rpi.round(new MathContext((int)Math.pow(2, i), RoundingMode.HALF_UP)));
    }
  }

  // is capapble of calculating up to 1 million digits (20 iterations).  Will throw
  // java.lang.ArithmeticException beyond that simply due to limitations in BigDecimal
  public BigDecimal pi(int iters){
    if (iters <= 0){
      return new BigDecimal(0);
    }
    BigDecimal[] a = new BigDecimal[iters];
    BigDecimal[] b = new BigDecimal[iters];
    BigDecimal[] t = new BigDecimal[iters];
    BigDecimal[] p = new BigDecimal[iters];

    //algorithm is capable of quadratic precision
    int precision = (int)Math.pow(2, iters);
    MathContext mc = new MathContext(precision, RoundingMode.HALF_EVEN); // note, you need to use HALF_UP or HALF_EVEN to get the last digit to stay precise more often, but you will still get errors there
    a[0] = new BigDecimal(1);
    b[0] = new BigDecimal(1).divide(new BigDecimal(2).sqrt(mc), mc);
    t[0] = new BigDecimal(1).divide(new BigDecimal(4));
    p[0] = new BigDecimal(1);

    // memoise the values (in-practice this is faster than calling methods)
    for(int n = 1; n < iters; n++){
      a[n] = a[n-1].add(b[n-1]).divide(new BigDecimal(2));
      b[n] = a[n-1].multiply(b[n-1]).sqrt(mc);
      p[n] = p[n-1].multiply(new BigDecimal(2));
      t[n] = t[n-1].subtract(p[n-1].multiply(a[n-1].subtract(a[n]).pow(2)));
    }

    // last index is one less than precision and holds the values we need for the most precise pi calculation
    int fin = iters - 1;
    BigDecimal pi = (a[iters-1]
                    .add(b[iters-1]
                    )
                    .pow(2)
                    .divide(t[iters-1].multiply(new BigDecimal(4)), mc)
                    );

    // We can't expect the last digits to keep precision (thought it mostly does), so we knock one off when reporting
    MathContext mcReport = new MathContext(precision - iters, RoundingMode.HALF_UP);

    return pi.round(mcReport);
  }

}