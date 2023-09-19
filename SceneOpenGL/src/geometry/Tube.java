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
 * methods for calculating vertices/etc. 
 * 
 * Class Tube - 
 * A Tube has a radius and a height, which are updated by setRadius() and setHeight(). Each of those methods are validated for
 * numbers greater than 0, and then setVolume() is called to keep the field volume updated.
 */
public class Tube extends ThreeDimensionalShape {
    private double radius, height;

    public Tube(){
        this(1, 5);
    }
    public Tube(double radius, double height){
        super((Math.PI * Math.pow(radius, 2) * height), "Tube");
        setRadius(radius);
        setHeight(height);
    }

    private void setVolume(double radius, double height){
        volume = Math.PI * Math.pow(radius, 2) * height;
    }
    @Override
    public double getVolume() {
        return volume;
    }
    public double getHeight() {
        return height;
    }
    public double getRadius() {
        return radius;
    }
    public void setHeight(double height) throws IllegalArgumentException {
        if (height <= 0){
            String err = "ERROR: Radius must be positive.";
            throw new IllegalArgumentException(err);
        }
        else{
            this.height = height;
            setVolume(this.radius, this.height);
        }
    }
    public void setRadius(double radius) throws IllegalArgumentException {
        if (radius <= 0){
            String err = "ERROR: Height must be positive.";
            throw new IllegalArgumentException(err);
        }
        else{
            this.radius = radius;
            setVolume(this.radius, this.height);
        }
    }
    public void drawShape(GL2 gl, GLU glu){
        drawShape(gl,glu, 32, 10, 0.2, 0.2, 0.2);
    }

    /*
     * Uses GLUquadric to draw the tube of a Tube
     */
    public void drawShape(GL2 gl, GLU glu, int slices, int stacks, double red, double green, double blue){
        gl.glPushMatrix();
            translate(gl);
            gl.glPushMatrix();
                rotate(gl);
                gl.glPushMatrix();
                    scale(gl);
                    GLUquadric Tube = glu.gluNewQuadric();
                    gl.glColor3d(0.3,0.3,0.1);
                    glu.gluQuadricDrawStyle(Tube, GLU.GLU_FILL);
                    glu.gluQuadricNormals(Tube, GLU.GLU_SMOOTH);
                    glu.gluCylinder(Tube, radius, radius, height, slices, stacks);
                gl.glPopMatrix();
            gl.glPopMatrix();
        gl.glPopMatrix();
    }
}
