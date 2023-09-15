package codes;
import java.io.Serializable;
import java.util.*;

public class restaurant implements Serializable {
    private final String name;
    private final int id;
    private final double score;
    private final String price_range;
    private final int zip;
    private List<food> foodList = new ArrayList<>();
    private int foodCount;
    private Set<String> FoodCategory = new HashSet<>(); // category of the foods this res has
    int FoodsAdded = 0;
    List<String> categories = new ArrayList<>(); // category of the res

    public restaurant(int id, String name, double score, String price_range, int zip, List<String> array) {
        this.name = name;
        this.id = id;
        this.score = score;
        this.price_range = price_range;
        this.zip = zip;
        categories = array;
    }

    public restaurant(String[] array) {
        int size = array.length;
        if (size > 8)
            size = 8;
        this.id = Integer.parseInt(array[0]);
        this.name = array[1];
        this.score = Double.parseDouble(array[2]);
        this.price_range = array[3];
        this.zip = Integer.parseInt(array[4]);

        categories.addAll(Arrays.asList(array).subList(5, size));
        
    }
    public restaurant() {

        this.id = 0;
        this.name = "";
        this.score =0;
        this.price_range = "";
        this.zip = 0;

    }
    public int getFoodCount()
    {
        foodCount= foodList.size();
        return foodCount;
    }
    public String getName() {
        return name;
    }

    public double getScore() {
        return score;
    }

    public String getPrice_range() {
        return price_range;
    }

    public int getZip() {
        return zip;
    }

    public int getId() {
        return id;
    }

    
    public Set<String> getCatSet() {
        return FoodCategory;
    }
    public List<String> getCategories() {
        return categories;
    }

    public List<food> getFoodList() {
        return foodList;
    }
    public void setFoodList(List<food> f) {
        this.foodList.addAll(f);
    }

    public synchronized void addFoodItem(food f) {
        foodList.add(f);
        FoodCategory.add(f.getCategory()); // update the hashset, if it didn't have that certain category
        ++FoodsAdded;
    }

    public String Cat(int i){
        return categories.get(i);
    }

    public int hasCategory(String category) {
        for (int i = 0; i < categories.size(); i++) {
            //if (categories.get(i).equalsIgnoreCase(category))
            if(categories.get(i).toLowerCase().contains(category.toLowerCase()))
                return i;
        }
        return -1;
    }

    public List<food> hasFood(String foodName) {
        List<food>foundFood = new ArrayList();
        for (food temp : foodList) {
           // if (temp.getName().equalsIgnoreCase(foodName)) 
            if(temp.getName().toLowerCase().contains(foodName.toLowerCase()))
            {
                //str = str + temp.show(); // shows food details
                foundFood.add(temp.retFood());
                
            }
        }
        return foundFood;
    }

    public List<food>  hasFoodCat(String FoodCat) {
        List<food>foundFood = new ArrayList();
        
            for (food temp : foodList) {
                //if (temp.getCategory().equalsIgnoreCase(FoodCat)) 
                if(temp.getCategory().toLowerCase().contains(FoodCat.toLowerCase()))
                {
                    foundFood.add(temp.retFood());// shows food details
                }
            }
        
        return foundFood;
    }

    public List<food> inPricerange(double lower, double upper) {
        List<food>foundFood = new ArrayList();
        for (food temp : foodList) {
            if (temp.getPrice() >= lower && temp.getPrice() <= upper) {
                foundFood.add(temp.retFood());// shows food details
            }
        }
        return foundFood;
    }

    public List<food>  priciest() {
        double max = 0;
        List<food>foundFood = new ArrayList();
        for (food temp : foodList) {
            if (temp.getPrice() > max)
                max = temp.getPrice();
        }
        for (food temp : foodList) {
            if (temp.getPrice() == max) {
                foundFood.add(temp.retFood());
            }

        }
        return foundFood;
    }
    
    public String Foodshow(food f) {
        return ("Restaurant Name: " + name + "\nFood Name: " + f.getName() + "\nFood Category: " + f.getCategory()
        + "\nFood Price: " + f.getPrice() + "\n");
    }

    public String show() {
        return ("Restaurant Name: " + name + "\nScore: " + score
                + "\nPrice Range: " + price_range + "\nZip: " + zip + "\n");
    }

}
