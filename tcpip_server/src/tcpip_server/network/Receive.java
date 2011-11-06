/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpip_server.network;

import dependency.Channel;
import dependency.Console;
import java.io.InputStream;
import java.net.SocketException;

/**
 *
 * @author Rudi
 */
public class Receive implements Runnable {

    private Channel channel;
    private InputStream input;

    public Receive(Channel channel, InputStream input) {
        this.channel = channel;
        this.input = input;
    }

    @Override
    public void run() {
        boolean run = true;
        while (run) {
            synchronized (input) {
                try {
                    channel.readObject(input);
                    Console.setMessage("[Channel " + channel.getNumber() + "]: Data sent (" + channel.getSamplingRate() + "Hz)\n" + channel.getData());
                }
                catch(SocketException ex){
                    run = false;
                }
            }
        }
    }
}
