package com.apetrei.engine.physics.primitives;

import com.apetrei.engine.components.TransformComponent;
import com.apetrei.misc.ExtraMath;
import com.apetrei.misc.Vector2;

import static com.apetrei.misc.ExtraMath.compare;

public class Box2D {

    private Vector2 size = new Vector2();
    private Vector2 halfSize = new Vector2();

    private TransformComponent transform = null;


    public Box2D() {
        this.halfSize = new Vector2(size).mul(0.5f);
    }

    public Box2D(Vector2 min, Vector2 max, TransformComponent _transform) {
        transform = _transform;
        this.size = new Vector2(max).sub(min);
        this.halfSize = new Vector2(size).mul(0.5f);
    }

    public Vector2 getMin() {
        return new Vector2(this.transform.getPosition()).sub(this.halfSize);
    }

    public Vector2 getMax() {
        return new Vector2(this.transform.getPosition()).add(this.halfSize);
    }

    public Vector2[] getVertices() {
        Vector2 min = getMin();
        Vector2 max = getMax();

        //Nota: ordinea in care le pui aici conteaza, fiind ca puctele sunt unite in ordine
        Vector2[] vertices = {
                new Vector2(min.x, min.y),
                new Vector2(max.x, min.y),
                new Vector2(max.x, max.y),
                new Vector2(min.x, max.y)
        };

        boolean aligned = compare(transform.getRotation(),0);
        if (!aligned  ) {
            for (Vector2 vert : vertices) {
                   ExtraMath.rotateVec(vert, this.getTransform().getRotation(), this.getTransform().getPosition());
            }
        }

        return vertices;
    }


    public TransformComponent getTransform() {
        return transform;
    }

}
