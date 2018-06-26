package com.test.lucene;

/**
 * Created by shenfl on 2018/5/30
 */
public class Runnable {
    // 索引的存放路径
    static final String indexDir = "/Users/dasouche1/IdeaProjects/test/common/data";
    // 被索引数据的路径
    static final String dataDir = "/Users/dasouche1/IdeaProjects/test/common/files";
    // 搜索内容
    static final String query = "api NOT index";

    public static void main(String[] args) {
        index();
//        search();
    }

    private static void index(){
        Indexer indexer = null;
        int numIndexed = 0;
        //startTime
        long start = System.currentTimeMillis();
        try{
            indexer = new Indexer(indexDir);
            numIndexed = indexer.index(dataDir);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                indexer.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        //endTime
        long end = System.currentTimeMillis();
        System.out.println("索引： "+ numIndexed + " 个文件， 用时： "+ (end-start));
    }

    private static void search(){
        try{
            Searcher.search(indexDir, query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}