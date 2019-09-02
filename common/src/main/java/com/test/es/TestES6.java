package com.test.es;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.plugin.analysis.ik.AnalysisIkPlugin;
import org.elasticsearch.plugin.analysis.ik.CreateModelFromSetAction;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class TestES6 {
    public static void main(String[] args) throws Exception {
        Properties props = loadProp("set.properties");
        System.out.println(props);

        Settings settings = buildSettings(propsToMap(props));
        TransportClient client = new PreBuiltTransportClient(settings, AnalysisIkPlugin.class);
        Properties addr = loadProp("addresses.properties");
        Map<String, Integer> addressPortMap = buildAddressPortMap(propsToMap(addr));


        for (Map.Entry<String, Integer> entry : addressPortMap.entrySet()) {
            try {
                client.addTransportAddress(new TransportAddress(InetAddress.getByName(entry.getKey()), entry.getValue()));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        XContentBuilder jsonBuilder = XContentFactory.jsonBuilder();

        CreateModelFromSetAction.CreateModelFromSetRequestBuilder builder = CreateModelFromSetAction.INSTANCE.newRequestBuilder(client);
        builder.routing("dd").withoutVersion("aa", "a", "1");
        CreateModelFromSetAction.CreateModelFromSetResponse response = builder.get();
        System.out.println(response);

    }
    public static Properties loadProp(String file) throws IOException {
        ClassLoader classLoader = TestES6.class.getClassLoader();
        InputStream stream = classLoader.getResourceAsStream(file);
        Properties props = new Properties();
        props.load(new InputStreamReader(stream, "UTF-8"));
        return props;
    }

    public static Map<String, String> propsToMap(Properties properties) {
        Map<String, String> map = null;
        Set<Map.Entry<Object, Object>> set = properties.entrySet();
        if (set != null && set.size() > 0) {
            map = new HashMap<String, String>();
            for (Map.Entry<Object, Object> entry : set) {
                map.put(entry.getKey().toString(), entry.getValue().toString());
            }
        }
        return map;
    }

    public static Settings buildSettings(Map<String, String> settings) {
        Settings.Builder builder = Settings.builder();
        for (Map.Entry<String, String> entry : settings.entrySet()) {
            if (entry.getValue().equalsIgnoreCase("false")) {
                builder.put(entry.getKey(), Boolean.parseBoolean(entry.getKey()));
            }
            if (entry.getValue().equalsIgnoreCase("true")) {
                builder.put(entry.getKey(), Boolean.parseBoolean(entry.getKey()));
            }
            builder.put(entry.getKey(), entry.getValue());
        }
        builder.put("client.transport.sniff", true);
        return builder.build();
    }

    public static Map<String, Integer> buildAddressPortMap(Map<String, String> addressAndPort) {
        Map<String, Integer> addresses = new HashMap<String, Integer>();
        for (Map.Entry<String, String> entry : addressAndPort.entrySet()) {
            String val = entry.getValue();
            String[] addrAndPort = val.split(":");
            if (addrAndPort != null && addrAndPort.length == 2) {
                addresses.put(addrAndPort[0], Integer.parseInt(addrAndPort[1]));
            }
        }
        return addresses;
    }
}
