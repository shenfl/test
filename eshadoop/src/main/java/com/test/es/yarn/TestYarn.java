package com.test.es.yarn;

import org.apache.hadoop.yarn.api.protocolrecords.GetNewApplicationResponse;
import org.apache.hadoop.yarn.api.records.*;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.apache.hadoop.yarn.client.api.YarnClientApplication;
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
//        yarnClient.killApplication(applicationId);

        // 拿到所有的queue信息
        QueueInfo queueInfo = yarnClient.getQueueInfo("default");
        System.out.println("Queue info"
                + ", queueName=" + queueInfo.getQueueName()
                + ", queueCurrentCapacity=" + queueInfo.getCurrentCapacity()
                + ", queueMaxCapacity=" + queueInfo.getMaximumCapacity()
                + ", queueApplicationCount=" + queueInfo.getApplications().size()
                + ", queueChildQueueCount=" + queueInfo.getChildQueues().size());
        List<QueueUserACLInfo> listAclInfo = yarnClient.getQueueAclsInfo();
        for (QueueUserACLInfo aclInfo : listAclInfo) {
            for (QueueACL userAcl : aclInfo.getUserAcls()) {
                System.out.println("User ACL Info for Queue"
                        + ", queueName=" + aclInfo.getQueueName()
                        + ", userAcl=" + userAcl.name());
            }
        }

        // 创建app
        YarnClientApplication app = yarnClient.createApplication();
        GetNewApplicationResponse appResponse = app.getNewApplicationResponse();
        int maxMem = appResponse.getMaximumResourceCapability().getMemory();//container最大内存
        System.out.println(maxMem);
        int maxVCores = appResponse.getMaximumResourceCapability().getVirtualCores();//container最大core
        System.out.println("Max virtual cores capabililty of resources in this cluster " + maxVCores);

        ApplicationSubmissionContext appContext = app.getApplicationSubmissionContext();
        ApplicationId appId = appContext.getApplicationId();
        System.out.println(appId);
    }
}
