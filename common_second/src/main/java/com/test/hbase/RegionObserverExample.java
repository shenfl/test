package com.test.hbase;

import org.apache.hadoop.hbase.CoprocessorEnvironment;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.coprocessor.ObserverContext;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessor;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.coprocessor.RegionObserver;
import org.apache.hadoop.hbase.wal.WALEdit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

/**
 * @author shenfl
 */
public class RegionObserverExample implements RegionObserver, RegionCoprocessor {

    private Logger log = LoggerFactory.getLogger(RegionObserverExample.class);

    public RegionObserverExample() {

    }

    @Override
    public Optional<RegionObserver> getRegionObserver() {
        return Optional.of(this);
    }

    @Override
    public void start(CoprocessorEnvironment env) throws IOException {
        log.info("启动:{}", env.toString());
    }

    @Override
    public void stop(CoprocessorEnvironment env) throws IOException {
        log.info("停止:{}", env.toString());
    }

    @Override
    public void postPut(ObserverContext<RegionCoprocessorEnvironment> c, Put put, WALEdit edit, Durability durability)
            throws IOException {
        log.info("post put {},c : {},edit: {},durability: {}", put.toJSON(), c, edit, durability);
    }

    @Override
    public void postDelete(ObserverContext<RegionCoprocessorEnvironment> c, Delete delete, WALEdit edit,
                           Durability durability) throws IOException {
        log.info("post delete {},c : {},edit: {},durability: {}", delete.toJSON(), c, edit, durability);
    }
}