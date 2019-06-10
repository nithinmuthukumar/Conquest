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
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.esotericsoftware.kryo.Kryo;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Components.*;
import com.nithinmuthukumar.conquest.Conquest;
import com.nithinmuthukumar.conquest.GameMap;
import com.nithinmuthukumar.conquest.Server.*;

import java.util.Arrays;
import java.util.Comparator;

import static com.nithinmuthukumar.conquest.Globals.*;


public class Utils {
    public static Comparator<Entity> zyComparator = (e1, e2) -> {

        if (transformComp.get(e1).z == transformComp.get(e2).z) {
            return (int) Math.signum(transformComp.get(e2).getRenderY() - transformComp.get(e1).getRenderY());
        } else {
            return (int) Math.signum(transformComp.get(e1).z - transformComp.get(e2).z);
        }

    };

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

    public static int screenToCameraX(float x) {

        return MathUtils.round(Conquest.camera.position.x - Gdx.graphics.getWidth() / 2 + x);
    }

    public static Texture resizeTexture(Texture texture, float newWidth, float newHeight) {
        Pixmap og = texture.getTextureData().consumePixmap();
        Pixmap new_ = new Pixmap(MathUtils.round(newWidth), MathUtils.round(newHeight), og.getFormat());
        new_.drawPixmap(og,
                0, 0, og.getWidth(), og.getHeight(),
                0, 0, new_.getWidth(), new_.getHeight()
        );


        return new Texture(new_);

    }

    public static int snapToGrid(GameMap gameMap, float x) {

        return MathUtils.round(gameMap.getTileWidth() * (MathUtils.ceil(x / gameMap.getTileWidth())));
    }

    public static int screenToCameraY(float y) {

        return MathUtils.round(Conquest.camera.position.y + Gdx.graphics.getHeight() / 2 - y);
    }

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

    public static boolean findTarget(AIComponent ai, TransformComponent transform, TargetComponent target, Entity entity) {
        for (Family f : ai.targetOrder) {
            Entity minTarget = findMinTarget(f, entity, transform);
            if (minTarget == null)
                return false;


            if (transform.pos.dst(transformComp.get(minTarget).pos) < ai.sightDistance) {
                ai.currentTarget = f;
                target.target = transformComp.get(minTarget).pos.cpy();
                return true;
            }
        }

        target.target = null;


        return false;

    }

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

    public static Entity findMinTarget(Family f, Entity entity, TransformComponent transform) {

        Entity[] targets = Conquest.engine.getEntitiesFor(f).toArray(Entity.class);
        return Arrays.stream(targets)
                .filter(e -> allianceComp.get(entity).side != allianceComp.get(e).side && entity != e)
                .min(new Utils.DistanceComparator(transform.pos)).orElse(null);

    }

    public static float getFollowDist(TransformComponent transform, FollowComponent follow) {
        return transform.pos.dst(transformComp.get(follow.target).pos);

    }

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

    public static Array<Entity> filterAlliance(int id, ImmutableArray<Entity> entities) {
        Array<Entity> entityArray = new Array<>();
        for (Entity e : entities) {
            if (allianceComp.get(e).side == id) {

                entityArray.add(e);
            }

        }
        return entityArray;
    }

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

    public static void spawn(SpawnMessage s) {
        spawn(s.id, s.name, s.x, s.y);


    }

    public static void spawn(int id, String name, float x, float y) {
        Entity e = Assets.recipes.get(name).make();
        e.add(Conquest.engine.createComponent(AllianceComponent.class).create(id));


        BodyComponent body = bodyComp.get(e);

        body.body.setTransform(x, y - 40, body.body.getAngle());


        Utils.setUserData(e);
        Conquest.engine.addEntity(e);

    }

    public static class DistanceComparator implements Comparator<Entity> {
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



