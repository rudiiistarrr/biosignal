/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dependency;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rudi
 */
public class Data {

    private static byte[] buffer;
    
    public static byte[] createRandomData() {
        Random r = new Random();
        buffer = new byte[2];
        r.nextBytes(buffer);
        return buffer;
    }

    public static void sendNumberOfChannels(OutputStream output, int number) {
        try {
            buffer = ByteBuffer.allocate(4).putInt(number).array();
            output.write(buffer);
            output.flush();
        } catch (IOException ex) {
            Logger.getLogger(Channel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int receiveNumberOfChannels(InputStream input) {
        try {
            buffer = new byte[4];
            input.read(buffer, 0, 4);
            return ByteBuffer.wrap(buffer).getInt();
        } catch (IOException ex) {
            Logger.getLogger(Channel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
}
