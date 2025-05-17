# Unique IPv4 Address Counter

This Java application efficiently calculates the number of unique IPv4 addresses from a large list using a memory-optimized data structure and multithreading.

---

## Components

### 1. `App` - Unique IP Counter

- Reads IPs from `src/main/resources/ips.txt`.
- Converts each IPv4 string to a 32-bit unsigned integer stored in a `long`.
- Marks presence in one of two synchronized `BitSet`s depending on the IP range.
- Uses a fixed thread pool sized to the number of CPU cores for concurrent processing.
- Outputs the count of unique IP addresses.

### 2. `IpWriter` - Test Data Generator

- Generates `10,000,000` random IPv4 addresses.
- Each IP can be duplicated between 1 to 3 times randomly.
- Writes the generated IP list to `src/main/resources/ips.txt`.
- Useful to create large input files for testing the `App`.

---

## Features

- Uses two BitSet instances to track IPv4 addresses in their full 32-bit unsigned range (0 to 4,294,967,295).
- Splits the address space into two halves to overcome BitSet index limits (int max value).
- Processes input file lines concurrently using a thread pool sized to available CPU cores.
- Thread-safe updates on shared BitSets using synchronization.
- Reads IP addresses from a resource file named ips.txt (one IP per line).
- Prints the count of unique IP addresses after processing.

---

## How it works

- Each IP address string is converted to a 32-bit unsigned integer stored in a long.
- Addresses ≤ Integer.MAX_VALUE are stored in LOWER_BIT_SET.
- Addresses > Integer.MAX_VALUE are stored in UPPER_BIT_SET with adjusted indices.
- Each IP address is marked as "seen" in the appropriate BitSet.
- The final count is the sum of the cardinalities of both BitSets.

---

## Usage

- Run `IpWriter` to generate your list of IPv4 addresses in the file: `src/main/resources/ips.txt` (One IP address per line, e.g., 192.168.1.1) or place your file.
- Run `App` to get unique number of IP addresses.

---

## Performance notes

- Uses all available CPU cores to process IPs concurrently.
- Memory usage is optimized by storing IPs as bits rather than using a full hash set or list.
- Suitable for large input files containing millions of IP addresses.

---

## Limitations

- Assumes input IPs are valid IPv4 strings.
- Uses Java standard library only.
- Maximum IPv4 range supported (32-bit unsigned space).
- Can be extended to batch input lines for fewer synchronization calls.
