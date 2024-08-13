/*
 *      Dynamic Programming Algorithm
 *      Core Algorithm:
 *          To split the problem:
 *          ---first consider the situation only have one item and 1 size of the bag,
 *             calculate the optimum value.
 *          ---then 2 size of the bag...to 6 size of the bag,
 *              calculate the optimum value.
 *          ---then the two items and 1 size of the bag...to 6 size of the bag,
 *              calculate the optimum value.During this progress,the prior optimum value
 *              can be used as the optimum value at the remaining size of
 *              the bag which has bagged the present item.
 *          ---by parity of reasoning, go on then finally we can calculate the optimum
 *              value of five items and 6 size of the bag.
 */
public class DynamicProgramming {
    //encapsulate name,space,value into a class
    static Water water = new Water();
    static Book book = new Book();
    static Food food = new Food();
    static Jacket jacket = new Jacket();
    static Camera camera = new Camera();

    //packaging all the classes into an array,deal with it one by one
    static Item[] arr={water,book,food,jacket,camera};

    static int space=6;//the size of the bag

    //recording the value in the sheet how to composed of.
    //need this to track back the path for final answer.
    //every time assign a value to the sheet,
    //we record its name where the value is from.
    static String[][] paths=new String[arr.length][space];

    public static void main(String[] args) {
        //the x-axis represents the items,
        //the y-axis represents the size of the bag.
        //So the value in it represents the optimum value
        // of the situation that need to bag all present items in this size
        int[][] sheet=new int[arr.length][space];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < space; j++) {

                if(arr[i].getWeight()<=(j+1)){//if the size of the bag isn't smaller than item's weight

                    //we assign the value of the first object to
                    // the first row directly as the present maximum value
                    if(i==0){
                        sheet[i][j]=arr[i].getValue();
                        paths[i][j] = arr[i].getName();

                    }else{//deal with the situation that not in first row

                        //in the first column or item's wight just equals to the size of the bag
                        if(j==0||arr[i].getWeight()==(j+1)){

                            //if the item's value is bigger than the value in the same position
                            // of the prior row,we assign this value to the position
                            if(arr[i].getValue()>=sheet[i - 1][j]){
                                sheet[i][j] = arr[i].getValue();
                                paths[i][j] = arr[i].getName();
                            }else{//else assign the prior row's value to here
                                sheet[i][j] = sheet[i - 1][j];
                                paths[i][j] = arr[i-1].getName();
                            }

                        //deal with the situation that not in the first column
                        //and the item's wight isn't equal to the size of the bag
                        }else{
                            //'sheet[i - 1][((j + 1) - arr[i].getWeight()) - 1]'  means:
                            //the present size of the bag minus this item's weight,
                            //then return the optimum value from the prior row in this new size.
                            //It represents got the optimum value at the remaining size of
                            // the bag which has bagged the present item.
                            int temp=arr[i].getValue() + sheet[i - 1][((j + 1) - arr[i].getWeight()) - 1];
                            if(temp>=sheet[i - 1][j]){
                                sheet[i][j] = temp;
                                paths[i][j] = arr[i].getName()+"+"+arr[i-1].getName();
                            }else{
                                sheet[i][j] = sheet[i - 1][j];
                                paths[i][j] = arr[i-1].getName();
                            }
                        }
                    }
                }else{//if the size of the bag is smaller than the present item's weight
                    if(i==0) {//the first row is directly equal to zero
                        sheet[i][j] = 0;
                    }else{//or,the optimum value at present equals to the prior row's
                        sheet[i][j] = sheet[i-1][j];
                        paths[i][j] = arr[i-1].getName();
                    }
                }
            }
        }

        //output the result
        System.out.print("\t\t\t\t");
        for (int i = 1; i <= space; i++) {
            System.out.print(i+"\t");
        }
        System.out.println();
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i].getName()+"\t"+arr[i].getWeight()+"\t"+arr[i].getValue()+":\t");
            for (int j = 0; j < space; j++) {
                System.out.print(sheet[i][j]+"\t");
            }
            System.out.println();
        }
        System.out.println("Finally I decided to take these stuffs:\t"+findPath(arr.length-1,space-1));
    }

    //an iterator to find the path to the optimum value
    public static String findPath(int row,int column){
        if(row==0||column<arr[row].getWeight()){
            return paths[row][column];
        } else{
            if(paths[row][column].contains("+")){
                if(column==arr[row].getWeight())return paths[row][column];
                else return paths[row][column].split("\\+")[0]+"+"+findPath(row-1,column- arr[row].getWeight());
            }else{
                return findPath(row-1,column);
            }
        }
    }
}

//inner class to encapsulate all the items
class Item{
    String name;
    int weight;
    int value;

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public int getValue() {
        return value;
    }

}
class Water extends Item{
    @Override
    public String getName() {
        return "water";
    }

    @Override
    public int getWeight() {
        return 3;
    }

    @Override
    public int getValue() {
        return 10;
    }

}
class Book extends Item{
    @Override
    public String getName() {
        return "book";
    }

    @Override
    public int getWeight() {
        return 1;
    }

    @Override
    public int getValue() {
        return 3;
    }
}
class Food extends Item{
    @Override
    public String getName() {
        return "food";
    }

    @Override
    public int getWeight() {
        return 2;
    }

    @Override
    public int getValue() {
        return 9;
    }
}
class Jacket extends Item{
    @Override
    public String getName() {
        return "jacket";
    }

    @Override
    public int getWeight() {
        return 2;
    }

    @Override
    public int getValue() {
        return 5;
    }
}
class Camera extends Item{
    @Override
    public String getName() {
        return "camera";
    }

    @Override
    public int getWeight() {
        return 1;
    }

    @Override
    public int getValue() {
        return 6;
    }
}
