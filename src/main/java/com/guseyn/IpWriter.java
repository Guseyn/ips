package com.guseyn;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class IpWriter {
    public static void main(String[] args) throws IOException {
        List<String> ipList = new ArrayList<>();

        final int numberOfUniqueIPs = 10_000_000;

        for (int i = 0; i <= numberOfUniqueIPs; i++) {
            final int first = ThreadLocalRandom.current().nextInt(0, 255);
            final int second = ThreadLocalRandom.current().nextInt(0, 255);
            final int third = ThreadLocalRandom.current().nextInt(0, 255);
            final int four = ThreadLocalRandom.current().nextInt(0, 255);
            final int numberOfDuplicates = ThreadLocalRandom.current().nextInt(1, 4);
            final StringBuffer address = new StringBuffer();
            address.append(first);
            address.append('.');
            address.append(second);
            address.append('.');
            address.append(third);
            address.append('.');
            address.append(four);
            final String addressValue = address.toString();
            for (int j = 0; j < numberOfDuplicates; j++) {
                ipList.add(addressValue);
            }
        }

        Path path = Paths.get("src/main/resources/ips.txt");
        System.out.println(ipList.size());
        Files.write(path, ipList, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        System.out.println("IP addresses written to ip.txt");
    }
}
