package currency;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class currencycontroller 
{
    private static final String API_KEY = "6aec76ec84005cf590002395";  //provided is api key 

    public static void main(String[] args) 
    {
        try
        {
        	System.out.println();
           System.out.println("CURRENCY CONVERTER TASK");
           System.out.println("************************");
           System.out.println();
           
           //taking input
            String basecurr = takeinput("Provide base currency code in uppercase (e.g., USD): ");
            String tarcurr = takeinput("Provide target currency code in uppercase (e.g., EUR): ");
            System.out.println();
            System.out.println("-------------------------------------");

           
            double Exrate = ansofexchangerate(basecurr, tarcurr);

            
            double toconvert = getamt();  //converting currency

           
            double applyconversion =currencyconversion(toconvert,Exrate);
            //  Display the result
            
            
            System.out.printf("%.2f %s is equal to %.2f %s%n",toconvert, basecurr, applyconversion, tarcurr);
                    

        } 
        catch (IOException exp) 
        {
            System.out.println("Error with API " + exp.getMessage());
        }
    }

    private static String takeinput(String val) throws IOException   //function for taking input
    {
        System.out.print(val);
        BufferedReader ans = new BufferedReader(new InputStreamReader(System.in));
        return ans.readLine();
    }

    
    
    
    //function to calculate result of exchanged rate
    private static double ansofexchangerate(String base, String target) throws IOException 
    {
        String api = String.format("https://open.er-api.com/v6/latest/%s?apikey=%s", base, API_KEY);

        URL url = new URL(api);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int getcode = con.getResponseCode();
        
        
        
        //if api fails validation check
        if (getcode != HttpURLConnection.HTTP_OK)
        {
        	throw new IOException("Failure in fetching exchange rates " + getcode);
        } 
        else
        {
        	
        	
        	BufferedReader read = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = read.readLine()) != null)
            {
                response.append(line);
            }

            
            
            
            read.close();
            con.disconnect();

           
            String jres = response.toString();
            
            
            int sind = jres.indexOf("\"" + target + "\":") + target.length() + 4;
            
            int eind = jres.indexOf(",", sind);
            
            
            return Double.parseDouble(jres.substring(sind, eind));

        	
        	
        	
        	
        	
        	
        	
            
        }
    }
    
    //getting the converted amount
    private static double getamt() throws IOException
    {
        return Double.parseDouble(takeinput("Provide Amount to Convert : "));
    }
    
    //convert currency
    private static double currencyconversion(double amount, double exchangeRate)
    {
        return amount *exchangeRate;
    }
    
    
    
}

