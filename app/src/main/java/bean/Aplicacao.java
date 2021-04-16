package bean;

import java.util.ArrayList;

public class  Aplicacao{

    public static void main(String[] args) {

//        String string = NetworkUtils.getJSONFromAPI("http://localhost:8080/services/store");
//        System.out.println(string);

        HttpService http =  new HttpService();
        http.execute();

    }

}