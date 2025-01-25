import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    private List<Block> chain; 
    private int difficulty; // leading zeros required in the hash

    // Constructor to initialize the blockchain with a given difficulty level
    public Blockchain(int difficulty) {
        this.chain = new ArrayList<>(); 
        this.difficulty = difficulty; 
        chain.add(createGenesisBlock()); // Add the genesis block (first block in the chain)
    }

    private Block createGenesisBlock() {
        return new Block(0, "Genesis Block", "0"); 
    }

    // Method to get the latest block in the blockchain
    public Block getLatestBlock() {
        return chain.get(chain.size() - 1); 
    }

    // Method to add a new block with transactions to the blockchain
    public void addBlock(String transactions) {
        Block newBlock = new Block(chain.size(), transactions, getLatestBlock().getHash()); 
        newBlock.mineBlock(difficulty); 
        chain.add(newBlock); // Add the mined block to the chain
    }

    // Method to retrieve the entire blockchain
    public List<Block> getChain() {
        return chain; // Return the list of all blocks in the blockchain
    }

    // Method to validate the integrity of the blockchain
    public boolean isChainValid() {
        // Loop through the chain to check each block
        for (int i = 1; i < chain.size(); i++) {
            Block current = chain.get(i); // Get the current block
            Block previous = chain.get(i - 1); // Get the previous block

            // Check if the current block's hash is valid
            if (!current.getHash().equals(current.calculateHash())) {
                return false; // Invalid hash, the chain is broken
            }
            // Check if the current block's previous hash matches the previous block's hash
            if (!current.getPreviousHash().equals(previous.getHash())) {
                return false; // Invalid previous hash, the chain is broken
            }
            // Check if the current block's hash starts with the required number of zeros (difficulty)
            if (!current.getHash().startsWith(new String(new char[difficulty]).replace('\0', '0'))) {
                return false; // Invalid hash, the block doesn't meet the mining difficulty
            }
        }
        return true; // If all blocks are valid, return true
    }

    // Method to repair the blockchain from a specific index onwards
    public void repairChainFromIndex(int index) {
        for (int i = index; i < chain.size(); i++) {
            Block currentBlock = chain.get(i); // Get the current block
            if (i > 0) {
                currentBlock.setPreviousHash(chain.get(i - 1).getHash());
            }
            currentBlock.mineBlock(difficulty);
        }
    }
}
