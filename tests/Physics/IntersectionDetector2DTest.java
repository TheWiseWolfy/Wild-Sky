package Physics;

import com.apetrei.engine.physics.rigidbody.IntersectionDetector2D;
import com.apetrei.misc.ConvexPolygon2D;
import com.apetrei.misc.Line;
import com.apetrei.misc.Vector2;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static junit.framework.TestCase.*;

public class IntersectionDetector2DTest  {





    @Test
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

    @Test
    public void SteliIsStupid_Test(){

        Vector2[] waka = {
                new Vector2(0, 0),
                new Vector2(200, 200),
                new Vector2(300, 400),
                new Vector2(100, 600)
        };
        ConvexPolygon2D wa = new ConvexPolygon2D(waka);

       // parent.getGameContainer().getRenderer().drawPoligon(wa);

        int x =-180;
        int y =0;
        Vector2[] waka2 = {
                new Vector2(370+x, 400+y),
                new Vector2(600+x, 500+y),
                new Vector2(620+x, 740+y),
                new Vector2(310+x, 720+y)
        };
        ConvexPolygon2D wa2 = new ConvexPolygon2D(waka2);

       // parent.getGameContainer().getRenderer().drawPoligon(wa2);

      //  Vector2[] waka3 = IntersectionDetector2D.GetIntersectionOfPolygons(wa,wa2);
        //ConvexPolygon2D wa3 = new ConvexPolygon2D( waka3 );

       // parent.getGameContainer().getRenderer().drawPoligon(wa3, Color.RED);

    }


}
