package com.github.drsmugbrain;

import com.google.api.client.util.ArrayMap;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Brian on 14/05/2017.
 */
public class EnvVariables {

    public static Map<String, String> getEnvVariables(){
        boolean error = false;
        Map<String, String> data = new ArrayMap<>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader("src\\.env"));
            String line = reader.readLine();
            while(line != null){

                String[] tuple = line.split("=", 2);
                if(tuple.length != 2){
                    System.out.println("ERROR: Configuración en archivo .env no válida!");
                    System.exit(1);
                }
                data.put(tuple[0], tuple[1]);

                line = reader.readLine();
            }
        } catch(IOException e){
            System.out.println(e);
            error = true;
        }

        if(error){

        }
        return data;
    }

}
