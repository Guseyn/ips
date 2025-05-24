# Unique IPv4 Address Counter

This Java project efficiently counts the number of **unique IPv4 addresses** from a large input file using **low memory and high performance** techniques. It's built to scale to files containing **tens or hundreds of gigabytes** of IP data.

---

## 🚀 Features

- Processes unlimited size files line-by-line using Java Streams
- Custom IP address to `int` converter (`IPToIntConvertor`)
- Efficient deduplication using a memory-optimized `BitSetContainer`
- Uses only Java standard library (no external dependencies)
- IP addresses are stored as 32-bit signed integers (`int`)
- Supports reading input file from classpath via ClassLoader

---

## 📂 Project Structure

```txt
src
└── main
├── java
│   └── com
│       └── guseyn
│           ├── App.java # Entry point: counts unique IPv4 addresses using classloader
│           ├── BitSetContainer.java  # Efficient int container using BitSet array for deduplication
│           ├── IntContainer.java  # Interface for custom integer containers
│           ├── IpWriter.java # Fast, memory-efficient converter from IPv4 string to int
│           └── IPToIntConverter.java
└── resources
└── ips.txt # Input file containing IPv4 addresses, one per line
```

## Usage

- Run `IpWriter` to generate your list of IPv4 addresses in the file: src/main/resources/ips.txt (One IP address per line, e.g., 192.168.1.1) or place your file.
- Run `App` to get unique number of IP addresses.
