package com.apetrei.engine.physics.forces;

import com.apetrei.engine.physics.rigidbody.Rigidbody2D;

//This class exists to pair up force generators and rigidbodies.
public class ForceRegistration {
    public ForceGenerator fg;
    public Rigidbody2D rb;

    public ForceRegistration(ForceGenerator fg, Rigidbody2D rb) {
        this.fg = fg;
        this.rb = rb;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other.getClass() != ForceRegistration.class) return false;

        ForceRegistration fr = (ForceRegistration)other;
        return fr.rb == this.rb && fr.fg == this.fg;
    }
}