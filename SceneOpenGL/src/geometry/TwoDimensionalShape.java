package geometry;

/* 
 * Vincent Testagrossa
 * CMSC 405 - JOGL Project
 * 
 * A lot of the class is reused from my CMSC 335 class, with a lot of changes made to the order of glPopMatrix/glPushMatrix methods,
 * as well as removing the call to the parent draw method. Order needed to be specific per class since a lot of the shapes use different
 * methods for calculating vertices/etc. 
 * 
 * Abstract class TwoDimensionalShape -
 * Calls the Parent constructor and ensures there are two dimensions. Introduces the abstract method getArea() and the field
 * double area, which all 2D shapes will have.
 */
public class TwoDimensionalShape extends Shape {
    double area;
    public TwoDimensionalShape(){
        this(1, "2D Shape");
    }
    public TwoDimensionalShape(double area, String name){
        super(2, name);
        this.area = area;
    }
    public double getArea(){
        return area;
    }
}
