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
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class Soap {
    
    private String session;
    public Soap(String session){
     
    }

    public void sap() {
      
      BufferedReader rd = null;
      OutputStream outStream = null;
      InputStream is = null; 
      InputStreamReader isr = null;
      HttpURLConnection con = null;
      String line="";
      String responseLine="";
        try{
            
             URL oURL = new URL("http://gkx638-22:8080/services/SysaidApiService");
             con = (HttpURLConnection) oURL.openConnection();
             con.setRequestMethod("POST");
             con.setRequestProperty("Content-type", "text/xml; charset=utf-8");
             con.setDoOutput(true);
             String user="sysaid"; 
             String soapXml =  
                " <soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"" +
                "  xmlns:api=\"http://api.ilient.com/\">" +
                " <soapenv:Header/>" + 
                " <soapenv:Body>" +
                " <api:login>" +
                " <!--Optional:-->" +
                " <accountId>selectivedorb</accountId>" +
                " <!--Optional:-->" +
                " <userName>"+user+"</userName>" + 
                " <!--Optional:-->" +
                " <password>changeit</password>" +
                " </api:login>" +
                " </soapenv:Body>" +
                "</soapenv:Envelope>"; 
            
                outStream = con.getOutputStream();
                outStream.write(soapXml.getBytes());
                is = con.getInputStream();
                isr = new InputStreamReader(is);
                rd = new BufferedReader(isr);
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                Document doc = null;
                String lineFix="";
                int beginIndex = 0;
                int endIndex = 0;
                while ((line = rd.readLine()) != null) {
                    responseLine = responseLine+line; 
                    beginIndex = line.indexOf("<return>") + 8;
                    endIndex = line.indexOf("</return>");
                    lineFix = line.substring(beginIndex,endIndex);
                   System.out.println(line+"\n");
                   this.session=lineFix; 
       
                }
               Document document = parseXmlFile(responseLine);
               NodeList nodeLst = document.getElementsByTagName("return");
               String session = nodeLst.item(0).getTextContent().trim();
            System.out.println("session: " + session);
                
            }catch(MalformedURLException ma){
                
                System.out.print("there is issue with sap request"+ma);
                
            }catch(IOException ei){
               
                System.out.print("there is issue with sap request"+ei);
           
            }finally{
                if(rd!=null)
                   try {
                          rd.close();
                   }catch(IOException ex) {
                            Logger.getLogger(Soap.class.getName()).log(Level.SEVERE, null, ex);
                   }
                
                  if(outStream!=null)
                     try {
                            outStream.close();
                     }catch(IOException ex) {
                            Logger.getLogger(Soap.class.getName()).log(Level.SEVERE, null, ex);
                     }
                  if(is!=null)
                     try {
                            is.close();
                     }catch(IOException ex) {
                            Logger.getLogger(Soap.class.getName()).log(Level.SEVERE, null, ex);
                     }
                  if(isr!=null)
                     try {
                            isr.close();
                     }catch(IOException ex) {
                            Logger.getLogger(Soap.class.getName()).log(Level.SEVERE, null, ex);
                     }
                   if(con!=null)
                       con.disconnect();    
            }     
    }
    
    public String getSession() {
        return session;
    }
    
    private Document parseXmlFile(String in) {
        try {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                InputSource is = new InputSource(new StringReader(in));
                    return db.parse(is);
            } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
            }
    }      
    
}
