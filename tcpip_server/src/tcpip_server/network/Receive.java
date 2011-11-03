/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpip_server.network;

import dependency.Channel;
import dependency.Console;
import java.io.InputStream;

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
        while (true) {
            synchronized (input) {
                if (channel.readObject(input) == -1) {
                    Console.setMessage("Failed");
                    break;
                }

            }
            Console.setMessage("[Channel " + channel.getNumber() + "]: Data sent (" + channel.getSamplingRate() + "Hz)\n" + channel.getData());
            break;
        }
    }
}
