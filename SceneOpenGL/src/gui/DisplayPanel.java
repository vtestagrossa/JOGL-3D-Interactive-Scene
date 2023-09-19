package gui;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.*;
import com.jogamp.opengl.glu.GLU;

import java.awt.event.*;
/*
 * Vincent Testagrossa
 * CMSC 405 - JOGL Project
 * 
 * GLJPanel for drawing the shape passed to it with OpenGL. Stores scene assets in an arraylist of Shapes, which are generated
 * in a separate method (using some random numbers). 
 * 
 * keyPressed() -
 * Keybindings are set in the KeyListener keyPressed() method. A looks left, D looks right, W moves forward, and S moves backwards.
 * I looks up and K looks down.
 * 
 * update() -
 * uses playerState currentState set by keyPressed() or keyReleased() (for idle) to control the translation and rotation of the scene
 * in reference to the player/camera.
 * 
 * Scene includes spheres for leaves, tubes for the trunks of trees, green cubes for
 * grass, and unbalanced tetrahedrons (Triangle3) for rocks. A log is made out of a cylinder object and transformed to a specific
 * location, while the other objects are more or less randomly placed (within bounds);
 */
import java.util.ArrayList;

public class DisplayPanel extends GLJPanel implements GLEventListener, KeyListener{
    private GLU glu; //used for the perspective view as well as the draw methods that need it for quadric shapes.
    private ArrayList<geometry.Shape> sceneAssets; //stores all of the scene assets to be drawn in a loop
    private double rotX = 0, rotY = 0, rotZ = 0, translateX = 0, translateY = 0, translateZ = 0, //rotation and translation of the scene
                    rotSpd = 9, moveSpd = 0.5; //movement/rotation speed for the camera/scene
    
    //enum for the state machine code
    enum playerState {
        LOOK_LEFT, LOOK_RIGHT, LOOK_UP, LOOK_DOWN, MOVE_FWD, MOVE_BCKWD, IDLE
    }

    playerState currState = playerState.IDLE;
    public DisplayPanel(){
        super(new GLCapabilities(null));
        setSize(800, 600); //need to use setSize again to force the size.
        addGLEventListener(this);
        requestFocus();
        addKeyListener(this);
        glu = new GLU();
        sceneAssets = initializeScene();
    }
    @Override
    public void display(GLAutoDrawable arg0) {
        final GL2 gl = arg0.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        update(gl); //pass the GL2 to the update function to be called in each shape's drawShape() method
    }

    @Override
    public void dispose(GLAutoDrawable arg0) {
        
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        //initializes the base state for the GL2 object. Enabled depth test and smooth shading.
        gl.glClearColor(0f, 0f, 0.5f, 0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glDepthFunc(GL2.GL_LEQUAL);
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
        gl.glShadeModel(GL2.GL_SMOOTH);
        gl.glLineWidth(2);
    }

    //A lot of this was in the examples in the books. Changes the aspect ratio and resets the camera bounds to match the window
    //if the window is resized. It shouldn't happen, but this should prevent any distortion if it does inadvertently.
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();  // get the OpenGL 2 graphics context
 
      if (height == 0) height = 1;   // prevent divide by zero
      float aspect = (float)width / height;
 
      // Set the view port (display area) to cover the entire window
      gl.glViewport(0, 0, width, height);
 
      // Setup perspective projection, with aspect ratio matches viewport
      gl.glMatrixMode(GL2.GL_PROJECTION);  // choose projection matrix
      gl.glLoadIdentity();             // reset projection matrix
      glu.gluPerspective(60.0, aspect, 0.1f, 100.0f); // fovy, aspect, zNear, zFar
 
      // Enable the model-view transform
      gl.glMatrixMode(GL2.GL_MODELVIEW);
      gl.glLoadIdentity(); // reset
    }

    //draw the scene, get the player control state, and repaint the scene (creating the loop).
    private void update(GL2 gl){
        drawScene(gl, glu);
        playerControls();
        repaint();
    }
    @Override
    public void keyPressed(KeyEvent e) {
        char keyPress = e.getKeyChar();
        //Would be better with a state machine. 
        switch (keyPress){
            case 'a':
                currState = playerState.LOOK_LEFT;
                //rotate left
                break;
            case 'd':
                currState = playerState.LOOK_RIGHT;
                //rotate right
                break;
            case 'w':
                currState = playerState.MOVE_FWD;
                //move forwards
                break;
            case 's':
                currState = playerState.MOVE_BCKWD;
                //Move backwards
                break;
            case 'i':
                currState = playerState.LOOK_DOWN;
                break;
            case 'k':
                currState = playerState.LOOK_UP;
                break;
            default:
                break;
        }
    }
    private void playerControls(){
        //simple controls using a "state machine". Only one control available at a time
        switch(currState){
            case IDLE:
                break;
            case LOOK_DOWN:
                rotX -= rotSpd;
                break;
            case LOOK_LEFT:
                rotY -= rotSpd;
                break;
            case LOOK_RIGHT:
                rotY += rotSpd;
                break;
            case LOOK_UP:
                rotX += rotSpd;
                break;
            /*
             * Uses https://gamedev.stackexchange.com/questions/101793/move-towards-view-direction-opengl example
             * to find the forward/backwards direction and move along it.
             */
            case MOVE_FWD:
                translateX -= Math.sin(toRadians(rotY)) * moveSpd;
                translateZ += Math.cos(toRadians(rotY)) * moveSpd;;
                break;
            case MOVE_BCKWD:
                translateX += Math.sin(toRadians(rotY)) * moveSpd;
                translateZ -= Math.cos(toRadians(rotY)) * moveSpd;;
                break;
            default:
                break;
            
        }
        //Not necessarily needed for toRadians, but prevents the double from getting too high/low
        if (rotY > 360){
            rotY = 0;
        }
        else if (rotY < 0){
            rotY = 360;
        }
    }
    private double toRadians(double degrees){
        return degrees * Math.PI/180;
    }
    //A key has been released. A more elegant solution would be to test for multiple key presses and have the ability to have two keys
    //pressed (currentKey and lastKey), but a lot of extra branching would be introduced. For the purposes of this assignment,
    //basic movement is only in one direction at a time.
    @Override
    public void keyReleased(KeyEvent e) {
        currState = playerState.IDLE;
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }


    private void drawScene(GL2 gl, GLU glu){
        gl.glPushMatrix();
            gl.glRotated(rotX, 1, 0, 0); //rotation in the x plane
            gl.glRotated(rotY, 0, 1, 0); //rotation in the y plane
            gl.glRotated(rotZ, 0, 0, 1); //rotation in the z plane
            gl.glTranslated(translateX, translateY, translateZ); //translates the scene in reference to the player/camera
            //draw the scenery
            for (geometry.Shape asset : sceneAssets){
                asset.drawShape(gl, glu);
            }
        gl.glPopMatrix();
    }

    /*
     * Generates all of the "scene" objects that will be rotated and translated by the playerState
     */
    private ArrayList<geometry.Shape> initializeScene(){
        ArrayList<geometry.Shape> assets = new ArrayList<geometry.Shape>();

        //generates all of the cubes for the ground: a 50x50 grid at y = -3.
        for (int rows = 0; rows < 50; rows++){
            for (int columns = 0; columns < 50; columns++){
                double x = (double)columns - 25;
                double z = (double)rows - 25;
                assets.add(new geometry.Cube());
                //multiplies rows times total columns to get the current row, then adds the column value.
                //to access the item that was just added to the list and set the translation.
                assets.get((rows * 50) + columns).setTranslation(x, -3, z);
            }
        }


        /*
         * Generates 15 trees randomly spaced. Trees are made from brown cylinder tubes and 3 deformed and translated spheres.
         * Places them between -25 and 25 on both the Z and X axis.
         */
        ArrayList<geometry.Shape> trees = new ArrayList<geometry.Shape>();
        for (int i = 0; i < 15; i++){
            int min = -25, max = 25, range = max - min;
            double randX = (Math.random() * range) + min, randZ = (Math.random() * range) + min; //random X/Z placement within the range
            
            //leaves of the tree
            trees.add(new geometry.Sphere(1.3));
            trees.get(i * 4).setTranslation(randX, 4, randZ);
            trees.add(new geometry.Sphere(1.6));
            trees.get(i * 4 + 1).setScale(1, 0.5, 1);
            trees.get(i * 4 + 1).setTranslation(randX, 4, randZ);
            trees.add(new geometry.Sphere(1.4));
            trees.get(i * 4 + 2).setTranslation(randX, 3, randZ);
    

            //trunk of the tree
            trees.add(new geometry.Tube(0.5, 10));
            trees.get(i * 4 + 3).setTranslation(randX, 3, randZ);
            trees.get(i * 4 + 3).setRotation(90, 0, 0);
        }

        /*
         * Randomly places rocks (Triangle3) between 20 and -20 on the X and Z axes.
         */
        ArrayList<geometry.Shape> rocks = new ArrayList<geometry.Shape>();
        for (int i = 0; i < 23; i++){
            int max = 20, min = -20, range = max - min;
            double randX = (Math.random() * range) + min, randZ = (Math.random() * range) + min;
            rocks.add(new geometry.Triangle3(2, 1));
            rocks.get(i).setRotation(0, randX * randZ, 0);
            rocks.get(i).setTranslation(randX, -2, randZ);
        }


        //Adds a cylinder, sets the radius so it's thinner, then translates it into the ground so it's sticking out
        //and rotates it so it's askew.
        geometry.Cylinder log = new geometry.Cylinder();
        log.setRadius(0.5);
        log.setTranslation(-10, -2.6, 10);
        log.setRotation(0, 67, 0);
        //combines the generated objects into the assets ArrayList and returns it.
        assets.addAll(trees);
        assets.addAll(rocks);
        assets.add(log);
        return assets;
    }
}
