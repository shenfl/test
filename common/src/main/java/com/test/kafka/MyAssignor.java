package com.test.kafka;

import org.apache.kafka.clients.consumer.internals.PartitionAssignor;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.TopicPartition;

import java.nio.ByteBuffer;
import java.util.*;

/**
 * Created by shenfl on 2018/8/20
 */
public class MyAssignor implements PartitionAssignor {

    public MyAssignor() {
    }

    /**
     * 用于提供本地信息，例如ip或者机架等
     * @param topics
     * @return
     */
    @Override
    public Subscription subscription(Set<String> topics) {
        return new Subscription(new ArrayList<>(topics), ByteBuffer.wrap("234".getBytes()));
    }

    @Override
    public Map<String, Assignment> assign(Cluster metadata, Map<String, Subscription> subscriptions) {
        Map<String, Assignment> result = new HashMap<>();
        boolean flag = false;
        List<String> customers = new ArrayList<>();
        for (Map.Entry<String, Subscription> entry : subscriptions.entrySet()) {
            String customerId = entry.getKey();
            customers.add(customerId);
            Subscription subscription = entry.getValue();
            ByteBuffer userData = subscription.userData();
            if (userData.equals(ByteBuffer.wrap("234".getBytes()))) {
                System.out.println("aaa");
                result.put(customerId, new Assignment(Arrays.asList(new TopicPartition("shenfl", 0),
                        new TopicPartition("shenfl", 1))));
                flag = true;
                break;
            }
        }
        if (!flag) {
            if (customers.size() == 1) {
                System.out.println("bbb");
                result.put(customers.get(0), new Assignment(Arrays.asList(new TopicPartition("shenfl", 0),
                        new TopicPartition("shenfl", 1))));
            } else {
                System.out.println("ccc");
                result.put(customers.get(0), new Assignment(Arrays.asList(new TopicPartition("shenfl", 0))));
                result.put(customers.get(1), new Assignment(Arrays.asList(new TopicPartition("shenfl", 1))));
            }
        } else {
            for (String customer : customers) {
                if (!result.containsKey(customer)) {
                    result.put(customer, null);
                }
            }
        }
        return result;
    }

    @Override
    public void onAssignment(Assignment assignment) {
        // no-ope
    }

    @Override
    public String name() {
        return "shenfl";
    }

    private static List<TopicPartition> partitions(String topic, int numPartitions) {
        List<TopicPartition> partitions = new ArrayList<>(numPartitions);
        for (int i = 0; i < numPartitions; i++)
            partitions.add(new TopicPartition(topic, i));
        return partitions;
    }
}
