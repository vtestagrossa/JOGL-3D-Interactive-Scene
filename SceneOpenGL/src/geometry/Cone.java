package geometry;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

/* 
 * Vincent Testagrossa
 * CMSC 405 - JOGL Project
 * 
 * A lot of the class is reused from my CMSC 335 class, with a lot of changes made to the order of glPopMatrix/glPushMatrix methods,
 * as well as removing the call to the parent draw method. Order needed to be specific per class since a lot of the shapes use different
 * methods for calculating vertices/etc. Part of the geometry package, but unused in the DisplayPanel
 * 
 * Class Cone - 
 * A right circular cone has a height and a radius. Whenever the setRadius() or setHeight() methods are called, they are validated
 * for numbers greater than 0 and setVolume() is called to update the volume field.
 */
public class Cone extends ThreeDimensionalShape {
    private double radius, height;

    public Cone(){
        this(0.5, 7);
    }
    public Cone(double radius, double height){
        super((Math.PI * Math.pow(radius, 2) * (height/3.0)), "Cone");
        setRadius(radius);
        setHeight(height);
    }
    private void setVolume(double radius, double height){
        volume = Math.PI * Math.pow(radius, 2) * (height/3.0);
    }
    @Override
    public double getVolume() {
        return volume;
    }
    public double getRadius() {
        return radius;
    }
    public double getHeight() {
        return height;
    }
    public void setRadius(double radius) throws IllegalArgumentException {
        if (radius <= 0){
            String err = "ERROR: Radius must be positive.";
            throw new IllegalArgumentException(err);
        }
        else{
            this.radius = radius;
            setVolume(this.radius, this.height);
        }
    }
    public void setHeight(double height) throws IllegalArgumentException {
        if (height <= 0){
            String err = "ERROR: Height must be positive.";
            throw new IllegalArgumentException(err);
        }
        else{
            this.height = height;
            setVolume(this.radius, this.height);
        }
    }

    /*
     * Uses GLUquadric to draw a wire mesh cone. When looking for an option that could be rotated I came across using GLU and 
     * quadrics to draw more complex objects. Also used in the Cylinder class. Scaled and rotated to fit better in the GLJPanel.
     */
    public void drawShape(GL2 gl, GLU glu){
        drawShape(gl,glu, 32, 32);
    }
    public void drawShape(GL2 gl, GLU glu, int slices, int stacks){
        gl.glPushMatrix();
            translate(gl);
            gl.glPushMatrix();
                rotate(gl);
                gl.glPushMatrix();
                    scale(gl);
                    gl.glColor3d(0.5,0.1,0.1);
                    GLUquadric cone = glu.gluNewQuadric();
                    glu.gluQuadricDrawStyle(cone, GLU.GLU_LINE);
                    glu.gluQuadricNormals(cone, GLU.GLU_SMOOTH);
                    glu.gluCylinder(cone, radius, 0, height, slices, stacks);
                gl.glPopMatrix();
            gl.glPopMatrix();
        gl.glPopMatrix();
    }
}
