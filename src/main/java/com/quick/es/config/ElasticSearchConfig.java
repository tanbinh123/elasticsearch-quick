package com.quick.es.config;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author wangxc
 * @date: 2020/3/22 下午5:09
 *
 */
@Configuration
@Slf4j
public class ElasticSearchConfig {
	@Value("${es.cluster-nodes}")
	String clusterNodes;

	@Value("${es.cluster-name}")
	String clusterName;

	@Bean
	public TransportClient transportClient() throws UnknownHostException {

		// 集群名称
		Settings settings = Settings.builder().put("cluster.name", clusterName)
				.put("client.transport.sniff", false).build();
		TransportClient client = new PreBuiltTransportClient(settings);
		for (String host : clusterNodes.split(",")) {
			String a[] = host.split(":");
			client.addTransportAddress(new TransportAddress(InetAddress.getByName(a[0]), Integer.parseInt(a[1])));
		}
		return client;
	}

}
