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
 * Class Cube - 
 * A cube has a volume and an edge length, which is represented by edge. setEdge() is validated to ensure the edge is always greater 
 * than 0, and is used in the constructors as well, so the volume calculation is called every time setEdge() is called, keeping it
 * updated.
 */
public class Cube extends ThreeDimensionalShape {
    private double edge;
    public Cube(){
        this(1);
    }
    public Cube(double edge){
        super(Math.pow(edge, 3), "Cube");
        setEdge(edge);
    }
    private void setVolume(double edge){
        volume = Math.pow(edge, 3);
    }
    @Override
    public double getVolume() {
        return volume;
    }
    public double getEdge() {
        return edge;
    }
    public void setEdge(double edge) throws IllegalArgumentException {
        if (edge <= 0){
            String err = "ERROR: Edge must be positive.";
            throw new IllegalArgumentException(err);
        }
        else{
            this.edge = edge;
            setVolume(this.edge);
        }
    }
    /*
     * This part of the cube function comes from the TextureDemo example in CMSC 405 textbook, Introduction to Computer 
     * Graphics - David J. Eck. I already had a square subclass of rectangle that drew a line loop around the edges and
     * I thought that it would work well without having to retrace the line loop like I did with triangle3 (which used
     * a triangle strip, instead)
     */
    public void drawShape(GL2 gl, GLU glu){
        geometry.Square square = new Square(edge);
        gl.glPushMatrix();
            translate(gl);
            gl.glPushMatrix();
                rotate(gl);
                gl.glPushMatrix();
                    scale(gl);
                    gl.glPushMatrix();
                        gl.glTranslated(0,0,edge/2);
                        square.drawShape(gl, 0, 0.5, 0);
                    gl.glPopMatrix();

                    gl.glPushMatrix();
                        gl.glRotatef(90,0,1,0);
                        gl.glTranslated(0,0,edge/2);
                        square.drawShape(gl, 0,0.5,0);
                    gl.glPopMatrix();

                    gl.glPushMatrix();
                        gl.glRotatef(180,0,1,0);
                        gl.glTranslated(0,0,edge/2);
                        square.drawShape(gl, 0, 0.5, 0);
                    gl.glPopMatrix();

                    gl.glPushMatrix();
                        gl.glRotatef(270,0,1,0);
                        gl.glTranslated(0,0,edge/2);
                        square.drawShape(gl, 0, 0.5, 0);
                    gl.glPopMatrix();

                    
                    gl.glPushMatrix();
                        gl.glRotatef(90, -1, 0,0);
                        gl.glTranslated(0,0,edge/2);
                        square.drawShape(gl, 0, 0.5, 0);
                    gl.glPopMatrix();

                    gl.glPushMatrix();
                        gl.glRotatef(-90, -1, 0,0);
                        gl.glTranslated(0,0,edge/2);
                        square.drawShape(gl, 0, 0.5, 0);
                        gl.glPopMatrix();
                gl.glPopMatrix();
            gl.glPopMatrix();
        gl.glPopMatrix();
    }
}
