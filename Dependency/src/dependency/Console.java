/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dependency;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rudi
 */
public class Console {

    private static DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
    private static BufferedReader reader;

    public static void setMessage(String message) {
        Date today = Calendar.getInstance().getTime();
        String time = sdf.format(today);
        System.out.println("[" + time + "]: " + message);
    }

    public static void setInputMessage(String message) {
        Date today = Calendar.getInstance().getTime();
        String time = sdf.format(today);
        System.out.print("[" + time + "]: " + message);
    }

    public static String getInput() {
        try {
            reader = new BufferedReader(new InputStreamReader(System.in));
            return reader.readLine();
        } catch (IOException ex) {
            Logger.getLogger(Console.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
