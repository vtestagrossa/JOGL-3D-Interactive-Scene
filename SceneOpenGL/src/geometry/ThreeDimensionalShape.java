package geometry;
/* 
 * Vincent Testagrossa
 * CMSC 405 - JOGL Project
 * 
 * A lot of the class is reused from my CMSC 335 class, with a lot of changes made to the order of glPopMatrix/glPushMatrix methods,
 * as well as removing the call to the parent draw method. Order needed to be specific per class since a lot of the shapes use different
 * methods for calculating vertices/etc. 
 * 
 * Abstract class ThreeDimensionalShape -
 * Calls the Parent constructor and ensures there are now three dimensions. Introduces the abstract method getVolume(), and the
 * field double volume, which all 3D shapes will have.
 */
public class ThreeDimensionalShape extends Shape {
    double volume;
    public ThreeDimensionalShape(){
        this(1, "3D Shape");
    }
    public ThreeDimensionalShape(double volume, String name){
        super(3, name);
        this.volume = volume;
    }
    public double getVolume(){
        return volume;
    }
}
