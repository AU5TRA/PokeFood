package util;
import codes.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class FileOperations {

    public static int restaurantsAdded = 0;;
    private static final String RESTAURANT = "restaurant.txt";
    private static final String MENU = "menu.txt";

    private static final String PASSWORD_FILE_NAME = "password.txt";

    static List<restaurant> ResList = new ArrayList();

    static ConcurrentHashMap<String,String> passwordList = new ConcurrentHashMap<>();
    static ConcurrentHashMap<String,String> resPasswordList = new ConcurrentHashMap<>();

    public static List<restaurant> readResFromFile() throws IOException {


        BufferedReader br = new BufferedReader(new FileReader(RESTAURANT));
        while (true) {
            String line = br.readLine();
            if (line == null)
                break;
            String[] array = line.split(",", -1);
            ResList.add(new restaurant(array));
            restaurantsAdded++;
        }

        br.close();

        BufferedReader br2 = new BufferedReader(new FileReader(MENU));
        while (true) {
            String line = br2.readLine();
            if (line == null)
                break;
            String[] array = line.split(",", -1);
            for (int i = 0; i < restaurantsAdded; i++) {
                if (ResList.get(i).getId() == Integer.parseInt(array[0])) {
                    ResList.get(i).addFoodItem(new food(array));
                    break;
                }
            }

        }
        br2.close();
        return ResList;
    }


    public static ConcurrentHashMap<String,String> readPasswordsFromFile() throws IOException{


        BufferedReader br = new BufferedReader(new FileReader(PASSWORD_FILE_NAME));

        while (true) {
            String line = br.readLine();
            if (line == null) break;
            String usertype;
            String name;
            String password;

            String[] properties = line.split(",");
            usertype= properties[0];
            name = properties[1];
            password = properties[2];

            if(name.length()>0 ) {
                if(usertype.equals("1"))
                    passwordList.put(name, password);
                else if(usertype.equals("2"))
                    resPasswordList.put(name, password);
            }
        }
        br.close();

        return passwordList;
    }
    public static ConcurrentHashMap<String,String> getResPasswordList()
    {
        return resPasswordList;
    }

    public static void writePasswordsToFile(ConcurrentHashMap<String,String> passwordList) throws IOException{

        BufferedWriter bw = new BufferedWriter(new FileWriter(PASSWORD_FILE_NAME));

        for(String key : passwordList.keySet()){
            String text = "1,"+key + "," + passwordList.get(key);
            //System.out.println(text);
            bw.write(text);
            bw.write(System.lineSeparator());
        }
        for(String key : resPasswordList.keySet()){
            String text = "2,"+key + "," + passwordList.get(key);
            //System.out.println(text);
            bw.write(text);
            bw.write(System.lineSeparator());
        }

        bw.close();

    }





    public static void writeAllResToFile(restaurantManager RDataBase) throws IOException {


        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RESTAURANT))) {
            for (restaurant t : RDataBase.getResList()) {
                String text = t.getId() + "," + t.getName() + "," + t.getScore() + "," + t.getPrice_range() + ","
                        + t.getZip() +
                        "," + t.getCategories().get(0) + "," + t.getCategories().get(1) + "," + t.getCategories().get(2);
                bw.write(text);
                bw.write(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        try (BufferedWriter bw = new BufferedWriter(new FileWriter(MENU))) {
            for (restaurant restaurant : RDataBase.getResList()) {
                List<food> temp = restaurant.getFoodList();
                Set<String> FoodCat = restaurant.getCatSet();
                for (String str : FoodCat)
                //for (food t : temp)
                {
                    for (food t : temp) {
                        if (t.getCategory().equals(str)) {
                            String text = t.getId() + "," + t.getCategory() + "," + t.getName() + "," + t.getPrice();
                            bw.write(text);
                            bw.write(System.lineSeparator());
                        }
                    }

                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }







}
