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
 * Public class Shape -
 * A shape has a number of dimensions and the default number of dimensions is 2, as shown in the constructor.
 * 
 * drawShape() has been added so that different shapes can be swapped into the Shape field in DisplayPanel.
 */
public class Shape {
    int numberOfDimensions;
    Double translateX, translateY, translateZ, scaleX, scaleY, scaleZ, rotateX, rotateY, rotateZ;
    final String name;
    public int getNumberOfDimensions() {
        return numberOfDimensions;
    }
    public Shape(){
        this(2, "Shape");
    }
    public Shape(int numberOfDimensions, String name){
        this.numberOfDimensions = numberOfDimensions;
        this.name = name;
        this.setTransform(0, 0, 0, 0, 0, 0);
    }
    public String getName(){
        return name;
    }
    // so that super.drawShape() for each shape will update the scaling, rotation, and translation;
    public void drawShape(GL2 gl, GLU glu){
        scale(gl);
        rotate(gl);
        translate(gl);
    }

    /*
     * These three methods are inherited by all of the shape classes and used within the draw methods. The order
     * is slightly more specific depending on the internal calculations done to generate vertices or faces, so
     * these are provided to place at the correct level.
     */
    public void rotate(GL2 gl){
        gl.glRotated(rotateX, 1, 0, 0);
        gl.glRotated(rotateY, 0, 1, 0);
        gl.glRotated(rotateZ, 0, 0, 1);
    }
    public void scale(GL2 gl){
        gl.glScaled(scaleX, scaleY, scaleZ);
    }
    public void translate(GL2 gl){
        gl.glTranslated(translateX, translateY, translateZ);
    }

    /*
     * Provides different options for setting the transform of the shape. Individually, or the entire transform.
     * Transform has two options; the first has the ability to change scaling, while the second has a default of
     * 1.0;
     */
    public void setTransform(double sX, double sY, double sZ, double rX, double rY, double rZ, double tX, double tY, double tZ){
        setScale(sX, sY, sZ);
        setRotation(rX, rY, rZ);
        setTranslation(tX, tY, tZ);
    }
    public void setTransform(double rX, double rY, double rZ, double tX, double tY, double tZ){
        this.setTransform(1, 1, 1, rX, rY, rZ, tX, tY, tZ);
    }
    public void setScale(double sX, double sY, double sZ){
        scaleX = sX;
        scaleY = sY;
        scaleZ = sZ;
    }
    public void setRotation( double rX, double rY, double rZ){
        rotateX = rX;
        rotateY = rY;
        rotateZ = rZ;
    }
    public void setTranslation(double tX, double tY, double tZ) {
        translateX = tX;
        translateY = tY;
        translateZ = tZ;
    }
}
