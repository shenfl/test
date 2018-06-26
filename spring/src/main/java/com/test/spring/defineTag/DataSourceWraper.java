package com.test.spring.defineTag;

import javax.sql.DataSource;

public class DataSourceWraper {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
