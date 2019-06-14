package com.nithinmuthukumar.conquest.Helpers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.esotericsoftware.kryo.Kryo;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Components.*;
import com.nithinmuthukumar.conquest.GameMap;
import com.nithinmuthukumar.conquest.Server.*;

import java.util.Arrays;
import java.util.Comparator;

import static com.nithinmuthukumar.conquest.Globals.*;


public class Utils {
    //compares entities by their z values so that they can be sorted for drawing
    public static Comparator<Entity> zyComparator = (e1, e2) -> {

        if (transformComp.get(e1).z == transformComp.get(e2).z) {
            return (int) Math.signum(transformComp.get(e2).getRenderY() - transformComp.get(e1).getRenderY());
        } else {
            return (int) Math.signum(transformComp.get(e1).z - transformComp.get(e2).z);
        }

    };

    //uses reflection to return the desired component
    public static Class<BaseComponent> getComponentClass(String name) {
        try {
            return ClassReflection.forName("com.nithinmuthukumar.conquest.Components." + name + "Component");
        } catch (ReflectionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void print(String file, String... message) {
        System.out.print(file + ": ");
        for (String s : message) {
            System.out.print(s + " ");

        }
        System.out.print("\n");

    }

    //joins an array into one string
    public static String joinArray(String[] strings) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : strings) {

            stringBuilder.append(s);

        }

        return stringBuilder.toString();

    }

    public static void print(float... message) {
        for (float i : message) {
            System.out.print(i + " ");


        }
        System.out.println();

    }

    //sets the user data of multiple fixtures to the entity
    //user data will always be the entity to which the body belongs
    public static void setUserData(Entity e) {
        for (Fixture f : bodyComp.get(e).body.getFixtureList()) {
            f.setUserData(e);
        }

    }

    public static float getTargetAngle(Vector2 p, Vector2 e) {
        return MathUtils.radiansToDegrees * MathUtils.atan2(e.y - p.y, e.x - p.x);
    }

    public static boolean inBounds(int lowerBound, int upperBound, float val) {
        return lowerBound < val && val < upperBound;

    }

    public static FileHandle[] listFiles(FileHandle f) {
        //List all files in a directory that arent .DS_Store
        //mac problem
        //also sorts it that pictures always come out the proper order
        FileHandle[] fileHandles = f.list(n -> !new FileHandle(n).extension().equals("DS_Store"));
        Arrays.sort(fileHandles, Comparator.comparing(FileHandle::name));
        return fileHandles;
    }



    //resizes a texture by taking its pixmap
    public static Texture resizeTexture(Texture texture, float newWidth, float newHeight) {
        Pixmap og = texture.getTextureData().consumePixmap();
        Pixmap new_ = new Pixmap(MathUtils.round(newWidth), MathUtils.round(newHeight), og.getFormat());
        new_.drawPixmap(og,
                0, 0, og.getWidth(), og.getHeight(),
                0, 0, new_.getWidth(), new_.getHeight()
        );


        return new Texture(new_);

    }

    //finds the nearest value that puts the it on the grid evenly
    public static int snapToGrid(GameMap gameMap, float x) {
        //finds the neares whole number to a multiple of the width which is done by rounding the value
        return MathUtils.round(gameMap.getTileWidth() * (MathUtils.ceil(x / gameMap.getTileWidth())));

    }

    //returns the camera coordinates of the point that is inputted in terms of the camera
    public static int screenToCameraY(float y) {

        return MathUtils.round(camera.position.y + Gdx.graphics.getHeight() / 2 - y);
    }

    public static int screenToCameraX(float x) {

        return MathUtils.round(camera.position.x - Gdx.graphics.getWidth() / 2 + x);
    }

    //
    //gets a copy of the icons pixmap
    public static Pixmap getPixmap(TextureRegion icon) {
        Pixmap pixmap = new Pixmap(icon.getRegionWidth(), icon.getRegionHeight(), Pixmap.Format.RGBA8888);
        for (int x = 0; x < icon.getRegionWidth(); x++) {
            for (int y = 0; y < icon.getRegionHeight(); y++) {
                int colorInt = pixmap.getPixel(icon.getRegionX() + x, icon.getRegionY() + y);
                pixmap.drawPixel(x, y, colorInt);

            }
        }
        return pixmap;
    }

    //finds a target for the entity to follow based on its ai and distance from enemies
    //returns true if a target is found
    public static boolean findTarget(AIComponent ai, TransformComponent transform, TargetComponent target, Entity entity) {
        //loops through the preferences so buildings could be first then entities with velocities
        for (Family f : ai.targetOrder) {
            Entity minTarget = findMinTarget(f, entity, transform);
            if (minTarget == null)
                return false;

            //checks if the closest target is within range and if it is set all the values
            if (transform.pos.dst(transformComp.get(minTarget).pos) < ai.sightDistance) {
                ai.currentTarget = f;
                target.target = transformComp.get(minTarget).pos.cpy();
                return true;
            }
        }


        target.target = null;


        return false;

    }

    //finds an entity to follow
    //same logic as target
    public static boolean findFollow(AIComponent ai, TransformComponent transform, FollowComponent follow, Entity entity) {

        for (Family f : ai.targetOrder) {
            Entity minTarget = findMinTarget(f, entity, transform);

            if (minTarget == null) {
                return false;
            }


            if (transform.pos.dst(transformComp.get(minTarget).pos) < ai.sightDistance) {
                ai.currentTarget = f;
                follow.target = minTarget;


                return true;
            }
        }
        follow.target = null;

        return false;
    }

    //finds the closest target
    public static Entity findMinTarget(Family f, Entity entity, TransformComponent transform) {
        //loops through all potential targets and finds the ones that are on the other team and the compares theit distance from him
        Entity[] targets = engine.getEntitiesFor(f).toArray(Entity.class);
        return Arrays.stream(targets)
                .filter(e -> allianceComp.has(e) && allianceComp.get(entity).side != allianceComp.get(e).side && entity != e)
                .min(new Utils.DistanceComparator(transform.pos)).orElse(null);

    }

    //get the distance between the entity and what it's following
    public static float getFollowDist(TransformComponent transform, FollowComponent follow) {
        return transform.pos.dst(transformComp.get(follow.target).pos);

    }

    //function needed for both the server and conquestClient to register classes
    //the classes must be registered in the same order
    public static void registerClasses(Kryo kryo) {
        kryo.register(float[].class);
        kryo.register(int[].class);
        kryo.register(SpawnMessage.class);
        kryo.register(BuildMessage.class);
        kryo.register(PlayerMessage.class);
        kryo.register(InputMessage.class);
        kryo.register(ItemMessage.class);
        kryo.register(WeaponSwitchMessage.class);
        kryo.register(MapTargetMessage.class);
        kryo.register(Rectangle.class);
        kryo.register(PlayerDeathMessage.class);


    }

    //returns all the entities within the array whose alliance is equal to the id
    public static Array<Entity> filterAlliance(int id, ImmutableArray<Entity> entities) {
        Array<Entity> entityArray = new Array<>();
        for (Entity e : entities) {
            if (allianceComp.get(e).side == id) {

                entityArray.add(e);
            }

        }
        return entityArray;
    }

    //rect Contains that accomodates for negative values
    public static boolean rectContains(Rectangle r, float x, float y) {


        float leftX = Math.min(r.x, r.x + r.width);
        float bottomY = Math.min(r.y, r.y + r.height);
        float rightX = Math.max(r.x, r.x + r.width);
        float topY = Math.max(r.y, r.y + r.width);
        return leftX <= x && rightX >= x && bottomY <= y && topY >= y;
    }

    public static boolean rectContains(Rectangle r, Vector2 pos) {
        return rectContains(r, pos.x, pos.y);
    }

    //allows for the spawning to
    public static void spawn(SpawnMessage s) {
        spawn(s.id, s.name, s.x, s.y);


    }

    //sets the distance of the melee weapon from the bearer and the rotation so that the weapon is always in front of the entity
    public static void setMeleeTransform(Entity bearer, Entity weapon) {
        Body body = bodyComp.get(weapon).body;
        Vector2 origin = transformComp.get(bearer).pos;
        //offset is the distance of the person from the weapon
        int offset = meleeComp.get(bearer).weaponOffset;
        if (shieldComp.has(weapon)) {
            offset /= 2;
        }

        TransformComponent transform = transformComp.get(weapon);

        switch (stateComp.get(bearer).direction) {
            case UP:
                body.setTransform(origin.x, origin.y + offset, 0);
                transform.rotation = 90;

                break;
            case DOWN:
                body.setTransform(origin.x, origin.y - offset, 0);
                transform.rotation = 270;
                break;
            case UPLEFT:
                body.setTransform(origin.x - offset, origin.y + offset, 0);
                transform.rotation = 135;
                break;
            case UPRIGHT:
                body.setTransform(origin.x + offset, origin.y + offset, 0);
                transform.rotation = 45;
                break;
            case LEFT:
                body.setTransform(origin.x - offset, origin.y, 0);
                transform.rotation = 180;
                break;
            case DOWNLEFT:
                body.setTransform(origin.x - offset, origin.y - offset, 0);
                transform.rotation = 225;
                break;
            case DOWNRIGHT:
                body.setTransform(origin.x + offset, origin.y - offset, 0);
                transform.rotation = 315;
                break;
            case RIGHT:
                body.setTransform(origin.x + offset, origin.y, 0);
                transform.rotation = 0;
                break;
        }
    }

    public static void spawn(int id, String name, float x, float y) {

        Entity e = Assets.recipes.get(name).make();
        e.add(engine.createComponent(AllianceComponent.class).create(id));


        BodyComponent body = bodyComp.get(e);

        body.body.setTransform(x, y - 40, body.body.getAngle());


        Utils.setUserData(e);
        engine.addEntity(e);

    }

    //compares the distance of values from a seed value
    public static class DistanceComparator implements Comparator<Entity> {
        //the seed value
        private Vector2 start;

        public DistanceComparator(Vector2 start) {
            this.start = start;

        }

        @Override
        public int compare(Entity o1, Entity o2) {
            float dist1 = start.dst(transformComp.get(o1).pos);
            float dist2 = start.dst(transformComp.get(o2).pos);
            if (dist1 > dist2) {
                return 1;
            } else if (dist1 < dist2) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}



