package com.guseyn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class App {

    private final static ToIntFunction<CharSequence> CONVERTOR = new IPToIntConvertor();
    private final static Supplier<IntContainer> SUPPLIER = () -> new BitSetContainer(Byte.SIZE);

    public static void main(String[] args) throws IOException {
        var classLoader = App.class.getClassLoader();
        try (var inputStream = classLoader.getResourceAsStream("ips.txt");
             var reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {

            var uniqueIPs = reader.lines()
                    .mapToInt(CONVERTOR)
                    .collect(SUPPLIER, IntContainer::add, IntContainer::addAll)
                    .countUnique();

            System.out.printf("unique number of IP adresses: %d", uniqueIPs);
        } catch (IOException | NullPointerException e) {
            System.err.println("Failed to load ips.txt: " + e.getMessage());
        }
    }
}
