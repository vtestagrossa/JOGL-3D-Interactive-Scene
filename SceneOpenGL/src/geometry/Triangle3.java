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
 * Class Triangle3 -
 * A tetrahedron shape that uses a triangle strip and a line loop for the outline. Shape is based on the base
 * and height passed to it. It can be balanced or unbalanced, depending on the parameters passed to it.
 * 
 * drawShape() - Just draws a triangle3 using the gltriangle option in begin. Traces the outline as well.
 */
public class Triangle3 extends TwoDimensionalShape {
    private double base, height;
    
    public Triangle3(){
        this(1, 1);
    }
    public Triangle3(double base, double height) {
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
        drawShape(gl, glu, 0.2, 0.2, 0.2);
    }
    public void drawShape(GL2 gl, GLU glu, double red, double green, double blue){
        gl.glPushMatrix();
            gl.glColor3d(red, green, blue);
            gl.glTranslated(translateX, translateY, translateZ);
            gl.glPushMatrix();
                rotate(gl);
                gl.glPushMatrix();
                    scale(gl);
                    gl.glBegin(GL2.GL_TRIANGLE_STRIP);
                        gl.glVertex3d(0, (height/2), 0); //top
                        gl.glVertex3d(0, -(height/2), (base/2)); //bottom front
                        gl.glVertex3d((base/2), -(height/2), -(base/2)); //right back
                        gl.glVertex3d(0, (height/2), 0); //top
                        gl.glVertex3d(-(base/2), -(height/2), -(base/2)); //left back
                        gl.glVertex3d((base/2), -(height/2), -(base/2)); //right back
                        gl.glVertex3d(0, -(height/2), (base/2)); //bottom front
                        gl.glVertex3d(-(base/2), -(height/2), -(base/2)); //left back
                        gl.glVertex3d(0, (height/2), 0); //top
                    gl.glEnd();
                    
                    
                    gl.glColor3d(1, 1, 1);
                    gl.glBegin(GL2.GL_LINE_LOOP);
                        gl.glVertex3d(0, (height/2), 0); //top
                        gl.glVertex3d(0, -(height/2), (base/2)); //bottom front
                        gl.glVertex3d((base/2), -(height/2), -(base/2)); //right back
                        gl.glVertex3d(0, (height/2), 0); //top
                        gl.glVertex3d(-(base/2), -(height/2), -(base/2)); //left back
                        gl.glVertex3d((base/2), -(height/2), -(base/2)); //right back
                        gl.glVertex3d(0, -(height/2), (base/2)); //bottom front
                        gl.glVertex3d(-(base/2), -(height/2), -(base/2)); //left back
                    gl.glEnd();
                gl.glPopMatrix();
            gl.glPopMatrix();
        gl.glPopMatrix();
    }
}
