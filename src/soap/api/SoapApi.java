/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soap.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

public class SoapApi {

  
    public static void main(String[] args) {
          
         Soap ss= new Soap("sdsa");
         ss.sap();
         String tt = ss.getSession();
         System.out.println(tt);
         UUID uuid = UUID.randomUUID();
         String randomUUIDString = uuid.toString();
         System.out.println(randomUUIDString);
         
   
    }
}
