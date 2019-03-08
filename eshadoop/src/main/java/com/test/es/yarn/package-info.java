package com.test.es.yarn;
/**
 * https://github.com/neoremind/app-on-yarn-demo
 * https://han-zw.iteye.com/blog/2337435
 /usr/hdp/2.6.2.0-205/hadoop/bin/yarn jar es-hadoop-1.0-SNAPSHOT.jar com.test.es.yarn.Client -jar_path /home/souche/projects/es-hadoop-1.0-SNAPSHOT.jar -jar_path_in_hdfs hdfs://hadoop-3:8020/tmp/es-hadoop-1.0-SNAPSHOT.jar -appname DemoApp -master_memory 1024 -container_memory 128 -num_containers 3 -memory_overhead 512 -queue default -shell_args "abc 123" -java_opts "-XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintGCDateStamps -XX:+UseConcMarkSweepGC"
 * */