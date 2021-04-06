package com.apetrei.engine.physics.forces;

import com.apetrei.engine.components.TransformComponent;

//TODO This will need to be implemented later for wind
public interface ForceGenerator {
    void updateForce(TransformComponent body, float dt);
}
