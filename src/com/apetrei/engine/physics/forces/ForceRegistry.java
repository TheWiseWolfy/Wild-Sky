package com.apetrei.engine.physics.forces;

import com.apetrei.engine.components.TransformComponent;
import com.apetrei.engine.physics.rigidbody.Rigidbody2D;


import java.util.ArrayList;
import java.util.List;


public class ForceRegistry {
    private List<ForceRegistration> registry;

    public ForceRegistry() {
        this.registry = new ArrayList<>();
    }

    public void add(Rigidbody2D rb, ForceGenerator fg) {
        ForceRegistration fr = new ForceRegistration(fg, rb);
        registry.add(fr);
    }

    public void remove(Rigidbody2D rb, ForceGenerator fg) {
        ForceRegistration fr = new ForceRegistration(fg, rb);
        registry.remove(fr);
    }

    public void clear() {
        registry.clear();
    }

    public void updateForces(float dt) {
        for (ForceRegistration fr : registry) {
            fr.fg.updateForce(fr.rb, dt);
        }
    }

    public void zeroForces() {
        for (ForceRegistration fr : registry) {
            // TODO: IMPLEMENT ME
            //fr.rb.zeroForces();
        }
    }
}