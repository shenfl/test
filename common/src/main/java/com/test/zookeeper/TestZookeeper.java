package com.test.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by shenfl on 2018/6/13
 * https://blog.csdn.net/qq_17089617/article/details/77922277
 */
public class TestZookeeper {
    //zookeeper地址
    private  String CONNECT_ADDR = "0.0.0.0:2181";
    //session超时时间
    private  int SESSION_OUTTIME = 2000 ; //ms
    //信号量，阻塞程序执行，用于等待zookeeper链接成功，发送成功信号
    private  CountDownLatch countDown = new CountDownLatch(1);
    private ZooKeeper zoo = null;
    public TestZookeeper(){
        try {
            zoo = new ZooKeeper(CONNECT_ADDR, SESSION_OUTTIME, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    //获取事件状态
                    Event.KeeperState state = event.getState();
                    Event.EventType eventType = event.getType();
                    if(Event.KeeperState.SyncConnected == state){
                        if(Event.EventType.None == eventType ){ // 刚链接时，没有任何节点
                            //如果连接成功，则发送信号量，让后续阻塞程序向下执行
                            System.out.println("客户端连接服务器成功。。。。");
                            countDown.countDown();
                        }
                    }
                }
            });
            //阻塞程序，等待客户端连接成功
            countDown.await();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建根节点信息
     * @param path 根节点路径
     * @param data 节点数据
     * @param dataMode 数据存储的类型
     * @throws KeeperException
     * @throws InterruptedException
     */
    public  void createRootNode(String path , String data , CreateMode ...dataMode)
            throws KeeperException, InterruptedException{
        Stat stat = zoo.exists(path, false);
        if(stat != null) return;
        //创建父节点节点
        String msg = zoo.create(
                path, // 节点路径，不能递归创建
                data.getBytes(), // 只能是字节数组类型
                ZooDefs.Ids.OPEN_ACL_UNSAFE, // 节点权限类型
                dataMode.length == 0 ? CreateMode.PERSISTENT : dataMode[0]// 节点类型，持久化和临时节点+有序节点
        );
        System.out.println(path+"节点创建成功。。。。");
        System.out.println(msg);
    }

    /**
     * 递归创建子节点
     * @param path 子节点全路径
     * @param data 子节点内容信息
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void createChilrenNode(String path , String data)
            throws KeeperException, InterruptedException{
        String[] paths = path.split("/");
        String rootPath = paths[0]+"/"+paths[1]; //根节点路径
        Stat stat = zoo.exists(rootPath, false);
        if(stat == null){ //如果根节点路径不存在，则创建
            createRootNode(rootPath,"");
        }
        for(int i=2;i<paths.length;i++){
            String chilrenPath = getChildrenPath(paths , i);
            createRootNode(chilrenPath,data);
        }
    }

    /**
     * 递归创建节点
     * @param path 节点全路径
     * @param nodeData 每个节点对应的数据，当节点的数据小于节点路径，缺少几个从根节点开始补空
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void createChilrenNode(String path , String[] nodeData)
            throws KeeperException, InterruptedException{
        String[] paths = path.split("/");
        String[] nodeDatas = minusNodeDate(paths , nodeData);
        String rootPath = paths[0]+"/"+paths[1]; //根节点路径
        String rootData = nodeDatas[1];
        Stat stat = zoo.exists(rootPath, false);
        if(stat == null){ //如果根节点路径不存在，则创建
            createRootNode(rootPath,rootData);
        }
        for(int i=2;i<paths.length;i++){
            String chilrenPath = getChildrenPath(paths , i);
            createRootNode(chilrenPath,nodeDatas[i]);
        }
    }

    /**
     * 取得节点对应的数据，如果节点数据小于节点路径，之前的节点数据为空处理
     * @param paths 节点的全路径
     * @param nodesDate 每个节点对应的节点数据
     * @return 所有节点数据
     */
    public String[] minusNodeDate(String[] paths , String[] nodesDate){
        if(paths.length  == nodesDate.length )
            return nodesDate;
        if(paths.length < nodesDate.length )
            throw new ZookeeperRunTimeException("子节点数据多于节点路径");
        String[] tempNode = new String[paths.length];
        int minus = paths.length - nodesDate.length;
        for(int i=0;i<minus;i++){
            tempNode[i] = "";
        }
        for(int i=0 ; i<nodesDate.length;i++){
            tempNode[minus++] = nodesDate[i];
        }
        return tempNode;
    }

    /**
     * 获取到子节点的全路径
     * @param paths
     * @param index
     * @return
     */
    public String getChildrenPath(String[] paths , int index ){
        StringBuffer sb = new StringBuffer();
        for(int i=1 ; i<=index;i++){
            sb.append("/").append(paths[i]);
        }
        return sb.toString();
    }

    /**
     * 获取节点的数据
     * @param nodePath
     * @throws InterruptedException
     * @throws KeeperException
     * @return
     */
    public byte[] getNodeData(String nodePath)
            throws KeeperException, InterruptedException{
        Stat sata = zoo.exists(nodePath, false);
        if(sata == null ){
            throw new ZookeeperRunTimeException("该节点不存在", nodePath);
        }
        byte[] data = zoo.getData(nodePath, false, null);
        return data;
    }

    /**
     * 得到所有节点数据
     * @param nodePath 节点路径
     * @return 所有节点的内容
     * @throws KeeperException
     * @throws InterruptedException
     */
    public Object[] getAllNodeData (String nodePath)
            throws KeeperException, InterruptedException{
        String[] nodePaths = nodePath.split("/");
        Object[] nodeDates = new Object[nodePaths.length-1];
        for(int i=1;i<nodePaths.length;i++){
            String nodeTempPath = getChildrenPath(nodePaths, i);
            nodeDates[i-1] = getNodeData(nodeTempPath);
        }
        return nodeDates;
    }

    /**
     * 判断节点是否存在
     * @param nodePath  节点路径
     * @return true为存在该子节点
     * @throws KeeperException
     * @throws InterruptedException
     */
    public boolean isExistNode(String nodePath)
            throws KeeperException, InterruptedException{
        Stat stat = zoo.exists(nodePath, false);
        if(stat == null){
            return false;
        }
        return true;
    }

    /**
     * 删除某一个子节点（只能删除子节点）
     * @param nodePath 子节点路径
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void deleteNode(String nodePath)
            throws KeeperException, InterruptedException{
        if(isExistNode(nodePath)){
            List<String> children = zoo.getChildren(nodePath, false);
            if(children != null && !children.isEmpty() ){
                System.out.println("该节点不是子节点，无法删除");
                return;
            }
            String data = new String(getNodeData(nodePath));
            zoo.delete(nodePath, -1); // -1删除所有版本
            System.out.println("删除成功:"+nodePath+":"+data);
        }else{
            System.out.println("没有该子节点:"+nodePath);
        }
    }

    /**
     * 删除根节点
     * @param nodePath 根节点
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void deleteAllNode(String nodePath)
            throws KeeperException, InterruptedException{
        List<String> nodes = new ArrayList<String>();
        getChildrenNode(nodePath,nodes);
        nodes.add(0,nodePath); // 将根节点放入容器中
        Collections.reverse(nodes); // 倒置
        for(String node : nodes){ //递归删除所有子节点
            deleteNode(node);
        }
    }

    /**
     * 得到根节点下的所有子节点全路径
     * @param nodePath 根节点路径
     * @param list 存放数据的集合（如果根节点/a 下有/b、/c这两个子节点，/b下有/b1、b2两个子节点，/c有 /c1子节点）
     *        </br>的值将会有 /a/c/c1,/a/c,/a/b/b2,/a/b/b1,/a/c,/a/b
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void getChildrenNode(String nodePath ,List<String> list)
            throws KeeperException, InterruptedException{
        List<String> nodes = zoo.getChildren(nodePath, false);
        for(int i = 0 ; i<nodes.size();i++){
            String node = nodes.get(i);
            list.add(nodePath+"/"+node);
            getChildrenNode(nodePath+"/"+node,list);
        }
        return;
    }

    /**
     * 设置子节点的值
     * @param nodePath 子节点的路径
     * @param data 子节点需要修改的值
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void setNodeData(String nodePath , String data)
            throws KeeperException, InterruptedException{
        zoo.setData(nodePath, data.getBytes(), -1);
    }

    /**
     * 修改所有节点的数据内容
     * @param nodePath 节点全路径
     * @param data 节点对应的数据内容（数组的最后一个值对应，nodePath的最后一个节点）
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void setAllNodeData(String nodePath , String[] data )
            throws KeeperException, InterruptedException{
        String[] nodePaths = nodePath.split("/");
        StringBuffer sb = new StringBuffer();
        if(nodePaths.length == data.length ){
            for(int i=1;i<nodePath.length() ;i++){
                sb.append("/").append(nodePaths[i]);
                setNodeData(sb.toString(),data[i]);
            }
        }
        List<String> list = getNodePath(nodePath);
        Collections.reverse(list);
        for(int i=data.length-1;i>=0;i--){
            setNodeData(list.get(data.length -1 - i ),data[i]);
        }
    }

    /**
     * 得到所有节点的全路径路径
     * @param nodePath  节点路径
     * @return 如果nodePath值为：/a/b/c  将返回：/a /a/b /a/b/c
     */
    public List<String> getNodePath(String nodePath){
        List<String> list = new ArrayList<String>();
        StringBuffer sb = new StringBuffer();
        String temp[] = nodePath.split("/");
        for(int i=1;i<temp.length;i++){
            sb.append("/").append(temp[i]);
            list.add(sb.toString());
        }
        return list;
    }

    /**
     *  异步删除子字节
     * @param nodePath  节点路径
     */
    public void deleteCallBack(String nodePath){
        zoo.delete(nodePath, -1, new AsyncCallback.VoidCallback() {

            @Override
            public void processResult(int rc, String path, Object ctx) {
                /**rc:为服务端响应码0表示调用成功，-4表示端口连接，-110表示执行节点存在，112表示会话已过期。
                 * path:接口调用时传入API的数据节点的路径参数
                 * ctx：为调用接口传入API的cxt值name：实际在服务器端创建节点的名称
                 **/
                System.out.println(rc);
                System.out.println(path);
                System.out.println(ctx);
            }
        }, "aa");
    }

    /**
     * 释放连接
     * @throws InterruptedException
     */
    public void close() throws InterruptedException{
        zoo.close();
    }

    public static void main(String[] args) throws Exception {

        TestZookeeper zooBase = new TestZookeeper();
		zooBase.createRootNode("testRoot","hello world");

//		zooBase.createChilrenNode("/hello/adai/b","hello adai b");
//
//		zooBase.createChilrenNode("/hello/adai/c","hello adai c");
//		zooBase.createChilrenNode("/a/b/c",new String[] {"a","b","c"});

//		System.out.println(new String ( zooBase.getNodeData("/a/b/c") ) );
//
//		zooBase.createChilrenNode("/a/b1/c1",new String[] {"c1"});
//		System.out.println( zooBase.isExistNode("/a/b/c1") );
//		zooBase.getAllNodeData("/a/b/c1");

//		zooBase.deleteNode("/a");
//		List<String> list = new ArrayList<String>();
//		zooBase.getChildrenNode("/a",list);
//		zooBase.setNodeData("/a", "aac");
//		zooBase.deleteAllNode("/a");
//		zooBase.setAllNodeData("/a/b/c", new String[]{"e","f"});

        zooBase.deleteCallBack("/hello");
        zooBase.close();
    }
    static class ZookeeperRunTimeException extends RuntimeException{
        private static final long serialVersionUID = 1L;
        private static String msgErr = "客户端操作zookeeper出现异常";

        public ZookeeperRunTimeException(){
            super(msgErr);
        }

        public ZookeeperRunTimeException(String mesErr,Object ...ars){
            super(mesErr);
            for(int i=0;i<ars.length;i++){
                System.out.println("ars["+(i+1)+"]:"+ars[i].toString());
            }
        }
    }
}
