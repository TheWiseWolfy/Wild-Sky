package com.apetrei.engine.physics.primitives.colliders;

import com.apetrei.engine.components.Collider2D;
import com.apetrei.engine.components.Rigidbody2D;
import com.apetrei.misc.ExtraMath;
import com.apetrei.misc.Vector2;

import static com.apetrei.misc.ExtraMath.compare;

/*!
 * Deprecated
 */
public class Box2DCollider extends Collider2D {

    private Vector2 size = new Vector2();
    private Vector2 halfSize = new Vector2();

    public Box2DCollider(Vector2 size) {
        this.size = size;
        this.halfSize = new Vector2(size).mul(0.5f);
    }

    public Box2DCollider(Vector2 min, Vector2 max) {
        this.size = new Vector2(max).sub(min);
        this.halfSize = new Vector2(size).mul(0.5f);
    }

    //____________________________GETTERS________________________
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

        boolean aligned = compare(rigidbody.getRotation(),0);
        if (!aligned  ) {
            for (Vector2 vert : vertices) {
                ExtraMath.rotateVec(vert, this.getRidigbody2D().getRotation(), this.getRidigbody2D().getPosition());
            }
        }

        return vertices;
    }

    public Vector2 getSize() {
        return size;
    }

    public Vector2 getHalfSize() {
        return halfSize;
    }

    public Vector2 getMin() {
        return new Vector2(this.rigidbody.getPosition()).sub(this.halfSize);
    }

    public Vector2 getMax() {
        return new Vector2(this.rigidbody.getPosition()).add(this.halfSize);
    }

    public Rigidbody2D getRidigbody2D() {
        return rigidbody;
    }

}
