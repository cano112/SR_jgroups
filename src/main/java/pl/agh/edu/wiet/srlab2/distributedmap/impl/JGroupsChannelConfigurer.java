package pl.agh.edu.wiet.srlab2.distributedmap.impl;

import org.jgroups.JChannel;
import org.jgroups.Receiver;
import org.jgroups.protocols.*;
import org.jgroups.protocols.pbcast.*;
import org.jgroups.stack.ProtocolStack;

import java.net.InetAddress;

public class JGroupsChannelConfigurer {

    public static JChannel initialize(String clusterName, Receiver receiver) throws Exception {
        JChannel channel = new JChannel(false);
        configureProtocolStack(channel);
        channel.setReceiver(receiver);
        channel.setDiscardOwnMessages(false);
        channel.connect(clusterName);
        return channel;
    }

    private static void configureProtocolStack(JChannel channel) throws Exception {
        if(channel != null) {
            ProtocolStack stack = new ProtocolStack();
            channel.setProtocolStack(stack);
            stack.addProtocol(new UDP().setValue("mcast_group_addr", InetAddress.getByName("230.0.0.237")))
                    .addProtocol(new PING())
                    .addProtocol(new MERGE3())
                    .addProtocol(new FD_SOCK())
                    .addProtocol(new FD_ALL()
                            .setValue("timeout", 12000)
                            .setValue("interval", 3000))
                    .addProtocol(new VERIFY_SUSPECT())
                    .addProtocol(new BARRIER())
                    .addProtocol(new NAKACK2())
                    .addProtocol(new UNICAST3())
                    .addProtocol(new STABLE())
                    .addProtocol(new GMS())
                    .addProtocol(new UFC())
                    .addProtocol(new MFC())
                    .addProtocol(new FRAG2())
                    .addProtocol(new STATE_TRANSFER());
            stack.init();
        }
    }
}
