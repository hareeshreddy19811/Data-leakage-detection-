# Data Leakage Detection System

A Java console program that tracks a confidential file by its SHA-256 hash and
alerts you if the file is later modified or deleted.

## How it works

1. You enter the path of a confidential file
2. The program computes its SHA-256 hash and stores it as the baseline
3. You modify (or leave) the file, then press ENTER
4. The program re-hashes the file and compares it with the baseline:
   - **Same hash**: integrity verified, no alteration detected
   - **Different hash**: security alert, the file was altered
   - **File missing**: security alert, the file was deleted or moved

## Project structure

```
data-leakage-detection/
├── src/
│   └── DataLeakageDetection.java
├── .gitignore
└── README.md
```

## Requirements

- Java Development Kit (JDK) **17 or newer** (the program uses `java.util.HexFormat`)

## Build and run

```bash
git clone https://github.com/<your-username>/data-leakage-detection.git
cd data-leakage-detection
javac -d out src/DataLeakageDetection.java
java -cp out DataLeakageDetection
```

## Example

```
======================================
 DATA LEAKAGE DETECTION SYSTEM
 Alteration-Based File Tracking
======================================
Enter confidential file path: secret.txt

File registered successfully!
File Name: secret.txt
Original SHA-256 Hash:
3a7bd3e2360a3d29eea436fcfb7e44c735d117c42d1c1835420b6b9942dd4f1b

Now modify the file externally, if desired.
Press ENTER to check file integrity.
```

## Limitations

- The baseline hash is kept in memory only, so it is lost when the program exits
- It detects that a file changed, not who changed it or whether it was copied
  or leaked elsewhere
- The whole file is read into memory, so it is not suited to very large files

## License

MIT
