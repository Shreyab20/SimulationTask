import java.util.Scanner;

public class BlockchainSimulation {
    public static void main(String[] args) {
        Blockchain blockchain = new Blockchain(4);

        // Add blocks
        blockchain.addBlock("Transaction 1");
        blockchain.addBlock("Transaction 2");

        // Print blockchain
        System.out.println("\nBlockchain:");
        blockchain.getChain().forEach(block -> System.out.println(
            "Index: " + block.getIndex() +
            ", Timestamp: " + block.getTimestamp() +
            ", Transactions: " + block.getTransactions() +
            ", Hash: " + block.getHash() +
            ", Previous Hash: " + block.getPreviousHash()
        ));

        // Validate chain
        System.out.println("\nIs blockchain valid? " + blockchain.isChainValid());

        // Tampering functionality
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nDo you want to tamper with the blockchain? (yes/no): ");
        String userResponse = scanner.nextLine().trim().toLowerCase();

        if (userResponse.equals("yes")) {
            System.out.println("\nEnter the index of the block you want to tamper with: ");
            int indexToTamper = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (indexToTamper >= 0 && indexToTamper < blockchain.getChain().size()) {
                System.out.println("Enter the new transaction data for block " + indexToTamper + ": ");
                String newTransaction = scanner.nextLine();

                // Tamper with the block
                blockchain.getChain().get(indexToTamper).setTransactions(newTransaction);

                // Repair the chain
                blockchain.repairChainFromIndex(indexToTamper);

                // Print blockchain after tampering
                System.out.println("\nBlockchain after tampering:");
                blockchain.getChain().forEach(block -> System.out.println(
                    "Index: " + block.getIndex() +
                    ", Timestamp: " + block.getTimestamp() +
                    ", Transactions: " + block.getTransactions() +
                    ", Hash: " + block.getHash() +
                    ", Previous Hash: " + block.getPreviousHash()
                ));

                // Validate chain after tampering
                System.out.println("\nIs blockchain valid after tampering? " + blockchain.isChainValid());
            } else {
                System.out.println("Invalid block index!");
            }
        } else {
            System.out.println("\nNo tampering performed. Exiting.");
        }

        scanner.close();
    }
}
