package geometry;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

/* 
 * Vincent Testagrossa
 * CMSC 405 - JOGL Project
 * 
 * A lot of the class is reused from my CMSC 335 class, with a lot of changes made to the order of glPopMatrix/glPushMatrix methods,
 * as well as removing the call to the parent draw method. Order needed to be specific per class since a lot of the shapes use different
 * methods for calculating vertices/etc. 
 * 
 * Class Square -
 * Extends the Rectangle class, which is a child of TwoDimensionalShape, so a Square is a TwoDimensionalShape. Since setLength() and
 * setWidth() are inherited, both need to be implemented with both parent methods called for each with the length as the parameter.
 * 
 * drawShape() also included in this class. Just calls the super.drawShape() and changes the color.
 * 
 */
public class Square extends Rectangle{
    public Square(){
        this(1);
    }
    public Square(double length){
        super(length, length, "Square");
    }
    public void setLength(double length){
        super.setLength(length);
        super.setWidth(length);
    }
    public void setWidth(double length){
        super.setLength(length);
        super.setWidth(length);
    }
    public void drawShape(GL2 gl, GLU glu){
        super.drawShape(gl, 0.6, 0, 0.6);
    }
    public void drawShape(GL2 gl, GLU glu, double red, double green, double blue){
        super.drawShape(gl, red, green, blue);
    }
}
