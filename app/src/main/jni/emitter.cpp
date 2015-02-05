#include "net_wandroid_snowflakes_wallpaper_particles_EmitterNative.h"

#include "particle.h"

#include <GLES2/gl2.h>

Emitter g_emitter;

JNIEXPORT void JNICALL
Java_net_wandroid_snowflakes_wallpaper_particles_EmitterNative_onDrawFrame
  (JNIEnv *env, jobject thisObj, jint programId,jint a_position,jfloat delta){
    glClearColor(0.0f,0.0f,0.0f,1.0f);
    glClear(GL_COLOR_BUFFER_BIT);
    glUseProgram(programId);
    glEnableVertexAttribArray(a_position);
    glVertexAttribPointer(a_position, 2, GL_FLOAT, false, 0, g_emitter.getParticles());
    glDrawArrays(GL_POINTS, 0, Emitter::NR_PARTICLES);
    g_emitter.updateParticles(delta);
}

JNIEXPORT void JNICALL
Java_net_wandroid_snowflakes_wallpaper_particles_EmitterNative_onSurfaceCreated
  (JNIEnv *env, jobject thisObj){
    g_emitter.initParticles();
}