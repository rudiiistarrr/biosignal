/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpip_server.console;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Rudi
 */
public class Output {
    
    private static DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
    
    public static void setMessage(String message){
        Date today = Calendar.getInstance().getTime();
        String time = sdf.format(today);
        System.out.println("["+time+"]: "+ message);
    }

    
    
    
}
