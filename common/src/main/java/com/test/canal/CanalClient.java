package com.test.canal;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.twelvemonkeys.lang.StringUtil;

import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by shenfl on 2018/5/17
 */
public class CanalClient {
    public static void main(String[] args) {
        // 创建链接
//        CanalConnector connector = CanalConnectors.newSingleConnector(
//                new InetSocketAddress("172.17.40.234", 11111),
//                "index_schema", "", "");

        CanalConnector connector = CanalConnectors.newClusterConnector("0.0.0.0:2181", "example", "", "");

        int batchSize = 1000;
        int emptyCount = 0;
        try {
            connector.connect();
            connector.subscribe(".*\\..*");
            connector.rollback();
            int totalEmptyCount = 10000000;
            while (emptyCount < totalEmptyCount) {
                Message message = connector.getWithoutAck(batchSize, 500l, TimeUnit.MILLISECONDS); // 获取指定数量的数据
                long batchId = message.getId();
                int size = message.getEntries().size();
                if (batchId == -1 || size == 0) {
                    emptyCount++;
                    System.out.println("empty count : " + emptyCount);
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                    }
                } else {
                    emptyCount = 0;
                    // System.out.printf("message[batchId=%s,size=%s] \n", batchId, size);
                    printEntry(message.getEntries());
                }

                connector.ack(batchId); // 提交确认
                // connector.rollback(batchId); // 处理失败, 回滚数据
            }

            System.out.println("empty too many times, exit");
        } finally {
            connector.disconnect();
        }
    }
    private static void printEntry(List<CanalEntry.Entry> entrys) {
        for (CanalEntry.Entry entry : entrys) {
            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN || entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                continue;
            }

            CanalEntry.RowChange rowChage = null;
            try {
                rowChage = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),
                        e);
            }

            CanalEntry.EventType eventType = rowChage.getEventType();
            System.out.println(String.format("================&gt; binlog[%s:%s] , name[%s,%s] , eventType : %s",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                    eventType));

            for (CanalEntry.RowData rowData : rowChage.getRowDatasList()) {
                if (eventType == CanalEntry.EventType.DELETE) {
                    printColumn(rowData.getBeforeColumnsList());
                } else if (eventType == CanalEntry.EventType.INSERT) {
                    printColumn(rowData.getAfterColumnsList());
                } else {
                    System.out.println("-------&gt; before");
                    printColumn(rowData.getBeforeColumnsList());
                    System.out.println("-------&gt; after");
                    printColumn(rowData.getAfterColumnsList());
                }

                if (eventType == CanalEntry.EventType.DELETE) {
                    Map<String, Object> data = new HashMap<>();
                    for (CanalEntry.Column column : rowData.getBeforeColumnsList()) {
                        data.put(column.getName(), parseValueByMysqlType(column.getValue(), column.getMysqlType()));
                        if (column.getIsKey()) {
                            System.out.println(column.getName());
                        }
                    }
                    System.out.println("data: " + data);
                } else if (eventType == CanalEntry.EventType.INSERT) {
                    Map<String, Object> data = new HashMap<>();
                    for (CanalEntry.Column column : rowData.getAfterColumnsList()) {
                        data.put(column.getName(), parseValueByMysqlType(column.getValue(), column.getMysqlType()));
                        if (column.getIsKey()) {
                            System.out.println(column.getName());
                        }
                    }
                    System.out.println("data: " + data);
                } else {
                    Map<String, Object> data = new HashMap<>();
                    List<String> updatedColumnNames = new ArrayList<>();
                    for (CanalEntry.Column column : rowData.getAfterColumnsList()) {
                        if (column.getUpdated()) {
                            updatedColumnNames.add(column.getName());
                        }
                        data.put(column.getName(), parseValueByMysqlType(column.getValue(), column.getMysqlType()));
                        if (column.getIsKey()) {
                            System.out.println(column.getName());
                        }
                    }
                    System.out.println("data: " + data);

                    Map<String, Object> old = new HashMap<>();
                    for (CanalEntry.Column column : rowData.getBeforeColumnsList()) {
                        if (updatedColumnNames.contains(column.getName())) {
                            old.put(column.getName(), parseValueByMysqlType(column.getValue(), column.getMysqlType()));
                        }
                    }
                    System.out.println("old: " + old);
                }

            }
        }
    }

    private static Object parseValueByMysqlType(String value, String mysqlType) {
        if (StringUtil.isEmpty(value)) {
            return null;
        }
        try {
            if (mysqlType.indexOf("bigint") > -1) {
                return Long.parseLong(value);
            } else if (mysqlType.indexOf("int") > -1) {
                return Integer.parseInt(value);
            } else if (mysqlType.indexOf("double") > -1) {
                return Double.parseDouble(value);
            } else if (mysqlType.indexOf("float") > -1) {
                return Float.parseFloat(value);
            } else if (mysqlType.indexOf("datetime") > -1) {
                return value;
            } else if (mysqlType.indexOf("date") > -1) {
                return value;
            } else {
                return value;
            }
        } catch (Exception e) {
            return value;
        }
    }

    private static void printColumn(List<CanalEntry.Column> columns) {
        for (CanalEntry.Column column : columns) {
            System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
        }
    }
}
