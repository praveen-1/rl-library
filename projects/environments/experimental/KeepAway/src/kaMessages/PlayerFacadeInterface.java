package kaMessages;

import java.util.Vector;

import generalGameCode.Vector2D;

public interface PlayerFacadeInterface {
Vector2D Pos();
Vector2D Heading();
Vector2D Scale();
Vector2D SteeringForce();
Vector<Vector2D> getVertices();
Vector2D Side();
}
