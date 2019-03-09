package com.test.es.yarn;

import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.api.records.ApplicationReport;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.exceptions.YarnException;

import java.io.IOException;
import java.util.List;

public class TestYarn {
    public static void main(String[] args) throws IOException, YarnException {
        YarnClient yarnClient = YarnClient.createYarnClient();
        YarnConfiguration configuration = new YarnConfiguration();
        yarnClient.init(configuration);
        yarnClient.start();

        // 拿到所有的application
        ApplicationId applicationId = null;
        List<ApplicationReport> applications = yarnClient.getApplications();
        for (ApplicationReport application : applications) {
            System.out.println(application.getApplicationId());
            if (application.getApplicationId().toString().equals("application_1548087621091_0063")) {
                applicationId = application.getApplicationId();
            }
        }

        // 杀掉application
        yarnClient.killApplication(applicationId);
    }
}
