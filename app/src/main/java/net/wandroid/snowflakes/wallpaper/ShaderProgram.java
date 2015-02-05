package net.wandroid.snowflakes.wallpaper;

import static android.opengl.GLES20.GL_COMPILE_STATUS;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_LINK_STATUS;
import static android.opengl.GLES20.GL_TRUE;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glCompileShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetProgramInfoLog;
import static android.opengl.GLES20.glGetProgramiv;
import static android.opengl.GLES20.glGetShaderInfoLog;
import static android.opengl.GLES20.glGetShaderiv;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glLinkProgram;
import static android.opengl.GLES20.glShaderSource;

/**
 * Class that represent shader programs
 */
public class ShaderProgram {

    private int mProgramId;

    /**
     * compiles and links a shader program from a vertex and fragment shader
     * @param vertexString the vertex shader as a string
     * @param fragmentShader the fragment shader as a string
     */
    public void load(String vertexString,String fragmentShader){
        int vertexId=loadVertexShader(vertexString);
        int fragmentId = loadFragmentShader(fragmentShader);

        mProgramId= createProgram(vertexId,fragmentId);

    }

    /**
     * Links the shader program
     * @param vertexId the id of the vertex shader to use
     * @param fragmentId the id of the fragment shader to use
     * @return the program
     */
    private int createProgram(int vertexId, int fragmentId) {
        int id = glCreateProgram();
        glAttachShader(id,vertexId);
        glAttachShader(id,fragmentId);
        glLinkProgram(id);
        int[] i = new int[1];
        glGetProgramiv(id,GL_LINK_STATUS,i,0);
        if(i[0]!=GL_TRUE){
            String error = glGetProgramInfoLog(id);
            throw new ShaderException(error);
        }
        return id;
    }

    /**
     * Compiles a vertex shader
     * @param vertexString the vertex shader as a string
     * @return the vertex shader id
     */
    private int loadVertexShader(String vertexString) {
        int id= glCreateShader(GL_VERTEX_SHADER);

        glShaderSource(id,vertexString);
        glCompileShader(id);
        int[] i=new int[1];
        glGetShaderiv(id,GL_COMPILE_STATUS,i,0);
        if(i[0]!=GL_TRUE){
            String error= glGetShaderInfoLog(id);
            throw new ShaderException(error);
        }

        return id;
    }

    /**
     * Compiles a fragment shader
     * @param fragmentString the fragment shader as a string
     * @return the fragment shader id
     */
    private int loadFragmentShader(String fragmentString) {
        int id= glCreateShader(GL_FRAGMENT_SHADER);

        glShaderSource(id,fragmentString);
        glCompileShader(id);
        int[] i=new int[1];
        glGetShaderiv(id,GL_COMPILE_STATUS,i,0);
        if(i[0]!=GL_TRUE){
            String error= glGetShaderInfoLog(id);
            throw new ShaderException(error);
        }

        return id;
    }

    /**
     * returns the id of the program
     * @return the program id
     */
    public int getProgramId() {
        return mProgramId;
    }

    /**
     * Finds the location of a attribute or uniform.
     * An attribute location must start with 'a_' and a uniform with 'u_'
     * Useful for debugging.
     * @param name name of the location to find. must start with a_ or u_ prefix
     * @return the location or -1 if none could be found
     */
    public int find(String name) {
        int position=-1;
        if(name.startsWith("a_")) {
            position = glGetAttribLocation(getProgramId(), name);
        }else if(name.startsWith("u_")){
            position = glGetUniformLocation(getProgramId(), name);
        }
        return position;
    }

    /**
     * Runtime exception when handling shaders.
     */
    public static class ShaderException extends RuntimeException{
        public ShaderException(String detailMessage) {
            super(detailMessage);
        }
    }

}
