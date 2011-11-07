/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpip_server.network;

import dependency.Channel;
import dependency.Console;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rudi
 */
public class Receive implements Runnable {

    private Channel channel;
    private InputStream input;
    private boolean output;
    private static BufferedWriter writer;
    private int counter;

    public Receive(Channel channel, InputStream input, boolean output) {
        this.channel = channel;
        this.input = input;
        this.output = output;
        try {
            writer = new BufferedWriter(new FileWriter("output.txt"));
        } catch (IOException ex) {
            Logger.getLogger(Receive.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        boolean run = true;
        while (run) {
            synchronized (input) {
                try {
                    channel.readObject(input);
                    if (output) {
                        createOutputFile();
                        counter++;
                        Console.setMessage("Daten werden in der Output Datei gespeichert " + counter);
                    } else {
                        Console.setMessage("[Channel " + channel.getNumber() + "]: Data sent (" + channel.getSamplingRate() + "Hz)\n" + channel.getData());
                    }
                } catch (SocketException ex) {
                    run = false;
                }
            }
        }
    }

    public void createOutputFile() {
        try {
             int hour = channel.getTime() / 10000;
             int min = (channel.getTime() - (hour * 10000)) / 100;
             int sek = (channel.getTime() - (hour * 10000) - (min * 100));
             writer.append(hour + ":" + min + ":" + sek + " Channel " + channel.getNumber() + "(" + channel.getSamplingRate() + "Hz) - Data: " + channel.getData());
             writer.newLine();
             writer.flush();
        } catch (IOException ex) {
            Logger.getLogger(Receive.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
