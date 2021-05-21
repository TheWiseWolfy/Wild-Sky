package Physics;

import com.apetrei.engine.physics.rigidbody.IntersectionDetector2D;
import com.apetrei.misc.ConvexPolygon2D;
import com.apetrei.misc.Line;
import com.apetrei.misc.Vector2;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.*;

public class IntersectionDetector2DTest  {

   //@Test
    public void GetIntersectionPoint_Test(){

        Vector2 A = new Vector2(10,10);
        Vector2 B = new Vector2(200,230);

        Line line1 = new Line(A,B);

        Vector2 A1 = new Vector2(0,200);
        Vector2 B1= new Vector2(100,0);

        Line line2 = new Line(A1,B1);
        Vector2 testVector= IntersectionDetector2D.GetIntersectionPoint(line1,line2);

       assertNotNull(testVector );
    }
}
