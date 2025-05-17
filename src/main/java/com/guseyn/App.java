package com.guseyn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class App {
    private final static BitSet LOWER_BIT_SET = new BitSet(); // Handles IPs [0, Integer.MAX_VALUE]
    private final static BitSet UPPER_BIT_SET = new BitSet(); // Handles IPs [Integer.MAX_VALUE+1, 2^32-1]
    private final static int HALF = Integer.MAX_VALUE;

    private final static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

    // Track lines submitted to executor
    private static final AtomicLong linesSubmitted = new AtomicLong(0);
    // Track lines processed inside processLine()
    private static final AtomicLong linesProcessed = new AtomicLong(0);

    public static void main(String[] args) throws IOException, InterruptedException {
        final ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_CORES);

        final BufferedReader fileReader = new BufferedReader(
                new InputStreamReader(
                        App.class.getClassLoader().getResourceAsStream("ips.txt"),
                        StandardCharsets.UTF_8
                )
        );

        System.out.println("Starting to read and submit IPs for processing...");
        String line;
        while ((line = fileReader.readLine()) != null) {
            final String currentLine = line;
            executor.submit(() -> processLine(currentLine));
            long submitted = linesSubmitted.incrementAndGet();
            if (submitted % 1_000_000 == 0) {
                System.out.printf("Submitted %,d IP lines for processing...\n", submitted);
            }
        }
        fileReader.close();
        System.out.println("Finished submitting IP lines.");

        executor.shutdown();
        System.out.println("Waiting for tasks to complete...");
        boolean terminated = executor.awaitTermination(1, TimeUnit.HOURS);
        System.out.println("Executor terminated: " + terminated);

        long uniqueCount = LOWER_BIT_SET.cardinality() + UPPER_BIT_SET.cardinality();
        System.out.printf("Number of unique IPs: %,d%n", uniqueCount);
    }

    private static void processLine(String line) {
        long ip = ipToLong(line.trim());
        if (ip <= HALF) {
            synchronized (LOWER_BIT_SET) {
                LOWER_BIT_SET.set((int) ip);
            }
        } else {
            synchronized (UPPER_BIT_SET) {
                UPPER_BIT_SET.set((int) (ip - HALF - 1));
            }
        }

        long processed = linesProcessed.incrementAndGet();
        if (processed % 1_000_000 == 0) {
            System.out.printf("Processed %,d IP lines...\n", processed);
        }
    }

    private static long ipToLong(String ip) {
        String[] parts = ip.split("\\.");
        long result = 0;
        for (String part : parts) {
            result = (result << 8) + Integer.parseInt(part);
        }
        return result;
    }
}
