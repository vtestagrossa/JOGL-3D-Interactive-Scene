package geometry;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

/* 
 * Vincent Testagrossa
 * CMSC 405 - JOGL Project
 * 
 * A lot of the class is reused from my CMSC 335 class, with a lot of changes made to the order of glPopMatrix/glPushMatrix methods,
 * as well as removing the call to the parent draw method. Order needed to be specific per class since a lot of the shapes use different
 * methods for calculating vertices/etc. Unused in this project.
 * 
 * Class Torus - 
 * A torus has a major radius and a minor radius. The methods setMajorRadius() and setMinorRadius() have to validate both for numbers
 * greater than 0 and that majorRadius is greater than minorRadius, since they are positional within the formula. 
 * 
 * Added a Draw method that can be called by the DisplayPanel to draw the Torus.
 */
public class Torus extends ThreeDimensionalShape {
    private double majorRadius, minorRadius;

    public Torus(){
        this(2, 1);
    }
    public Torus(double majorRadius, double minorRadius){
        super((Math.PI * Math.pow(minorRadius, 2)) * (2.0 * Math.PI * majorRadius), "Torus");
        setMajorRadius(majorRadius);
        setMinorRadius(minorRadius);
    }

    private void setVolume(double majorRadius, double minorRadius){
        volume = (Math.PI * Math.pow(minorRadius, 2)) * (2.0 * Math.PI * majorRadius);
    }
    @Override
    public double getVolume() {
        return volume;
    }
    public double getMajorRadius() {
        return majorRadius;
    }
    public double getMinorRadius() {
        return minorRadius;
    }
    public void setMajorRadius(double majorRadius) throws IllegalArgumentException {
        if (majorRadius <= 0){
            String err = "ERROR: Major radius must be positive.";
            throw new IllegalArgumentException(err);
        }
        else if (majorRadius <= minorRadius){
            String err = "ERROR: Major radius must be larger than minor radius";
            throw new IllegalArgumentException(err);
        }
        else{
            this.majorRadius = majorRadius;
            setVolume(this.majorRadius, this.minorRadius);
        }
    }
    public void setMinorRadius(double minorRadius) throws IllegalArgumentException {
        if (minorRadius <= 0){
            String err = "ERROR: Minor radius must be positive.";
            throw new IllegalArgumentException(err);
        }
        else if (majorRadius <= minorRadius){
            String err = "ERROR: Major radius must be larger than minor radius";
            throw new IllegalArgumentException(err);
        }
        else{
            this.minorRadius = minorRadius;
            setVolume(this.majorRadius, this.minorRadius);
        }
    }

    /*
     * Calculates the position of each vertex and draws a quad strip, based on the number of slices and rings (the strips). Alternates
     * colors on the rings to show depth, since the panel doesn't have lighting enabled. Most of this method was provided as part of 
     * the demo for TexturedShapes from the CMSC 405 textbook, Introduction to Computer Graphics - David J. Eck. The scaling, coloring
     * and some of the simplification is my own.
     */
    public void drawShape(GL2 gl, GLU glu){
        drawShape(gl, 64, 64);
    }
    public void drawShape(GL2 gl, int slices, int rings){
        gl.glPushMatrix();
            translate(gl);
            gl.glPushMatrix();
                rotate(gl);
                gl.glPushMatrix();
                    scale(gl);
                    double centerRadius = (minorRadius + majorRadius) / 2;
                    double tubeRadius = majorRadius - centerRadius;
                    for (int slice = 0; slice < slices; slice++){
                        if (slice % 2 ==0){
                            gl.glColor3d(0, 0.5, 0.7);
                        }else{
                            gl.glColor3d(0, 0.7, 0.5);
                        }
                        double s1 = 1.0/slices * slice;
                        double s2 = 1.0/slices * (slice + 1);
                        double centerCos1 = Math.cos(2*Math.PI*s1);
                        double centerSin1 = Math.sin(2*Math.PI*s1);
                        double centerCos2 = Math.cos(2*Math.PI*s2);
                        double centerSin2 = Math.sin(2*Math.PI*s2);
                        gl.glBegin(GL2.GL_QUAD_STRIP);
                        for (int ring = 0; ring <= rings; ring++){
                            double t = 1.0/rings * ring;
                            double cos = Math.cos(2*Math.PI*t - Math.PI);
                            double sin = Math.sin(2*Math.PI*t - Math.PI);
                            double x1 = centerCos1*(centerRadius + tubeRadius*cos);
                            double y1 = centerSin1*(centerRadius + tubeRadius*cos);
                            double z = sin*tubeRadius;
                            double x2 = centerCos2*(centerRadius + tubeRadius*cos);
                            double y2 = centerSin2*(centerRadius + tubeRadius*cos);
                            gl.glNormal3d(centerCos1*cos,centerSin1*cos,sin);
                            gl.glVertex3d(x1,y1,z);
                            gl.glNormal3d(centerCos2*cos, centerSin2*cos, sin);
                            gl.glVertex3d(x2, y2, z);
                        }
                        gl.glEnd();
                    }
                gl.glPopMatrix();
            gl.glPopMatrix();
        gl.glPopMatrix();
    }
}
