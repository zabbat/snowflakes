precision mediump float;

attribute vec3 a_position;

void main(){
    gl_PointSize=10.0;
    gl_Position=vec4( a_position.x, a_position.y,0.0,1.0);
}