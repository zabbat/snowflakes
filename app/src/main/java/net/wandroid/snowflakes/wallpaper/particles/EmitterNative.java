package net.wandroid.snowflakes.wallpaper.particles;


public class EmitterNative {
    static{
        System.loadLibrary("app");
    }

    /**
     * Called each frame. Should be called in the OpenGl onDrawFrame
     * @param programId the opengl program to be used
     * @param a_position the position attribute location
     * @param delta time in seconds sine last call to anDrawFrame
     */
    public native void onDrawFrame(int programId,int a_position,float delta);

    /**
     * Called to initiate the emitter. shoold be called in the OpenGl onSurfaceCreated
     */
    public native void onSurfaceCreated();

}
