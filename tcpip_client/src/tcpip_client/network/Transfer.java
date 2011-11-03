/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpip_client.network;

import dependency.Channel;
import dependency.Console;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rudi
 */
public class Transfer extends TimerTask {

    private Channel channel;
    private OutputStream output;
    private Timer timer;

    public Transfer(Channel channel, OutputStream output) {
        this.channel = channel;
        this.output = output;
        timer = new Timer();
    }

    @Override
    public void run() {
        while (true) {
            synchronized (output) {
                channel.writeObject(output);
            }
            Console.setMessage("[Channel " + channel.getNumber() + "]: Data sent (" + channel.getSamplingRate() + "Hz)\n" + channel.getData());
            break;
        }
    }
}
