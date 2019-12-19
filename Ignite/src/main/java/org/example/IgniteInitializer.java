package org.example;

import org.apache.ignite.*;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.communication.tcp.TcpCommunicationSpi;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;

import java.util.Arrays;

public class IgniteInitializer {

    public void init() throws InterruptedException {
        TcpDiscoveryVmIpFinder ipFinder = new TcpDiscoveryVmIpFinder();
        ipFinder.setAddresses(Arrays.asList("172.20.0.2:47500..47509"));

        TcpDiscoverySpi discoverySpi = new TcpDiscoverySpi();
        discoverySpi.setLocalPort(47500);
        discoverySpi.setLocalPortRange(9);
        discoverySpi.setIpFinder(ipFinder);

        TcpCommunicationSpi commSpi = new TcpCommunicationSpi();
        commSpi.setLocalPort(47100);

        IgniteConfiguration cfg = new IgniteConfiguration();
        cfg.setDiscoverySpi(discoverySpi);
        cfg.setCommunicationSpi(commSpi);
        cfg.setClientMode(true);
        //cfg.setPeerClassLoadingEnabled(true);

        try (Ignite ignite = Ignition.start(cfg)) {
            IgniteCache<Integer, String> cache = ignite.getOrCreateCache("data");
            for (int i = 100; i < 110; i++) {
                cache.put(i, Integer.toString(i));
                System.out.println("Record was added " + i);
            }
            Thread.sleep(999999);
        }
    }
}
