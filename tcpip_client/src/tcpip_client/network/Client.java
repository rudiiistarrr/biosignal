/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpip_client.network;

import dependency.Channel;
import dependency.ChannelComparator;
import dependency.Data;
import dependency.Console;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rudi
 */
public class Client {

    private Socket socket;
    private int number;
    private Timer timer;
    private List<Channel> channels;

    public Client(Socket socket) {
        this.socket = socket;
        timer = new Timer();
        channels = new ArrayList<Channel>();
    }

    public void connectToServer() {
        try {
            ScheduledThreadPoolExecutor service = new ScheduledThreadPoolExecutor(10);
            BufferedReader reader;
            boolean reconnect = false;
            OutputStream output = socket.getOutputStream();

            Console.setMessage("Connection established");

            /* Check if config file exsists */
            if (new File("config.txt").exists() == true) {
                Console.setMessage("Abgebrochene Connection wieder aufnehmen? ");
                reader = new BufferedReader(new InputStreamReader(System.in));
                if (reader.readLine().equals("y")) {
                    reconnect = true;
                }
            }

            /* Starting a new connection */
            if (!reconnect) {
                Console.setMessage("Set Channels: ");
                loadConfig();
                reader = new BufferedReader(new InputStreamReader(System.in));
                String userInput = reader.readLine();
                number = Integer.parseInt(userInput);

                /* Send: Number of Channels */
                Data.sendNumberOfChannels(output, number);

                for (int i = 0; i < number; i++) {
                    Console.setMessage("[Channel " + i + ":] Abtastrate: ");
                    String readLine = reader.readLine();
                    int samplingRate = Integer.parseInt(readLine);
                    channels.add(new Channel(i, samplingRate));
                }
            } else {

                /* Load configuration from a config file and reconnect */
                loadConfig();

                /* Send: Number of Channels */
                Data.sendNumberOfChannels(output, number);
            }

            saveConfig();

            /* Sort Channels (lowest sampling rate first) */
            Collections.sort(channels, new ChannelComparator());
            ScheduledFuture<?>[] r = new ScheduledFuture<?>[3];
            int i = 0;
            for (Channel channel : channels) {

                long delay = (long) (((double) 1 / channel.getSamplingRate()) * 1000);
                r[i] = service.scheduleWithFixedDelay(new Transfer(channel, output), 1000, (long) (((double) 1 / channel.getSamplingRate()) * 1000), TimeUnit.MILLISECONDS);
                //service.schedule(new Transfer(channel, output), delay, TimeUnit.MILLISECONDS);
                //timer.scheduleAtFixedRate(new Transfer(channel, output), 1000, (long) (((double) 1 / channel.getSamplingRate()) * 1000));
                i++;
            }
            List<Runnable> shutdownNow = service.shutdownNow();
            System.out.println(shutdownNow.size());
            return;

        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
            System.out.println("Test1");
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void saveConfig() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("config.txt"));
            writer.write(String.valueOf(number) + "\r\n");
            writer.flush();
            for (Channel channel : channels) {
                writer.write(String.valueOf(channel.getNumber()) + "\r\n");
                writer.flush();
                writer.write(String.valueOf(channel.getSamplingRate()) + "\r\n");
                writer.flush();
            }
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadConfig() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("config.txt"));
            number = Integer.parseInt(reader.readLine());
            for (int i = 0; i < number; i++) {
                int channelNumber = Integer.parseInt(reader.readLine());
                int samplingRate = Integer.parseInt(reader.readLine());
                channels.add(new Channel(channelNumber, samplingRate));
            }
            reader.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
}
