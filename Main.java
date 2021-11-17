/**
 * Main
 * Houses all logic of the game.
 * Runs the game.
 */

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void addToMap(char[][] array, Entity sprite){
        array[sprite.current_y()][sprite.current_x()] = sprite.getAvatar();
    }
    public static void main(String[] args) {

        int y = 10;
        int x = 10;
        int playerHitChance = 99;
        int enemyHitRate = 2;
        int enemiesKilled = 0;
        double exp = 1;
        double totalExp = 0;
        int playerLevel = 0;
        int bossDoors = 1;
        int dungeonNum = 1;
        double healthIncrease = 2.5;
        int yUpperBound = y - 1;
        int xUpperBound = x - 1;

        Random rand = new Random();
        Entity player = new Entity('P', 1.0, 1.0, yUpperBound, xUpperBound);
        Move move = new Move();
        GameMap map = new GameMap(y, x);

        char[][] arr = map.createMap();

        map.checkPostion(arr, player);
        addToMap(arr, player);

        while(true){
            System.out.println("Player Health: " + player.getHealth() + "/" + player.getMaxHealth());
            System.out.println("Player Level: " + playerLevel);
            System.out.println("Player Damage: " + player.getDamage());
            System.out.println("Enemies Killed: " + enemiesKilled);
            System.out.println("Dungeon Level: " + dungeonNum);
            for (int i=  0; i < y; i++){
                System.out.println(Arrays.toString(arr[i]));
            }
            System.out.println("------------------------------");
            Scanner input = new Scanner(System.in);
            char cmd = input.next().charAt(0);
            if(cmd == 'w'){
                try{
                    if (arr[player.current_y() - 1][player.current_x()] == 'D'){
                        dungeonNum++;
                        if(dungeonNum % 10 == 0){
                            double newMultiplier = player.getHealthMultiplier() + 0.25;
                            player.setHealthMultiplier(newMultiplier);
                            map.enemyLevelUp();
                            exp++;
                        }
                        arr = map.createMap();
                        addToMap(arr, player);
                    }
                    if (arr[player.current_y() - 1][player.current_x()] == 'H'){
                            if (player.getHealth() != player.getMaxHealth()){
                                if (player.getHealth() < player.getMaxHealth() - 50){
                                    double hpUp = player.getHealth() + 50.0;
                                    player.setHealth(hpUp);
                                    arr[player.current_y() - 1][player.current_x()] = '.';
                                }else{
                                    player.setHealth(player.getMaxHealth());
                                    arr[player.current_y() - 1][player.current_x()] = '.';
                                }
                        }else{}
                    }
                    if (arr[player.current_y() - 1][player.current_x()] == 'U'){
                        double damageUp = player.getDamage() + 7.5;
                        player.setDamage(damageUp);
                        arr[player.current_y() - 1][player.current_x()] = '.';
                    }
                    if (arr[player.current_y() - 1][player.current_x()] == 'S'){
                        player.setHealth(250);
                        arr[player.current_y() - 1][player.current_x()] = '.';
                    }
                    if (arr[player.current_y() - 1][player.current_x()] == 'E'){
                        int playerHitRoll = rand.nextInt(playerHitChance);
                        String[] enemy = {
                            Integer.toString(player.current_y() - 1), 
                            Integer.toString(player.current_x())
                        };
                        double newEnemyHealth = map.getEnemiesHealth().get(Arrays.toString(enemy)) - player.getDamage();
                        if (newEnemyHealth <= 0.0){
                            map.getEnemiesHealth().remove(Arrays.toString(enemy));
                            enemiesKilled++;
                            totalExp += exp;
                            arr[player.current_y() - 1][player.current_x()] = '.';
                            playerLevel = player.levelUp(totalExp, playerLevel, healthIncrease);
                        }else{
                            if (playerHitRoll <= (playerHitChance + 1)/enemyHitRate){
                                double enemyDamage = map.getEmemiesDamage().get(Arrays.toString(enemy));
                                double newPlayerHealth = player.getHealth() - enemyDamage;
                                player.setHealth(newPlayerHealth);
                                if (player.getHealth() == 0.0){
                                    input.close();
                                    System.out.println("Game Over!");
                                    break;
                                }
                            }
                            map.getEnemiesHealth().put(Arrays.toString(enemy), newEnemyHealth);
                            System.out.println("Enemy Health: " + map.getEnemiesHealth().get(Arrays.toString(enemy)));
                        }
                    }
                    if (arr[player.current_y() - 1][player.current_x()] == 'B'){
                        int playerHitRoll = rand.nextInt(playerHitChance);
                        String[] enemy = {
                            Integer.toString(player.current_y() - 1), 
                            Integer.toString(player.current_x())
                        };
                        double newEnemyHealth = map.getEnemiesHealth().get(Arrays.toString(enemy)) - player.getDamage();
                        if (newEnemyHealth <= 0.0){
                            map.getEnemiesHealth().remove(Arrays.toString(enemy));
                            arr[player.current_y() - 1][player.current_x()] = '.';
                            map.addObject(arr, 'D', 100.0, 0.0, bossDoors);
                        }else{
                            if (playerHitRoll <= (playerHitChance + 1)/enemyHitRate){
                                double enemyDamage = map.getEmemiesDamage().get(Arrays.toString(enemy));
                                double newPlayerHealth = player.getHealth() - enemyDamage;
                                player.setHealth(newPlayerHealth);
                                if (player.getHealth() < 0.0){
                                    input.close();
                                    System.out.println("Game Over!");
                                    break;
                                }
                            }
                            map.getEnemiesHealth().put(Arrays.toString(enemy), newEnemyHealth);
                            System.out.println("Enemy Health: " + map.getEnemiesHealth().get(Arrays.toString(enemy)));
                        }
                    }
                }catch(Exception e){}
                move.up(player, arr, player.current_y(), player.current_x());
            }
            else if(cmd == 's'){
                try{
                    if (arr[player.current_y() + 1][player.current_x()] == 'D'){
                        dungeonNum++;
                        if(dungeonNum % 10 == 0){
                            double newMultiplier = player.getHealthMultiplier() + 0.25;
                            player.setHealthMultiplier(newMultiplier);
                            map.enemyLevelUp();
                            exp++;
                        }
                        arr = map.createMap();
                        addToMap(arr, player);
                    }
                    if (arr[player.current_y() + 1][player.current_x()] == 'H'){
                        if (player.getHealth() != player.getMaxHealth()){
                            if (player.getHealth() < player.getMaxHealth() - 50.0){
                                double hpUp = player.getHealth() + 50.0;
                                player.setHealth(hpUp);
                                arr[player.current_y() + 1][player.current_x()] = '.';
                            }else{
                                player.setHealth(player.getMaxHealth());
                                arr[player.current_y() + 1][player.current_x()] = '.';
                            }
                        }else{}
                    }
                    if (arr[player.current_y() + 1][player.current_x()] == 'U'){
                        double damageUp = player.getDamage() + 7.5;
                        player.setDamage(damageUp);
                        arr[player.current_y() + 1][player.current_x()] = '.';
                    }
                    if (arr[player.current_y() + 1][player.current_x()] == 'S'){
                        player.setHealth(250);
                        arr[player.current_y() + 1][player.current_x()] = '.';
                    }
                    if (arr[player.current_y() + 1][player.current_x()] == 'E'){
                        int playerHitRoll = rand.nextInt(playerHitChance);
                        String[] enemy = {
                            Integer.toString(player.current_y() + 1), 
                            Integer.toString(player.current_x())
                        };
                        double newEnemyHealth = map.getEnemiesHealth().get(Arrays.toString(enemy)) - player.getDamage();
                        if (newEnemyHealth <= 0.0){
                            map.getEnemiesHealth().remove(Arrays.toString(enemy));
                            enemiesKilled++;
                            totalExp += exp;
                            arr[player.current_y() + 1][player.current_x()] = '.';
                            playerLevel = player.levelUp(totalExp, playerLevel, healthIncrease);
                        }else{
                            if (playerHitRoll <= (playerHitChance + 1)/enemyHitRate){
                                double enemyDamage = map.getEmemiesDamage().get(Arrays.toString(enemy));
                                double newPlayerHealth = player.getHealth() - enemyDamage;
                                player.setHealth(newPlayerHealth);
                                if (player.getHealth() <= 0.0){
                                    input.close();
                                    System.out.println("Game Over!");
                                    break;
                                }
                            }
                            map.getEnemiesHealth().put(Arrays.toString(enemy), newEnemyHealth);
                            System.out.println("Enemy Health: " + map.getEnemiesHealth().get(Arrays.toString(enemy)));
                        }
                    }
                    if (arr[player.current_y() + 1][player.current_x()] == 'B'){
                        int playerHitRoll = rand.nextInt(playerHitChance);
                        String[] enemy = {
                            Integer.toString(player.current_y() + 1), 
                            Integer.toString(player.current_x())
                        };
                        double newEnemyHealth = map.getEnemiesHealth().get(Arrays.toString(enemy)) - player.getDamage();
                        if (newEnemyHealth <= 0.0){
                            map.getEnemiesHealth().remove(Arrays.toString(enemy));
                            arr[player.current_y() + 1][player.current_x()] = '.';
                            map.addObject(arr, 'D', 100.0, 0.0, bossDoors);
                        }else{
                            if (playerHitRoll <= (playerHitChance + 1)/enemyHitRate){
                                double enemyDamage = map.getEmemiesDamage().get(Arrays.toString(enemy));
                                double newPlayerHealth = player.getHealth() - enemyDamage;
                                player.setHealth(newPlayerHealth);
                                if (player.getHealth() <= 0.0){
                                    input.close();
                                    System.out.println("Game Over!");
                                    break;
                                }
                            }
                            map.getEnemiesHealth().put(Arrays.toString(enemy), newEnemyHealth);
                            System.out.println("Enemy Health: " + map.getEnemiesHealth().get(Arrays.toString(enemy)));
                        }
                    }
                }catch(Exception e){}
                move.down(player, arr, player.current_y(), player.current_x());
            }
            else if(cmd == 'a'){
                try{
                    if (arr[player.current_y()][player.current_x() - 1] == 'D'){
                        dungeonNum++;
                        if(dungeonNum % 10 == 0){
                            double newMultiplier = player.getHealthMultiplier() + 0.25;
                            player.setHealthMultiplier(newMultiplier);
                            map.enemyLevelUp();
                            exp++;
                        }
                        arr = map.createMap();
                        addToMap(arr, player);
                    }
                    if (arr[player.current_y()][player.current_x() - 1] == 'H'){
                        if (player.getHealth() != player.getMaxHealth()){
                            if (player.getHealth() < player.getMaxHealth() - 50.0){
                                double hpUp = player.getHealth() + 50.0;
                                player.setHealth(hpUp);
                                arr[player.current_y()][player.current_x() - 1] = '.';
                            }else{
                                player.setHealth(player.getMaxHealth());
                                arr[player.current_y()][player.current_x() - 1] = '.';
                            }
                        }else{}
                    }
                    if (arr[player.current_y()][player.current_x() - 1] == 'U'){
                        double damageUp = player.getDamage() + 7.5;
                        player.setDamage(damageUp);
                        arr[player.current_y()][player.current_x() - 1] = '.';
                    }
                    if (arr[player.current_y()][player.current_x() - 1] == 'S'){
                        player.setHealth(250);
                        arr[player.current_y()][player.current_x() - 1] = '.';
                    }
                    if (arr[player.current_y()][player.current_x() - 1] == 'E'){
                        int playerHitRoll = rand.nextInt(playerHitChance);
                        String[] enemy = {
                            Integer.toString(player.current_y()), 
                            Integer.toString(player.current_x() - 1)
                        };
                        double newEnemyHealth = map.getEnemiesHealth().get(Arrays.toString(enemy)) - player.getDamage();
                        if (newEnemyHealth <= 0.0){
                            map.getEnemiesHealth().remove(Arrays.toString(enemy));
                            enemiesKilled++;
                            totalExp += exp;
                            arr[player.current_y()][player.current_x() - 1] = '.';
                            playerLevel = player.levelUp(totalExp, playerLevel, healthIncrease);
                        }else{
                            if (playerHitRoll <= (playerHitChance + 1)/enemyHitRate){
                                double enemyDamage = map.getEmemiesDamage().get(Arrays.toString(enemy));
                                double newPlayerHealth = player.getHealth() - enemyDamage;
                                player.setHealth(newPlayerHealth);
                                if (player.getHealth() <= 0.0){
                                    input.close();
                                    System.out.println("Game Over!");
                                    break;
                                }
                            }
                            map.getEnemiesHealth().put(Arrays.toString(enemy), newEnemyHealth);
                            System.out.println("Enemy Health: " + map.getEnemiesHealth().get(Arrays.toString(enemy)));
                        }
                    }
                    if (arr[player.current_y()][player.current_x() - 1] == 'B'){
                        int playerHitRoll = rand.nextInt(playerHitChance);
                        String[] enemy = {
                            Integer.toString(player.current_y()), 
                            Integer.toString(player.current_x() - 1)
                        };
                        double newEnemyHealth = map.getEnemiesHealth().get(Arrays.toString(enemy)) - player.getDamage();
                        if (newEnemyHealth <= 0.0){
                            map.getEnemiesHealth().remove(Arrays.toString(enemy));
                            arr[player.current_y()][player.current_x() - 1] = '.';
                            map.addObject(arr, 'D', 100.0, 0.0, bossDoors);
                        }else{
                            if (playerHitRoll <= (playerHitChance + 1)/enemyHitRate){
                                double enemyDamage = map.getEmemiesDamage().get(Arrays.toString(enemy));
                                double newPlayerHealth = player.getHealth() - enemyDamage;
                                player.setHealth(newPlayerHealth);
                                if (player.getHealth() <= 0.0){
                                    input.close();
                                    System.out.println("Game Over!");
                                    break;
                                }
                            }
                            map.getEnemiesHealth().put(Arrays.toString(enemy), newEnemyHealth);
                            System.out.println("Enemy Health: " + map.getEnemiesHealth().get(Arrays.toString(enemy)));
                        }
                    }
                }catch(Exception e){}
                move.left(player, arr, player.current_y(), player.current_x());
            }
            else if(cmd == 'd'){
                try{
                    if (arr[player.current_y()][player.current_x() + 1] == 'D'){
                        dungeonNum++;
                        if(dungeonNum % 10 == 0){
                            double newMultiplier = player.getHealthMultiplier() + 0.25;
                            player.setHealthMultiplier(newMultiplier);
                            map.enemyLevelUp();
                            exp++;
                        }
                        arr = map.createMap();
                        addToMap(arr, player);
                    }                    
                    if (arr[player.current_y()][player.current_x() + 1] == 'H'){
                        if (player.getHealth() != player.getMaxHealth()){
                            if (player.getHealth() < player.getMaxHealth() - 50.0){
                                double hpUp = player.getHealth() + 50.0;
                                player.setHealth(hpUp);
                                arr[player.current_y()][player.current_x() + 1] = '.';
                            }else{
                                player.setHealth(player.getMaxHealth());
                                arr[player.current_y()][player.current_x() + 1] = '.';
                            }
                        }else{}
                    }
                    if (arr[player.current_y()][player.current_x() + 1] == 'U'){
                        double damageUp = player.getDamage() + 7.5;
                        player.setDamage(damageUp);
                        arr[player.current_y()][player.current_x() + 1] = '.';
                    }
                    if (arr[player.current_y()][player.current_x() + 1] == 'S'){
                        player.setHealth(250);
                        arr[player.current_y()][player.current_x() + 1] = '.';
                    }
                    if (arr[player.current_y()][player.current_x() + 1] == 'E'){
                        int playerHitRoll = rand.nextInt(playerHitChance);
                        String[] enemy = {
                            Integer.toString(player.current_y()), 
                            Integer.toString(player.current_x() + 1)
                        };
                        double newEnemyHealth = map.getEnemiesHealth().get(Arrays.toString(enemy)) - player.getDamage();
                        if (newEnemyHealth <= 0.0){
                            map.getEnemiesHealth().remove(Arrays.toString(enemy));
                            enemiesKilled++;
                            totalExp += exp;
                            arr[player.current_y()][player.current_x() + 1] = '.';
                            playerLevel = player.levelUp(totalExp, playerLevel, healthIncrease);
                        }else{
                            if (playerHitRoll <= (playerHitChance + 1)/enemyHitRate){
                                double enemyDamage = map.getEmemiesDamage().get(Arrays.toString(enemy));
                                double newPlayerHealth = player.getHealth() - enemyDamage;
                                player.setHealth(newPlayerHealth);
                                if (player.getHealth() <= 0.0){
                                    input.close();
                                    System.out.println("Game Over!");
                                    break;
                                }
                            }
                            map.getEnemiesHealth().put(Arrays.toString(enemy), newEnemyHealth);
                            System.out.println("Enemy Health: " + map.getEnemiesHealth().get(Arrays.toString(enemy)));
                        }
                    }
                    if (arr[player.current_y()][player.current_x() + 1] == 'B'){
                        int playerHitRoll = rand.nextInt(playerHitChance);
                        String[] enemy = {
                            Integer.toString(player.current_y()), 
                            Integer.toString(player.current_x() + 1)
                        };
                        double newEnemyHealth = map.getEnemiesHealth().get(Arrays.toString(enemy)) - player.getDamage();
                        if (newEnemyHealth <= 0.0){
                            map.getEnemiesHealth().remove(Arrays.toString(enemy));
                            arr[player.current_y()][player.current_x() + 1] = '.';
                            map.addObject(arr, 'D', 100.0, 0.0, bossDoors);
                        }else{
                            if (playerHitRoll <= (playerHitChance + 1)/enemyHitRate){
                                double enemyDamage = map.getEmemiesDamage().get(Arrays.toString(enemy));
                                double newPlayerHealth = player.getHealth() - enemyDamage;
                                player.setHealth(newPlayerHealth);
                                if (player.getHealth() <= 0.0){
                                    input.close();
                                    System.out.println("Game Over!");
                                    break;
                                }
                            }
                            map.getEnemiesHealth().put(Arrays.toString(enemy), newEnemyHealth);
                            System.out.println("Boss Health: " + map.getEnemiesHealth().get(Arrays.toString(enemy)));
                        }
                    }
                }
                catch(Exception e){}
                move.right(player, arr, player.current_y(), player.current_x());
            }
        }

    }
    
}