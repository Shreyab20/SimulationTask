import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.security.MessageDigest;

public class Block {
    private int index; // Position of the block 
    private String timestamp; // Timestamp when the block is created
    private String transactions; //transactions stored in the block
    private String previousHash; // Hash of the previous block 
    private String hash; //  hash of Current block
    private int nonce; // A counter used for mining to achieve the desired hash

    // Constructor to initialize a new block
    public Block(int index, String transactions, String previousHash) {
        this.index = index; 
        this.transactions = transactions; 
        this.previousHash = previousHash; 
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); // Record the current timestamp
        this.nonce = 0; 
        this.hash = calculateHash(); 
    }

    // Method to calculate the hash of the block using SHA-256
    public String calculateHash() {
        // Combine block properties to create a unique string for hashing
        String dataToHash = index + timestamp + transactions + previousHash + nonce;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256"); // Create SHA-256 hash function
            byte[] hashBytes = digest.digest(dataToHash.getBytes()); // Generate hash bytes
            StringBuilder hexString = new StringBuilder(); 
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString(); // Return the final hash as a string
        } catch (Exception e) {
            throw new RuntimeException(e); 
        }
    }

    // Method to mine the block by finding a hash with the required difficulty
    public void mineBlock(int difficulty) {
        // Create a target string of 'difficulty' zeros
        String target = new String(new char[difficulty]).replace('\0', '0');
        // Increment nonce until the hash matches the target
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++; // Increment nonce to try a new hash
            hash = calculateHash(); // Recalculate hash with the new nonce
        }
        System.out.println("Block mined: " + hash); // Output the mined block's hash
    }

    // Getters for block properties
    public int getIndex() {
        return index;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getTransactions() {
        return transactions;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getHash() {
        return hash;
    }

    // Method to update the transactions and recalculate the hash
    public void setTransactions(String transactions) {
        this.transactions = transactions; 
        this.hash = calculateHash(); 
    }

    // Method to update the previous hash and recalculate the hash
    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash; 
        this.hash = calculateHash(); 
    }
}
