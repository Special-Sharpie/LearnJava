/**
 * GameMap
 * Handles the creation of the Dungeons.
 * Adds all entities to the map.
 * Creates hashmaps for handling enemy health and damamge.
 */

import java.util.HashMap;
import java.util.Random;
import java.util.Arrays;

public class GameMap {
    int mWidth;
    int mHeight;
    double enemyHealthMultiplier = 1.0;
    double bossHealthMultiplier = 5.0;
    double enemyDamageMultiplier = 1.0;
    double bossDamageMultiplier = 2.5;
    Random rand = new Random();
    HashMap<String, Double> enemys_on_map_health = new HashMap<String, Double>();
    HashMap<String, Double> enemys_on_map_damage = new HashMap<String, Double>();
    String[] walls = {"ww", "www", "wwww"};
    String[] wallAngles = {"0", "90"};
    String angle = "0";

    public GameMap(int width, int height){
        mWidth = width;
        mHeight = height;
    }

    public void checkPostion(char[][] map, Entity entity){
        if(map[entity.current_y()][entity.current_x()] != '.'){
            entity.setNewY(rand.nextInt(mHeight));
            entity.setNewX(rand.nextInt(mWidth));
            if(map[entity.current_y()][entity.current_x()] != '.'){
                checkPostion(map, entity);
            }else{}
        }else{}
    }
    public char[][] createMap(){
        enemys_on_map_health.clear();
        enemys_on_map_damage.clear();
        char[][] arr = new char[mHeight][];
        for(int i= 0; i < mHeight; i++){
            char[] xRange = new char[mWidth];
            for(int m=0; m < mWidth; m++){
                xRange[m] = '.';
            }
            arr[i] = xRange;
        }
        addWalls(arr, 5);
        int bossChance = rand.nextInt(99);
        if (bossChance <= 5){
            arr = addObject(arr, 'B', bossHealthMultiplier, bossDamageMultiplier, 1);
            arr = addObject(arr, 'S', 100.0, 0.0, 1);
            arr = addObject(arr, 'H', 100.0, 0.0, 2);
        }else{
            arr = addObject(arr, 'E', enemyHealthMultiplier, enemyDamageMultiplier, 4);
            arr = addObject(arr, 'H', 100.0, 0.0, 1);
            arr = addObject(arr, 'D', 100.0, 0.0, 1);
            int upgradeChance = rand.nextInt(99);
            if (upgradeChance <= 20){
                arr = addObject(arr, 'U', 100, 0, 1);
            }
        }
        return arr;
    }
    public static void addToMap(char[][] array, Entity sprite){
        array[sprite.current_y()][sprite.current_x()] = sprite.getAvatar();
    }
    public char[][] addObject(char[][] map, char avatar, double healthMultipier, double damageMultipier, int numOfObject){
        for (int i = 1; i <= numOfObject; i++){
            Entity object = new Entity(avatar, healthMultipier, damageMultipier, mHeight, mWidth);
            checkPostion(map, object);
            String[] object_postion = {Integer.toString(object.current_y()), Integer.toString(object.current_x())};
            enemys_on_map_health.put(Arrays.toString(object_postion), object.getHealth());
            enemys_on_map_damage.put(Arrays.toString(object_postion), object.getDamage());
            addToMap(map, object);
        }
        return map;
    }

    public char[][] addWalls(char[][] map, int wallAmount){
        int height = mHeight - 1;
        int width = mWidth - 1;
        for (int i = 1; i <= wallAmount;  i++){
            String wall = walls[rand.nextInt(3)];
            String angle = wallAngles[rand.nextInt(2)];
            if (angle == "0"){
                int[] origin = {rand.nextInt(height), rand.nextInt(width)};
                if (origin[1] + (wall.length() - 1) > width){
                    int varience = width - (origin[1] + (wall.length() - 1));
                    origin[1] = origin[1] + varience;
                }
                map[origin[0]][origin[1]] = 'W';
                for (int j = 1; j <= wall.length() - 1; j++){
                    map[origin[0]][origin[1] + j] = 'W'; 
                }
            }
            if (angle == "90"){
                int[] origin = {rand.nextInt(height), rand.nextInt(width)};
                if (origin[0] + (wall.length() - 1) > width){
                    int varience = width - (origin[0] + (wall.length() - 1));
                    origin[0] = origin[0] + varience;
                }
                map[origin[0]][origin[1]] = 'W';
                for (int j = 1; j <= wall.length() - 1; j++){
                    map[origin[0] + j][origin[1]] = 'W'; 
                }
            }
        }
        return map;
    }

    public HashMap<String, Double> getEnemiesHealth(){
        return enemys_on_map_health;
    }
    public HashMap<String, Double> getEmemiesDamage(){
        return enemys_on_map_damage;
    }
    public void enemyLevelUp() {
        bossDamageMultiplier += 0.5;
        enemyDamageMultiplier +=  0.25;
        bossHealthMultiplier += 1.0;
        enemyHealthMultiplier += 0.5;     
    }
        
}
