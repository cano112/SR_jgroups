package pl.agh.edu.wiet.srlab2.distributedmap.impl;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.MergeView;
import org.jgroups.View;
import pl.agh.edu.wiet.srlab2.distributedmap.exceptions.ChannelMergeException;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MergeViewHandler extends Thread {
    private final JChannel channel;
    private final MergeView view;

    public MergeViewHandler(JChannel channel, MergeView view) {
        this.channel = channel;
        this.view = view;
    }

    public void run() {
        int subgroupsCount = view.getSubgroups().size();
        if(subgroupsCount > 0) {
            View mainView = view.getSubgroups().get(ThreadLocalRandom.current().nextInt(0, subgroupsCount));
            Address localAddress = channel.getAddress();

            if (!mainView.getMembers().contains(localAddress)) {
                System.out.println("Not member of the new primary partition (" + mainView + "), will re-acquire the state");
                try {
                    channel.getState(null, 30000);
                } catch (Exception e) {
                    throw new ChannelMergeException(e);
                }
            }
            else {
                System.out.println("Member of the new primary partition (" + mainView + "), will do nothing");
            }
        }
    }
}
