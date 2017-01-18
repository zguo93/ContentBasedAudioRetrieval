public class Complex {
    private final double realPart;   // the real part
    private final double imgPart;   // the imaginary part
 // create a new object with the given real and imaginary parts
    public Complex(double real, double imag) {
        realPart = real;
        imgPart = imag;
    }
    
 // return a new Complex object whose value is (this + b)
    public Complex plus(Complex b) {
        Complex a = this;             // invoking object
        double real = a.realPart + b.realPart;
        double imag = a.imgPart + b.imgPart;
        return new Complex(real, imag);
    }

    // return a new Complex object whose value is (this - b)
    public Complex minus(Complex b) {
        Complex a = this;
        double real = a.realPart - b.realPart;
        double imag = a.imgPart - b.imgPart;
        return new Complex(real, imag);
    }

    
 // return a string representation of the invoking Complex object
    public String toString() {
        if (imgPart == 0) return realPart + "";
        if (realPart == 0) return imgPart + "i";
        if (imgPart <  0) return realPart + " - " + (-imgPart) + "i";
        return realPart + " + " + imgPart + "i";
    }
    
    
 // scalar multiplication
    // return a new object whose value is (this * alpha)
    public Complex times(double alpha) {
        return new Complex(alpha * realPart, alpha * imgPart);
    }

    // return a new Complex object whose value is the conjugate of this
    public Complex conjugate() {  return new Complex(realPart, -imgPart); }

    
    

    // return abs/modulus/magnitude and angle/phase/argument
    public double abs()   { return Math.hypot(realPart, imgPart); }  // Math.sqrt(re*re + im*im)
    public double phase() { return Math.atan2(imgPart, realPart); }  // between -pi and pi

    
    // return a new Complex object whose value is (this * b)
    public Complex times(Complex b) {
        Complex a = this;
        double real = a.realPart * b.realPart - a.imgPart * b.imgPart;
        double imag = a.realPart * b.imgPart + a.imgPart * b.realPart;
        return new Complex(real, imag);
    }

 // return a new Complex object whose value is the reciprocal of this
    public Complex reciprocal() {
        double s = realPart*realPart + imgPart*imgPart;// s means scale
        return new Complex(realPart / s, -imgPart / s);
    }

    // return the real or imaginary part
    public double re() { return realPart; }
    public double im() { return imgPart; }

    // return a / b
    public Complex divides(Complex b) {
        Complex a = this;
        return a.times(b.reciprocal());
    }




}