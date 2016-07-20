package com.example.igeek.movies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by igeek on 7/16/16.
 */
public class HttpManager {

     static BufferedReader reader = null;
    public static String getData(String uri){

        try{
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();

            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;

            while((line = reader.readLine())!= null){

                sb.append(line);
            }return sb.toString();

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }finally{

            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }
}
