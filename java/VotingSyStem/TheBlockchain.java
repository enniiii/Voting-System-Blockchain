package VotingSyStem;

import java.util.ArrayList;
import java.util.List;

public class TheBlockchain {
    public List<TheBlock> chain;

    public TheBlockchain() {
        chain = new ArrayList<>();
        chain.add(generateGenesisBlock());

    }
    private TheBlock generateGenesisBlock(){
        return new TheBlock("0", "Genesis Block", "0");
    }
    public void addBlock (TheBlock block) {
        chain.add(block);
    }
    public  TheBlock getLastBlock(){
        return chain.get(chain.size()-1);
    }
    public boolean validateChain() {
        for (int i = 1; i < chain.size(); i++) {
            TheBlock currentBlock = chain.get(i);
            TheBlock previousBlock = chain.get(i-1);
            if (!currentBlock.getCurrentHash().equals(currentBlock.calculateHash())) {
                return false;
            }
            if (!currentBlock.getPreviousHash().equals(previousBlock.getCurrentHash())) {
                return false;

            }
        }
        return true;
    }
    public void displayChain () {
        for (TheBlock block : chain) {
            System.out.println("Block Hash: " + block.getCurrentHash());
            System.out.println("Previous Hash: " + block.getPreviousHash());
            System.out.println("----------------");
        }
    }
}
