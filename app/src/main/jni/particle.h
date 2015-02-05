#ifndef _PARTICLE_H_
#define _PARTICLE_H_

#include <random>

struct Particle{
        float x;
        float y;
};

class Emitter{
    private:

        const float SNOW_MAX_POS = 2.0f;
        const float SNOW_MIN_POS = -2.0f;
        const float SNOW_Y_START_OFFSET=3.0f;
        const float SNOW_Y_SPEED = 0.3f;
        const float PI = 3.14f;
        const float SCREEN_LOWER_BOUND = -1.0f;

        Particle* _pParticles;

        std::uniform_real_distribution<float> distribution;
        std::default_random_engine randomEngine;

        float _totalTime; // total time since program started

        float getWindX(float y);

    public:

        const static int NR_PARTICLES = 60;

        Emitter(){
            std::random_device rd;
            randomEngine = std::default_random_engine(rd());
            distribution = std::uniform_real_distribution<float>(SNOW_MIN_POS, SNOW_MAX_POS);
            _pParticles = new Particle[NR_PARTICLES];
        }

        virtual ~Emitter(){
            delete[] _pParticles;
        }

        void initParticles();

        void updateParticles(float delta);

        Particle *getParticles(){
            return _pParticles;
        }

};

#endif