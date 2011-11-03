/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dependency;

import java.util.Comparator;

/**
 *
 * @author Rudi
 */
public class ChannelComparator implements Comparator<Channel>{

    @Override
    public int compare(Channel o1, Channel o2) {
        int i = o1.getSamplingRate() - o2.getSamplingRate();
        return i;
    }
    
}
