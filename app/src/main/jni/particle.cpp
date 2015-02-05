#include "particle.h"

#include <random>
#include <cmath>

using namespace std;

void Emitter::initParticles(){

    for(int i=0;i<NR_PARTICLES;i++){
        _pParticles[i].x=distribution(randomEngine);
        _pParticles[i].y=distribution(randomEngine)+SNOW_Y_START_OFFSET;
    }
}

void Emitter::updateParticles(float delta){
    _totalTime+=delta;
    for(int i=0;i<NR_PARTICLES;i++){
        float y=_pParticles[i].y;
        y-=(SNOW_Y_SPEED*delta);
        if(y<SCREEN_LOWER_BOUND){
            _pParticles[i].x=distribution(randomEngine);
            y=distribution(randomEngine)+SNOW_Y_START_OFFSET;
        }
        _pParticles[i].y=y;
        _pParticles[i].x=_pParticles[i].x+getWindX(y);
    }
}

float Emitter::getWindX(float y){
    // individual flake movement (dependent on flake y position)
    float flakeMove=(0.005f*cos(PI*4*y));
    // all snow flakes movement (dependent on total time).
    float globalMove=(0.0025f*cos(_totalTime*0.5f));
    return flakeMove + globalMove;
}
