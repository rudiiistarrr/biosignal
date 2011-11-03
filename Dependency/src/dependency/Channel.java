/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dependency;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rudi
 */
public class Channel {

    private int number;
    private int time;
    private int samplingRate;
    private short data;
    private byte[] buffer;
    
    public Channel(){
        
    }

    public Channel(int number, int samplingRate) {
        this.number = number;
        this.samplingRate = samplingRate;
    }
    

    public void writeObject(OutputStream output) {
        try {
            buffer = ByteBuffer.allocate(4).putInt(number).array();
            output.write(buffer, 0, 4);
            output.flush();
            
            Calendar cal = Calendar.getInstance();
            time = cal.get(Calendar.HOUR_OF_DAY) + cal.get(Calendar.MINUTE) + cal.get(Calendar.SECOND);
            buffer = ByteBuffer.allocate(4).putInt(time).array();
            output.write(buffer,0,4);
            output.flush();
            
            buffer = Data.create();
            data = ByteBuffer.wrap(buffer).getShort();
            output.write(buffer,0,2);
            output.flush();
            
            buffer = ByteBuffer.allocate(4).putInt(samplingRate).array();
            output.write(buffer, 0, 4);
            output.flush();
            
        } catch (IOException ex) {
            Logger.getLogger(Channel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int readObject(InputStream input) {
        try {
            buffer = new byte[4];
            int read = input.read(buffer, 0, 4);
            number = ByteBuffer.wrap(buffer).getInt();
            
            read = input.read(buffer, 0, 4);
            time = ByteBuffer.wrap(buffer).getInt();
            
            read = input.read(buffer, 0, 2);
            data = ByteBuffer.wrap(buffer).getShort();
            
            read = input.read(buffer,0, 4);
            samplingRate = ByteBuffer.wrap(buffer).getInt();
            
            return 1;
        }    catch(SocketException ex){
                return -1;
            }
         catch (IOException ex) {
            Logger.getLogger(Channel.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    public int getSamplingRate() {
        return samplingRate;
    }

    public int getNumber() {
        return number;
    }

    public short getData() {
        return data;
    }
    
    
    
    
    
}
