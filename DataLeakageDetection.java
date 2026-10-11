import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.Scanner;

/**
 * Data Leakage Detection System (alteration-based file tracking).
 *
 * Registers a confidential file by storing its SHA-256 hash as a baseline,
 * then re-hashes the file later to detect any modification or deletion.
 *
 * Requires Java 17+ (uses java.util.HexFormat).
 */
public class DataLeakageDetection {

    // Generate SHA-256 hash for a file
    public static String calculateHash(Path file) throws Exception {
        byte[] fileData = Files.readAllBytes(file);
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(fileData);
        return HexFormat.of().formatHex(hashBytes);
    }

    private static boolean isRegularFile(Path file) {
        return Files.exists(file) && Files.isRegularFile(file);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("======================================");
        System.out.println(" DATA LEAKAGE DETECTION SYSTEM");
        System.out.println(" Alteration-Based File Tracking");
        System.out.println("======================================");

        try {
            System.out.print("Enter confidential file path: ");
            String filePath = sc.nextLine().trim();
            Path file = Paths.get(filePath);

            if (!isRegularFile(file)) {
                System.out.println("Error: File does not exist.");
                return;
            }

            // Store original hash as baseline
            String originalHash = calculateHash(file);

            System.out.println("\nFile registered successfully!");
            System.out.println("File Name: " + file.getFileName());
            System.out.println("Original SHA-256 Hash:");
            System.out.println(originalHash);

            System.out.println("\nNow modify the file externally, if desired.");
            System.out.println("Press ENTER to check file integrity.");
            sc.nextLine();

            // Check if file still exists
            if (!isRegularFile(file)) {
                System.out.println("\nSECURITY ALERT!");
                System.out.println("File has been deleted or is missing.");
                return;
            }

            // Calculate current hash
            String currentHash = calculateHash(file);

            System.out.println("\nCurrent SHA-256 Hash:");
            System.out.println(currentHash);

            // Compare original and current hashes
            if (originalHash.equals(currentHash)) {
                System.out.println("\nFILE INTEGRITY VERIFIED");
                System.out.println("No content alteration detected.");
            } else {
                System.out.println("\nSECURITY ALERT!");
                System.out.println("File alteration detected.");
                System.out.println("Potential unauthorized modification requires review.");
            }

            System.out.println("\n======================================");
            System.out.println("Detection process completed.");
            System.out.println("======================================");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}
