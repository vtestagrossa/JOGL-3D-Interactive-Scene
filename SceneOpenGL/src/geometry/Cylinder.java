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
 * Cylinder uses the tube and circle classes to put end caps on the tube and make a solid cylinder.
 */

public class Cylinder extends ThreeDimensionalShape{
    private double radius, height;

    public Cylinder(){
        this(1, 5);
    }
    public Cylinder(double radius, double height){
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
        drawShape(gl, glu, 32, 10);
    }
    public void drawShape(GL2 gl, GLU glu, int slices, int stacks){
        Circle cap = new Circle(radius);
        Tube tube = new Tube(radius, height);
        gl.glPushMatrix();
            translate(gl);
            gl.glPushMatrix();
                rotate(gl);
                gl.glPushMatrix();
                    scale(gl);
                    //left cap
                    gl.glPushMatrix();
                        gl.glTranslated(0, 0, -height/2);
                        cap.drawShape(gl, slices, 0.2, 0.2, 0.2);
                    gl.glPopMatrix();
                    //right cap
                    gl.glPushMatrix();
                        gl.glTranslated(0, 0, height/2);
                        cap.drawShape(gl, slices, 0.2, 0.2, 0.2);
                    gl.glPopMatrix();
                    //tube
                    gl.glPushMatrix();
                        gl.glTranslated(0, 0, -height/2); // move center to local 0, 0, 0
                        tube.drawShape(gl, glu);
                    gl.glPopMatrix();
                gl.glPopMatrix();
            gl.glPopMatrix();
        gl.glPopMatrix();
    }
}
