/**
 * Entity
 * Handles the creation of all entities on the map.
 * The methods of this class are primarily targeted towards the player, however do prove useful
 * for all other entities in the game.
 */

import java.util.Random;

public class Entity {
    private char eAvatar;
    private int eStartY;
    private int eStartX;
    private double baseHealth = 100;
    private double health;
    private double eHealthMultiplier;
    private double baseDamage = 10;
    private double damage;
    public Entity(char avatar, double healthMultipier, double damageMultipier, int yBound, int xBound){
        eHealthMultiplier = healthMultipier;
        eAvatar = avatar;
        health = baseHealth * eHealthMultiplier;
        damage = baseDamage * damageMultipier;
        Random rand = new Random();
        eStartY = rand.nextInt(yBound);
        eStartX = rand.nextInt(xBound);
    }
    public char getAvatar(){
        return eAvatar;
    }
    public double getHealth(){
        return health;
    }
    public double setHealth(double new_Health){
        health = new_Health;
        return health;
    }
    public double getDamage(){
        return damage;
    }
    public double setDamage(double new_damage){
        damage = new_damage;
        return damage;
    }
    public double getHealthMultiplier(){
        return eHealthMultiplier;
    }
    public double setHealthMultiplier(double newMultiplier){
        System.out.println("A mystical aura surounds me as pass through this door... I feel stronger...");
        System.out.println("Max Health increased!");
        eHealthMultiplier = newMultiplier;
        return eHealthMultiplier;
    }
    public double getMaxHealth(){
        double maxHealth = baseHealth * eHealthMultiplier;
        return maxHealth;
    }
    public int current_y(){
        return eStartY;
    }
    public int current_x(){
        return eStartX;
    }
    public int setNewY(int new_y){
        eStartY = new_y;
        return eStartY;
    }
    public int setNewX(int new_x){
        eStartX = new_x;
        return eStartX;
    }
    public int levelUp(double enemiesKilled, int playerLevel, double healthIncrease){
        double exp = Math.pow(enemiesKilled, 0.5);
        int whole = (int)exp;
        if (exp % whole == 0){
            double newDamage = getDamage() + healthIncrease;
            setDamage(newDamage);
            playerLevel++;
            System.out.println("Level Up! Level: " + playerLevel);
        }
        return playerLevel;
    }
}