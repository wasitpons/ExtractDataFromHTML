/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extractdatafromhtml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormatSymbols;
import org.jsoup.*;
import java.util.regex.*;  

/**
 *
 * @author Wasitpon
 */
public class ExtractDataFromHTML {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
           BufferedReader reader=null;  
           File file;
           int numberOfData = 0;
           String rawData = "",output = "";
           String stripped,line,tmp;
           String arrSplit[],rawSplit[];
           
    try {
        file = new File("tester.html");
        reader = new BufferedReader(new InputStreamReader(
                      new FileInputStream(file), "UTF-8"));
        
        while ((line = reader.readLine()) != null) {    rawData += line;    }
    } 
    catch (IOException e) {
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //System.out.println(rawData);
        rawSplit = rawData.split("<tbody>");
        for (int newline = 0; newline < rawSplit.length; newline++) {
            stripped = Jsoup.parse(rawSplit[newline]).text();
            arrSplit = stripped.split(" ");
            numberOfData = 0;
            //System.out.print(arrSplit.length + " : ");
           
            if(Pattern.matches("(�Ҥ|ภาค)(.*)", arrSplit[0]))    {
                
                if(arrSplit[0].equals("�Ҥ����֡�ҵ�|ภาคการศึกษาต้น"))          {   output += "1 ";     }
                else if(arrSplit[0].equals("�Ҥ����֡�һ���|ภาคการศึกษาปลาย"))   {   output += "2 ";     }
                else if(arrSplit[0].equals("�ҤĴ���͹|ภาคฤดูร้อน"))         {   output += "3 ";     }
                
                output += String.valueOf( Integer.parseInt(arrSplit[2])-543 ) + " ";
                
                newline++;
                stripped = Jsoup.parse(rawSplit[newline]).text();
                arrSplit = stripped.split(" ");
                tmp = "";
                
                for(int count=0; count<arrSplit.length; count++)  {
                    if(isStringNumeric(arrSplit[count]))    
                    {   
                        numberOfData++; 
                        if(numberOfData % 2 == 0 ){
                            tmp += arrSplit[count] + " ";
                            count++;
                            tmp += arrSplit[count] + " ";
                        }
                        else    {
                            tmp += arrSplit[count] + " ";
                        }
                    }
                    
                }
                tmp = tmp.substring(0,tmp.length()-1);
                output += String.valueOf(numberOfData/2) + System.lineSeparator() + tmp + System.lineSeparator();
                
                              
            }
        }
        
        System.out.println(output); 
    }
    public static boolean isStringNumeric( String str )
    {
        if(str.isEmpty())   return false;
        DecimalFormatSymbols currentLocaleSymbols = DecimalFormatSymbols.getInstance();
        char localeMinusSign = currentLocaleSymbols.getMinusSign();

        if ( !Character.isDigit( str.charAt( 0 ) ) && str.charAt( 0 ) != localeMinusSign ) return false;

        boolean isDecimalSeparatorFound = false;
        char localeDecimalSeparator = currentLocaleSymbols.getDecimalSeparator();

        for ( char c : str.substring( 1 ).toCharArray() )
        {
            if ( !Character.isDigit( c ) )
            {
                if ( c == localeDecimalSeparator && !isDecimalSeparatorFound )
                {
                    isDecimalSeparatorFound = true;
                    continue;
                }
                return false;
            }
        }
        return true;
    }
}
