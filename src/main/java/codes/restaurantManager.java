package codes;


import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class restaurantManager {

    List<restaurant> restaurantList = new ArrayList<>();
    private Set<String> CategoryTypes = new HashSet<>(); // res category

    private int restaurantsAdded = 0;
    private static final String RESTAURANT = "restaurant.txt";
    private static final String MENU = "menu.txt";

    public restaurantManager(){}
//    public restaurantManager(restaurantManager r){
//
//    }

    public restaurantManager(List<restaurant> resList)
    {
        for(var temp: resList)
        {
            addRestaurants(temp);
        }
    }

    public void setList(List<restaurant> resList){
        for(var temp: resList)
        {
            addRestaurants(temp);
        }
    }

    public List<restaurant> getResList()
    {
        return restaurantList;
    }

    public int addCount()
    {
        return restaurantsAdded;
    }
    public synchronized boolean addRestaurants(restaurant r) {
        for (restaurant temp : restaurantList) {
            if (r.getName().equalsIgnoreCase(temp.getName()))
                return false;
        }
        restaurantList.add(r);
        // update the hashset, if it didn't have that certain category
        CategoryTypes.addAll(r.categories);
        ++restaurantsAdded;
        return true;
    }


//    public String showRestaurant() { // shows all res
//        String str = "";
//        for (int i = 0; i < restaurantsAdded; i++) {
//            restaurant t = restaurantList.get(i);
//            str = str + t.show();
//        }
//        return str;
//    }

    public List<restaurant> searchName(String name) {

        List<restaurant>foundRes = new ArrayList();
        for (restaurant t : restaurantList) {
            //if (t.getName().equalsIgnoreCase(name))
            if (t.getName().toLowerCase().contains(name.toLowerCase())) {
                foundRes.add(t);
            }
        }
        return foundRes;
    }

    public List<restaurant>  searchScore(double score1, double score2) { // take a range ====> edit this later
        List<restaurant>foundRes = new ArrayList();
        for (restaurant t : restaurantList) {
            if (t.getScore() >= score1 && t.getScore() <= score2) {
                foundRes.add(t);

            }
        }
        return foundRes;
    }

    public List<restaurant> searchCategory(String Cat) {
        List<restaurant>foundRes = new ArrayList();

        for (restaurant restaurant : restaurantList) {
            int index = restaurant.hasCategory(Cat);
            if (index != -1) {
                foundRes.add(restaurant);
            }
        }
        return foundRes;

    }

    public List<restaurant> searchPrice(String price) {
        List<restaurant>foundRes = new ArrayList();

        for (restaurant t : restaurantList) {
            if (t.getPrice_range().equalsIgnoreCase(price)) {
                foundRes.add(t);
            }
        }
        return foundRes;
    }

    public List<restaurant> searchZip(int zip) {
        List<restaurant>foundRes = new ArrayList();
        for (restaurant t : restaurantList) {
            if (t.getZip() == zip) {
                foundRes.add(t);
            }

        }
        return foundRes;
    }

    public String showCatWise() // shows all restaurants
    {
        String str = "";
        for (String j : CategoryTypes) // iterate through the hashset of categories
        {
            if (!j.isEmpty()) {
                str = str + j + ": ";
                boolean flag = false;
                // through the list of res
                for (restaurant restaurant : restaurantList) {

                    List<String> temp = restaurant.getCategories(); // keep the category list of res(i)
                    if (temp.contains(j)) {
                        if (flag) {
                            str = str + ", ";
                        }

                        str = str + restaurant.getName();
                        flag = true;
                    }
                }
                str = str + "\n";
            }
        }
        return str;

    }

    public List<food> searchFoodName(String FoodName) {
        //String str = "";
        List<food>foundFood = new ArrayList();

        for (restaurant t : restaurantList) {
            foundFood.addAll(t.hasFood(FoodName));
        }
//        if (str.isEmpty())
//            str = str + "No foods with this name.\n";
        return foundFood;
    }

    public String searchFoodinRes(String FoodName, String ResName) {
        String str = "";
        for (restaurant restaurant : restaurantList) {
            if (restaurant.getName().equalsIgnoreCase(ResName)) {
                restaurant t = restaurant;
                str = str + t.hasFood(FoodName);
                return str;

            }
        }
        str = str + "No such food item with this name on the menu of this restaurant\n";
        return str;
    }

    public List<food> searchFoodCategory(String FoodCat) {
        String str = "";
        List<food> foundFood = new ArrayList();

        for (restaurant t : restaurantList) {
            foundFood.addAll(t.hasFoodCat(FoodCat));
        }
//        if (str.isEmpty())
//            str = str + "No such food item with this category.\n";
        return foundFood;
    }

    public String searchFoodCategoryinRes(String FoodCat, String Res) {
        String str = "";
        for (restaurant restaurant : restaurantList) {
            if (restaurant.getName().toLowerCase().contains(Res.toLowerCase())) {
                restaurant t = restaurant;
                str = str + t.hasFoodCat(FoodCat);
            }
        }
        if (str.isEmpty())
            str = str + "No such food with this category on the menu of this restaurant.\n";
        return str;
    }

    public List<food>  searchFoodInPrice(double lower, double upper) {
        //String str = "";
        List<food> foundFood = new ArrayList();

        for (restaurant t : restaurantList) {
            foundFood.addAll(t.inPricerange(lower, upper));
            //str = str + t.inPricerange(lower, upper);
        }
//        if (str.isEmpty())
//            str = str + "No such food item with this price range.";
        return foundFood;
    }

    public String searchFoodInPriceInRes(double lower, double upper, String ResName) {
        // int flag=0;
        String str = "";
        for (restaurant restaurant : restaurantList) {
            if (restaurant.getName().equalsIgnoreCase(ResName)) {
                restaurant t = restaurant;
                str = str + t.inPricerange(lower, upper);
            }

        }
        if (str.isEmpty())
            str = str + "No such food item with this price range on the menu of this restaurant.";
        return str;
    }

    public String searchPriciest(String ResName) {
        //int flag = 0;
        String str = "";
        for (restaurant restaurant : restaurantList) {
            if (restaurant.getName().equalsIgnoreCase(ResName)) {
                restaurant t = restaurant;
                str = str + t.priciest();
                return str;
            }

        }
        str = str + "No food item found.\n";
        return str;
    }

    public String showFoodCounts() {
        String str = "";
        for (restaurant t : restaurantList) {
            str = str + t.getName() + ": " + t.FoodsAdded + "\n";
        }
        return str;
    }

    public int addFoodToRes(String ResName, String FoodName, String category, Double price) {
        int flag = 0;
        for (restaurant restaurant : restaurantList) {
            if (restaurant.getName().equalsIgnoreCase(ResName)) {
                flag = 2;  //res exists
                if (searchFoodinRes(FoodName, ResName).isEmpty()) {
                    restaurant t = restaurant;
                    int id = restaurant.getId();
                    t.addFoodItem(new food(id, category, FoodName, price));
                    flag = 1; //res exists and food is successfully added
                    return flag;
                }

            }

        }
        return flag;
    }

   




}
