package com.castile.casspringbootweb.demos.zk;

import com.castile.common.zk.client.CuratorZkClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author castile
 * @date 2024-12-24 00:12
 */
@RestController
@RequestMapping("/zk")
@Slf4j
public class ZkController {
    @Autowired
    private CuratorZkClient zkClient;

    @GetMapping("/getForPath")
    public List<String> getForPath(@RequestParam("path") String path) {

        try {
            List<String> results = zkClient.getCurator().getChildren().forPath(path);
            return results;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/createOrUpdate")
    public String createOrUpdate(@RequestParam("path") String path, @RequestParam("data") String data) throws Exception {
        if (StringUtils.isBlank(path)) {
            log.error("Create or update path is blank");
            return null;
        }
        Stat exist = zkClient.getCurator().checkExists().forPath(path);
        if (null != exist) {
            log.warn("Create or update path exists");
            return null;
        }
        log.info("Create or update for path: {}", path);
        //2、创建节点, curator客户端开发提供了Fluent风格的API，是一种流式编码方式，可以不断地点.点.调用api方法
        //创建永久节点（默认就是持久化的）
        return zkClient.getCurator().create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path, data.getBytes());
    }
}
