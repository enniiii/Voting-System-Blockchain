package VotingSyStem;
import java.security.MessageDigest;

public class TheBlock {
    private String voterID;
    private String vote;
    private String previousHash;
    private String currentHash;
    private long timestamp;

    public TheBlock(String voterID, String vote, String previousHash) {
        this.voterID = voterID;
        this.vote = vote;
        this.previousHash = previousHash;
        this.timestamp = System.currentTimeMillis();
        this.currentHash = calculateHash();
    }

    public String calculateHash () {
        String dataToHash = voterID + vote + previousHash + timestamp;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(dataToHash.getBytes());
            StringBuilder hash = new StringBuilder();
            for (byte b : hashBytes) {
                hash.append(String.format("%02x", b));
            }
            return hash.toString();
        } catch (Exception e)   {
            throw new RuntimeException(e);
        }
    }

    public String getCurrentHash (){
        return currentHash;
    }
    public String getPreviousHash() {
        return previousHash;
    }

}
