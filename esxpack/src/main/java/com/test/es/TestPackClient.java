package com.test.es;

import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class TestPackClient {
    public static void main(String[] args) throws IOException {
        Properties props = loadProp("set.properties");

        Settings settings = buildSettings(propsToMap(props));
        TransportClient client = new PreBuiltXPackTransportClient(settings);
        Properties addr = loadProp("addresses.properties");
        Map<String, Integer> addressPortMap = buildAddressPortMap(propsToMap(addr));


        for (Map.Entry<String, Integer> entry : addressPortMap.entrySet()) {
            try {
                client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(entry.getKey()), entry.getValue()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        XContentBuilder jsonBuilder = XContentFactory.jsonBuilder();

        // 判断index是否处在
        IndicesAdminClient indices = client.admin().indices();
        IndicesExistsResponse response = indices.prepareExists("ttt").get();
        System.out.println(response.isExists());
    }

    public static Properties loadProp(String file) throws IOException {
        ClassLoader classLoader = TestPackClient.class.getClassLoader();
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
            System.out.println();
            if (entry.getValue().equalsIgnoreCase("true")) {
                builder.put(entry.getKey(), Boolean.parseBoolean(entry.getKey()));
            }
            builder.put(entry.getKey(), entry.getValue());
        }
//        builder.put("xpack.security.user", "elastic:changeme");
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
