/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpip_client.network;

import dependency.Channel;
import dependency.Console;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 *
 * @author Rudi
 */
public class Transfer implements Runnable {

    private Channel channel;
    private OutputStream output;
    private ScheduledThreadPoolExecutor service;

    public Transfer(Channel channel, OutputStream output, ScheduledThreadPoolExecutor service) {
        this.channel = channel;
        this.output = output;
        this.service = service;
    }

    @Override
    public void run() {

        synchronized (output) {
            try {
                channel.writeObject(output);
                Console.setMessage("[Channel " + channel.getNumber() + "]: Data sent (" + channel.getSamplingRate() + "Hz)\n" + channel.getData());
            } catch (SocketException ex) {
                service.shutdownNow();
            }
        }

    }
}
