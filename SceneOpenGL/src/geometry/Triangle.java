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
 * Class Triangle -
 * A triangle has a base and a height, which are used to find the area. The methods setBase() and setHeight() are validated for numbers
 * greater than 0 and will throw IllegalArgument exceptions for args 0 or less. setBase() and setHeight() are used in the overloaded
 * constructor with valid inputs in the default constructor, so setArea() is called during the setBase()/setHeight() to ensure the
 * area is updated.
 * 
 * drawShape() - Just draws a triangle using the gltriangle option in begin. Traces the outline as well.
 */
public class Triangle extends TwoDimensionalShape {
    private double base, height;
    
    public Triangle(){
        this(1, 1);
    }
    public Triangle(double base, double height) {
        super(((base * height)/2), "Triangle");
        setBase(base);
        setHeight(height);
    }

    private void setArea(double base, double height){
        area = (base * height)/2;
    }
    public double getArea() {
        return area;
    }
    public double getBase() {
        return base;
    }
    public double getHeight() {
        return height;
    }
    public void setBase(double base) throws IllegalArgumentException {
        if (base <= 0){
            String err = "ERROR: Base must be positive.";
            throw new IllegalArgumentException(err);
        }
        else{
            this.base = base;
            setArea(this.base, this.height);
        }
    }
    public void setHeight(double height) throws IllegalArgumentException{
        if (height <= 0){
            String err = "ERROR: Height must be positive.";
            throw new IllegalArgumentException(err);
        }
        else{
            this.height = height;
            setArea(this.base, this.height);
        }
    }
    public void drawShape(GL2 gl, GLU glu){
        drawShape(gl, 0, 0, 0.5);
    }
    public void drawShape(GL2 gl, double red, double green, double blue){
        translate(gl);
        gl.glPushMatrix();
            rotate(gl);
            gl.glPushMatrix();
                scale(gl);
                gl.glColor3d(red, green, blue);
                gl.glBegin(GL2.GL_TRIANGLES);
                gl.glVertex3d(-(base/2), -(height/2), 0.0);
                gl.glVertex3d(0, (height/2), 0.0);
                gl.glVertex3d((base/2), -(height/2), 0.0);
                gl.glEnd();
            gl.glPopMatrix();
            drawOUtline(gl);
        gl.glPopMatrix();
    }
    private void drawOUtline(GL2 gl){
        gl.glColor3d(1, 1, 1);
        gl.glBegin(GL2.GL_LINE_LOOP);
        gl.glVertex3d(-(base/2), -(height/2), 0.0);
        gl.glVertex3d(0, (height/2), 0.0);
        gl.glVertex3d((base/2), -(height/2), 0.0);
        gl.glEnd();
    }
}
