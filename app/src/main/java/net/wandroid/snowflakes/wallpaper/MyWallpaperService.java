package net.wandroid.snowflakes.wallpaper;

import android.opengl.GLSurfaceView;
import android.view.SurfaceHolder;


import net.wandroid.snowflakes.wallpaper.particles.EmitterNative;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.glViewport;


/**
 * Created by 23060996 on 12/22/14.
 */
public class MyWallpaperService extends GlWallpaperService {
    @Override
    public Engine onCreateEngine() {
        MyGlWallpaperEngine engine = new MyGlWallpaperEngine();
        return engine;
    }

    private class MyGlWallpaperEngine extends GlWallpaperEngine {
        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            setEglContextClientVersion(2);
            try {
                String vertex = loadShaderString("shaders/particle.vsh");
                String fragment = loadShaderString("shaders/particle.fsh");

                setRenderer(new MyWallpaperRenderer(vertex, fragment));

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        private String loadShaderString(String path) throws IOException {
            InputStream is = getAssets().open(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            is.close();
            return sb.toString();
        }
    }

    private class MyWallpaperRenderer implements GLSurfaceView.Renderer {

        private final ShaderProgram mShaderProgram;
        private final String mVertexString;
        private final String mFragmentString;
        private int a_position = -1;
        private long mTimeStamp = System.currentTimeMillis();
        private EmitterNative mEmitterNative=new EmitterNative();

        private MyWallpaperRenderer(String vertexString, String fragmentString) {
            mShaderProgram = new ShaderProgram();
            mVertexString = vertexString;
            this.mFragmentString = fragmentString;
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            mShaderProgram.load(mVertexString, mFragmentString);
            a_position =mShaderProgram.find("a_position");
            mEmitterNative.onSurfaceCreated();
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            glViewport(0, 0, width, height);
        }
        float a=0;
        @Override
        public void onDrawFrame(GL10 gl) {
            float delta = (System.currentTimeMillis() - mTimeStamp)/1000.0f;
            mEmitterNative.onDrawFrame(mShaderProgram.getProgramId(),a_position,delta);
            mTimeStamp=System.currentTimeMillis();
        }
    }

}
