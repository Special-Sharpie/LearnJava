/**
 * Move
 * Handles all the array navigation.
 */

public class Move {
    public void up(Entity sprite, char[][] array, int y_pos, int x_pos){
        if(y_pos - 1 >= 0){
            if(array[y_pos - 1][x_pos] == '.'){
                char avatar = array[y_pos][x_pos];
                array[y_pos][x_pos] = '.';
                array[y_pos - 1][x_pos] = avatar;
                sprite.setNewY(y_pos - 1);
            }
        }else{
            System.out.println("Walking into walls is pointless and hurts my head!");
        }
    }
    public void down(Entity sprite, char[][] array, int y_pos, int x_pos){
        if(y_pos + 1 < array.length){
            if(array[y_pos + 1][x_pos] == '.'){
                char avatar = array[y_pos][x_pos];
                array[y_pos][x_pos] = '.';
                array[y_pos + 1][x_pos] = avatar;
                sprite.setNewY(y_pos + 1);
            }
        }else{
            System.out.println("Walking into walls is pointless and hurts my head!");
        }
    }
    public void left(Entity sprite, char[][] array, int y_pos, int x_pos){
        if(x_pos - 1 >= 0){
            if(array[y_pos][x_pos - 1] == '.'){
            char avatar = array[y_pos][x_pos];
            array[y_pos][x_pos] = '.';
            array[y_pos][x_pos - 1] = avatar;
            sprite.setNewX(x_pos - 1);
            }
        }else{
            System.out.println("Walking into walls is pointless and hurts my head!");
        }
    }
    public void right(Entity sprite, char[][] array, int y_pos, int x_pos){
        if(x_pos + 1 < array[0].length){
            if(array[y_pos][x_pos + 1] == '.'){
                char avatar = array[y_pos][x_pos];
                array[y_pos][x_pos] = '.';
                array[y_pos][x_pos + 1] = avatar;
                sprite.setNewX(x_pos + 1);
            }
        }else{
            System.out.println("Walking into walls is pointless and hurts my head!");
        }
    }
}