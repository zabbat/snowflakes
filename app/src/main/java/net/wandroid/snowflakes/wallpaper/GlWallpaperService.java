package net.wandroid.snowflakes.wallpaper;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

/** class for wallpaper service with OpenGl calls*/
public abstract class GlWallpaperService extends WallpaperService {

    /**
     * Engine class for OpenGl.
     * Wraps calls to the OpenGlSurface
     */
    public class GlWallpaperEngine extends Engine {
        private GlWallpaperSurface mGlWallpaperSurface;
        private boolean mIsRendererSet = false;

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            mGlWallpaperSurface = new GlWallpaperSurface(GlWallpaperService.this);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            if (!mIsRendererSet) {
                return;
            }
            // make sure that no rendering is done if the surface is not visible
            if (visible) {
                mGlWallpaperSurface.onResume();
            } else {
                mGlWallpaperSurface.onPause();
            }
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            mGlWallpaperSurface.onDestroy();
        }

        public void setRenderer(GLSurfaceView.Renderer renderer) {
            mGlWallpaperSurface.setRenderer(renderer);
            mIsRendererSet = true;
        }

        public void setEglContextClientVersion(int version) {
            mGlWallpaperSurface.setEGLContextClientVersion(version);
        }

        /**
         * GlSurfaceView class
         */
        public class GlWallpaperSurface extends GLSurfaceView {

            public GlWallpaperSurface(Context context) {
                super(context);
            }

            @Override
            public SurfaceHolder getHolder() {
                // return the engines surface holder
                return getSurfaceHolder();
            }

            public void onDestroy() {
                super.onDetachedFromWindow();
            }
        }

    }


}
