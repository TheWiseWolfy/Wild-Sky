package Physics;

import com.apetrei.engine.physics.rigidbody.IntersectionDetector2D;
import com.apetrei.misc.Line;
import com.apetrei.misc.Vector2;
import junit.framework.Assert;
import org.junit.jupiter.api.Test;
import static junit.framework.TestCase.*;

public class IntersectionDetector2DTest  {

    @Test
    public void pointOnLine2D_Test(){

        Line line = new Line( new Vector2(), new Vector2(0,10));
        Vector2 point = new Vector2(0,5);

        assertTrue(IntersectionDetector2D.pointOnLine(point,line));

    }
    @Test
    void lineInAABB_Test() {
        Vector2 wakandaVector = new Vector2(5,13);
        wakandaVector.normalize();
        System.out.print(wakandaVector);
    }
}
