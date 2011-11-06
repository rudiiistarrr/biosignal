/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpip_client.network;

import dependency.Channel;
import dependency.Console;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
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
        synchronized (output) {
            try {
                channel.writeObject(output);
                Console.setMessage("[Channel " + channel.getNumber() + "]: Data sent (" + channel.getSamplingRate() + "Hz)\n" + channel.getData());
            } catch (SocketException ex) {
                return;
            }
        }

    }
}
