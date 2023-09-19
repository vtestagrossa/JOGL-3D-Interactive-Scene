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
 * Class Circle -
 * Circle introduces a radius field, which is needed for the area calculation. The overloaded constructor allows the radius to be
 * defined during instantiation, or the setRadius() method can be used. 
 * 
 * setRadius(double radius) - 
 * Does basic validation to ensure that the radius is greater than 0. Throws an IllegalArgumentException with a specific
 * error message if it's 0 or less.
 * 
 * drawShape() -
 * 
 * 
 */
public class Circle extends TwoDimensionalShape {
    
    private double radius; //additional field needed for a circle.
    
    public Circle(){
        this(1);
    }
    public Circle(double radius){
        super(Math.PI * Math.pow(radius, 2), "Circle");
        //sets the radius and then uses it to perform the area calculation
        setRadius(radius);
        setArea(radius);
    }
    //safe method to set the area.
    private void setArea(double radius){
        area = Math.PI * Math.pow(radius, 2);
    }
    @Override
    public double getArea() {
        return area;
    }


    public void setRadius(double radius) throws IllegalArgumentException{
        if (radius <= 0){ // checks for a valid radius
            String msg = "ERROR: Radius must be greater than 0.";
            throw new IllegalArgumentException(msg);
        }
        else{
            this.radius = radius;
            setArea(radius);
        }
    }
    public double getRadius() {
        return radius;
    }

    /*
     * Draws a circle from slices made of a Triangle Fan. Places points around the radius which are connected together by the 
     * fan, making a "circle". There's also a separate line loop that is drawn to highlight the edge.
     */
    public void drawShape(GL2 gl, GLU glu){
        drawShape(gl, 128, 0.7, 0.7, 0);
    }
    public void drawShape(GL2 gl, int slices, double red, double green, double blue){
        gl.glPushMatrix();
            translate(gl);
            gl.glPushMatrix();
                rotate(gl);
                gl.glPushMatrix();
                    scale(gl);
                    gl.glColor3d(red, green, blue);
                    gl.glBegin(GL2.GL_TRIANGLE_FAN);
                    double angle = 0;
                    double angleIncrement = 2 * Math.PI / slices;
                    for (int slice = 0; slice <= slices; slice++){
                        angle = slice * angleIncrement;
                        double x = radius * Math.cos(angle);
                        double y = radius * Math.sin(angle);
                        gl.glVertex2d(x,y);
                    }
                    gl.glEnd();

                    gl.glColor3d(1, 1, 1);
                    gl.glBegin(GL2.GL_LINE_LOOP);
                    angle = 0;
                    angleIncrement = 2 * Math.PI / slices;
                    for (int slice = 0; slice <= slices; slice++){
                        angle = slice * angleIncrement;
                        double x = radius * Math.cos(angle);
                        double y = radius * Math.sin(angle);
                        gl.glVertex2d(x,y);
                    }
                    gl.glEnd();
                gl.glPopMatrix();
            gl.glPopMatrix();
        gl.glPopMatrix();
    }
}
