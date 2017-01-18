import java.math.*;
public class FFT {

	
	 public static Complex[] fft(Complex[] x) {
	        int N = x.length;

	        if (N == 1) return new Complex[] { x[0] };

	        if (N % 2 != 0) { throw new RuntimeException("N is not a power of 2"); }

	        Complex[] even = new Complex[N/2];
	        for (int k = 0; k < N/2; k++) {
	            even[k] = x[2*k];
	        }
	        Complex[] q = fft(even);

	        Complex[] odd  = even;  
	        for (int k = 0; k < N/2; k++) {
	            odd[k] = x[2*k + 1];
	        }
	        Complex[] r = fft(odd);

	        Complex[] y = new Complex[N];
	        for (int k = 0; k < N/2; k++) {
	            double kth = -2 * k * Math.PI / N;
	            Complex wk = new Complex(Math.cos(kth), Math.sin(kth));
	            y[k]       = q[k].plus(wk.times(r[k]));
	            y[k + N/2] = q[k].minus(wk.times(r[k]));
	        }
	        return y;
	    }
}
