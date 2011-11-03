/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpip_client.network;

import dependency.Channel;
import dependency.Console;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rudi
 */
public class Transfer implements Runnable {

    private Channel channel;
    private OutputStream output;

    public Transfer(Channel channel, OutputStream output) {
        this.channel = channel;
        this.output = output;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1 / channel.getSamplingRate());
                synchronized (output) {
                    channel.writeObject(output);
                }
                Console.setMessage("[Channel " + channel.getNumber() + "]: Data sended (" + channel.getSamplingRate() + "MHz)\n" + channel.getData());
            } catch (InterruptedException ex) {
                Logger.getLogger(Transfer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
