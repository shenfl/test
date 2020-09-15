package com.test.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.regionserver.BloomType;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * https://cloud.tencent.com/developer/article/1453282
 * @author shenfl
 */
public class TestHbase {
    private static final String TABLE_NAME = "t_user_info";

    public static void createOrOverwrite(Admin admin, HTableDescriptor table) throws IOException {
        if (admin.tableExists(table.getTableName())) {
            admin.disableTable(table.getTableName());
            admin.deleteTable(table.getTableName());
        }
        admin.createTable(table);
    }

    public static void createSchemaTables(Configuration config) throws IOException {
        try (Connection connection = ConnectionFactory.createConnection(config);
             Admin admin = connection.getAdmin()) {

            HTableDescriptor table = new HTableDescriptor(TableName.valueOf(TABLE_NAME));
            table.addFamily(new HColumnDescriptor("base_info").setMaxVersions(3));
            table.addFamily(new HColumnDescriptor("extra_info"));

            System.out.print("Creating table. ");
            createOrOverwrite(admin, table);
            System.out.println(" Done.");
        }
    }

    public static void modifySchema (Configuration config) throws IOException {
        try (Connection connection = ConnectionFactory.createConnection(config);
             Admin admin = connection.getAdmin()) {

            TableName tableName = TableName.valueOf(TABLE_NAME);
            if (!admin.tableExists(tableName)) {
                System.out.println("Table does not exist.");
                System.exit(-1);
            }

            HTableDescriptor table = admin.getTableDescriptor(tableName);

            // Update existing table
            HColumnDescriptor newColumn = new HColumnDescriptor("other_info");
            newColumn.setBloomFilterType(BloomType.ROWCOL);
            table.addFamily(newColumn);
            admin.modifyTable(TableName.valueOf(TABLE_NAME), table);
        }
    }

    public static void main(String... args) throws IOException {
        Configuration config = HBaseConfiguration.create();

        //Add any necessary configuration files (hbase-site.xml, core-site.xml)
//        config.addResource(new Path(System.getenv("HBASE_CONF_DIR"), "hbase-site.xml"));
//        config.addResource(new Path(System.getenv("HADOOP_CONF_DIR"), "core-site.xml"));
        config.set("hbase.zookeeper.quorum", "172.17.41.96:2181");  // Until 2.x.y versions
//        createSchemaTables(config);
//        modifySchema(config);
//        putData(config);
        getData(config);
//        manyPut(config);
//        scanData(config);
//        pageData(config);
//        filterData(config);
    }

    private static void filterData(Configuration config) {
        try (Connection connection = ConnectionFactory.createConnection(config);
             Table table = connection.getTable(TableName.valueOf("t_user_info"))) {
//            Filter filter = new ColumnRangeFilter("a".getBytes(), true, "ah".getBytes(), false); // 遍历所有列名在此范围内的数据
//            Filter filter = new FamilyFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes("other_info"))); // 查询包含other_info列族的数据

//            Filter filter = new ColumnRangeFilter("a".getBytes(), true, "ah".getBytes(), false);
//            filter = new FilterList(FilterList.Operator.MUST_PASS_ALL, filter, new PageFilter(4)); // 组合filter，这儿能查到包含age和addr列的数据，但是限制4条

            Filter filter = new ColumnPaginationFilter(4, "e".getBytes()); // 查询所有数据，但是要比e大的字段

            Scan scan  = new Scan();
            scan.setFilter(filter);
            ResultScanner scanner = table.getScanner(scan);
            print(scanner);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void pageData(Configuration config) {
        try (Connection connection = ConnectionFactory.createConnection(config);
             Table table = connection.getTable(TableName.valueOf("t_user_info"))) {
            Scan scan  = new Scan();

//            scan.addColumn(Bytes.toBytes("base_info"), Bytes.toBytes("username"));
            // 設置當前查询的其实位置
            scan.setStartRow(Bytes.toBytes("400"));
            // 第二个参数
            Filter pageFilter = new PageFilter(4);
            scan.setFilter(pageFilter);
            ResultScanner scanner = table.getScanner(scan);
            print(scanner);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void scanData(Configuration config) {
        try (Connection connection = ConnectionFactory.createConnection(config);
             Table table = connection.getTable(TableName.valueOf("t_user_info"))) {
            // 包含起始行键，不包含结束行键,但是如果真的想查询出末尾的那个行键，那么，可以在末尾行键上拼接一个不可见的字节（\000）
            Scan scan = new Scan("0".getBytes(), "01".getBytes());

            scan.addColumn("extra_info".getBytes(), "addr".getBytes()); // scan时可以指定获取哪些字段，不设置默认全部获取

            ResultScanner scanner = table.getScanner(scan);

            print(scanner);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void print(ResultScanner scanner) throws IOException {
        Iterator<Result> iterator = scanner.iterator();

        int count = 0;
        while(iterator.hasNext()){

            Result result = iterator.next();
            // 遍历整行结果中的所有kv单元格
            CellScanner cellScanner = result.cellScanner();
            while(cellScanner.advance()){
                Cell cell = cellScanner.current();

                byte[] rowArray = cell.getRowArray();  //本kv所属的行键的字节数组
                byte[] familyArray = cell.getFamilyArray();  //列族名的字节数组
                byte[] qualifierArray = cell.getQualifierArray();  //列名的字节数据
                byte[] valueArray = cell.getValueArray(); // value的字节数组

                System.out.println("行键: "+new String(rowArray,cell.getRowOffset(),cell.getRowLength()));
                System.out.println("列族名: "+new String(familyArray,cell.getFamilyOffset(),cell.getFamilyLength()));
                System.out.println("列名: "+new String(qualifierArray,cell.getQualifierOffset(),cell.getQualifierLength()));
                System.out.println("value: "+new String(valueArray,cell.getValueOffset(),cell.getValueLength()));
            }
            count++;
            System.out.println("----------------------");
        }
        System.out.println("Total: " + count);
    }

    private static void manyPut(Configuration config) {
        try (Connection connection = ConnectionFactory.createConnection(config);
             Table table = connection.getTable(TableName.valueOf("t_user_info"))) {
            ArrayList<Put> puts = new ArrayList<>();

            for(int i=0; i<100000; i++) {
                Put put = new Put(Bytes.toBytes("" + i));
                put.addColumn(Bytes.toBytes("base_info"), Bytes.toBytes("username"), Bytes.toBytes("张三"+i));
                put.addColumn(Bytes.toBytes("base_info"), Bytes.toBytes("age"), Bytes.toBytes((18+i)+""));
                put.addColumn(Bytes.toBytes("extra_info"), Bytes.toBytes("addr"), Bytes.toBytes("北京"));

                puts.add(put);
            }

            table.put(puts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void getData(Configuration config) {
        // 获取一个操作指定表的table对象,进行DML操作
        try (Connection connection = ConnectionFactory.createConnection(config);
             Table table = connection.getTable(TableName.valueOf("t_user_info"))) {
            Get get = new Get("002".getBytes());
//            get.addFamily("base_info".getBytes()); // 只要base_info列族的数据

            Result result = table.get(get);

            // 从结果中取用户指定的某个key的value
            byte[] value = result.getValue("base_info".getBytes(), "age".getBytes());
            System.out.println(new String(value));

            System.out.println("-------------------------");

            // 遍历整行结果中的所有kv单元格
            CellScanner cellScanner = result.cellScanner();
            while(cellScanner.advance()) {
                Cell cell = cellScanner.current();

                byte[] rowArray = cell.getRowArray();  //本kv所属的行键的字节数组
                byte[] familyArray = cell.getFamilyArray();  //列族名的字节数组
                byte[] qualifierArray = cell.getQualifierArray();  //列名的字节数据
                byte[] valueArray = cell.getValueArray(); // value的字节数组

                System.out.println("行键: "+new String(rowArray,cell.getRowOffset(),cell.getRowLength()));
                System.out.println("列族名: "+new String(familyArray,cell.getFamilyOffset(),cell.getFamilyLength()));
                System.out.println("列名: "+new String(qualifierArray,cell.getQualifierOffset(),cell.getQualifierLength()));
                System.out.println("value: "+new String(valueArray,cell.getValueOffset(),cell.getValueLength()));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void putData(Configuration config) {
        // 获取一个操作指定表的table对象,进行DML操作
        try (Connection connection = ConnectionFactory.createConnection(config);
             Table table = connection.getTable(TableName.valueOf("t_user_info"))) {

            // 构造要插入的数据为一个Put类型(一个put对象只能对应一个rowkey)的对象
            Put put = new Put(Bytes.toBytes("001"));
            put.addColumn(Bytes.toBytes("base_info"), Bytes.toBytes("username"), Bytes.toBytes("张三"));
            put.addColumn(Bytes.toBytes("base_info"), Bytes.toBytes("age"), Bytes.toBytes("18"));
            put.addColumn(Bytes.toBytes("extra_info"), Bytes.toBytes("addr"), Bytes.toBytes("北京"));


            Put put2 = new Put(Bytes.toBytes("002"));
            put2.addColumn(Bytes.toBytes("base_info"), Bytes.toBytes("username"), Bytes.toBytes("李四"));
            put2.addColumn(Bytes.toBytes("base_info"), Bytes.toBytes("age"), Bytes.toBytes("28"));
            put2.addColumn(Bytes.toBytes("extra_info"), Bytes.toBytes("addr"), Bytes.toBytes("上海"));


            ArrayList<Put> puts = new ArrayList<>();
            puts.add(put);
            puts.add(put2);


            // 插进去
            table.put(puts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
