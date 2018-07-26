package pl.agh.edu.wiet.srlab2.distributedmap.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.io.IOUtils;
import org.jgroups.*;
import pl.agh.edu.wiet.srlab2.distributedmap.SimpleStringMap;
import pl.agh.edu.wiet.srlab2.distributedmap.exceptions.ChannelInitializationException;
import pl.agh.edu.wiet.srlab2.distributedmap.exceptions.ChannelReceiveException;
import pl.agh.edu.wiet.srlab2.distributedmap.exceptions.ChannelSendException;
import pl.agh.edu.wiet.srlab2.distributedmap.exceptions.SerializationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class DistributedMap implements SimpleStringMap {
    private static String ENCODING = "UTF-8";

    private final JChannel channel;
    private final JsonStringSerializationUtils utils;
    private final Map<String, String> map;

    public DistributedMap() {
        this.map = new HashMap<>();
        this.utils = new JsonStringSerializationUtils();
        try {
            this.channel = JGroupsChannelConfigurer.initialize(
                    "DistributedMap",
                    new DistributedMap.Receiver());
            channel.getState(null, 6000);
        } catch (Exception e) {
            throw new ChannelInitializationException(e);
        }
    }

    public boolean containsKey(String key) {
        synchronized(map) {
            return map.containsKey(key);
        }
    }

    public String get(String key) {
        synchronized(map) {
            return map.get(key);
        }
    }

    public String put(String key, String value) {
        SimpleStringMapOperationDTO dto = new SimpleStringMapOperationDTO(Operation.PUT, key, value);
        synchronized(map) {
            sendSerializedOperation(dto);
            return map.get(key);
        }
    }

    public String remove(String key) {
        if(containsKey(key)) {
            SimpleStringMapOperationDTO dto = new SimpleStringMapOperationDTO(Operation.REMOVE, key);
            synchronized(map) {
                sendSerializedOperation(dto);
                return map.get(key);
            }
        } else {
            return null;
        }
    }

    private void sendSerializedOperation(SimpleStringMapOperationDTO dto) {
        try {
            channel.send(new Message(null, null, utils.serializeOperation(dto).getBytes(ENCODING)));
        } catch (JsonProcessingException e) {
            throw new SerializationException(e);
        } catch (Exception e) {
            throw new ChannelSendException(e);
        }
    }

    private void doPut(String key, String value) {
        synchronized(map) {
            map.put(key, value);
        }
    }

    private void doRemove(String key) {
        synchronized(map) {
            map.remove(key);
        }
    }

    @Override
    public String toString() {
        return map.toString();
    }

    private class Receiver extends ReceiverAdapter {

        // MESSAGE RECEIVED
        @Override
        public void receive(Message msg) {
            try {
                SimpleStringMapOperationDTO dto = utils.deserializeOperation(new String(msg.getRawBuffer(), ENCODING));

                switch(dto.getOperation()) {
                    case PUT:
                        doPut(dto.getKey(), dto.getValue());
                        break;
                    case REMOVE:
                        doRemove(dto.getKey());
                        break;
                    default:
                        throw new UnsupportedOperationException("Operation: " + dto.getOperation().name()
                                + " is not supported");
                }
            } catch (UnsupportedEncodingException | SerializationException e) {
                throw new SerializationException(e);
            } catch (IOException e) {
                throw new ChannelReceiveException(e);
            }
        }

        // VIEW CHANGED
        @Override
        public void viewAccepted(View view) {
            if(view instanceof MergeView) {
                MergeView mergeView = (MergeView) view;
                new MergeViewHandler(channel, mergeView).run();
            }
        }

        // STATE REQUESTED
        @Override
        public void getState(OutputStream output) throws Exception {
            synchronized(map) {
                output.write(utils.serializeStringMap(map).getBytes(ENCODING));
            }
        }

        // STATE RECEIVED
        @Override
        public void setState(InputStream input) throws Exception {
            String receivedState = IOUtils.toString(input, ENCODING);
            synchronized(map) {
                map.clear();
                map.putAll(utils.deserializeStringMap(receivedState));
            }
        }
    }
}
