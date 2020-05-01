package com.jake.main.main.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.jake.main.main.MyGame;
import com.jake.main.main.Screens.PlayScreen;
import com.jake.main.main.TileObjects.Boulder;
import com.jake.main.main.TileObjects.Wall;

public class B2DWorldCreator {

    public B2DWorldCreator(PlayScreen screen)
    {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        // create wall bodies/fixtures
        for(MapObject object: map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MyGame.PPM, (rect.getY() + rect.getHeight() / 2) / MyGame.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / MyGame.PPM, rect.getHeight() / 2 / MyGame.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        /*for(MapObject object: map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MyGame.PPM, (rect.getY() + rect.getHeight() / 2) / MyGame.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / MyGame.PPM, rect.getHeight() / 2 / MyGame.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);


        }

         */

        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class))
        {
            new Wall(screen, object);
        }
        //create boulder bodies/fixtures
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class))
        {
            new Boulder(screen, object);
        }

    }
}
