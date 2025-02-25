package VotingSyStem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashSet;

public class TheVote {
    private TheBlockchain blockchain; // Manages the blockchain
    private HashSet<String> registeredVoters; // Tracks registered voters

    public TheVote() {
        blockchain = new TheBlockchain();
        registeredVoters = new HashSet<>();
        createUI();
    }

    private void createUI() {
        JFrame frame = new JFrame("Voting System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new GridLayout(6, 1));

        // Load Custom Font
        Font customFont;
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/path/to/your/font.ttf"))
                    .deriveFont(Font.PLAIN, 16);
        } catch (Exception e) {
            customFont = new Font("Arial", Font.BOLD, 16); // Fallback font
        }

        // Color Scheme
        Color backgroundColor = new Color(30, 144, 255); // Dodger Blue
        Color buttonColor = new Color(0, 102, 204); // Deep Blue
        Color textColor = Color.WHITE;

        frame.getContentPane().setBackground(backgroundColor);

        // Add Icons (Replace with your own icon paths)
        ImageIcon registerIcon = new ImageIcon("path/to/register_icon.png");
        ImageIcon voteIcon = new ImageIcon("path/to/vote_icon.png");
        ImageIcon displayIcon = new ImageIcon("path/to/display_icon.png");
        ImageIcon validateIcon = new ImageIcon("path/to/validate_icon.png");
        ImageIcon resetIcon = new ImageIcon("path/to/reset_icon.png");

        // Register Voter Panel
        JPanel registerPanel = new JPanel(new FlowLayout());
        registerPanel.setBackground(backgroundColor);
        registerPanel.setBorder(BorderFactory.createTitledBorder("Register Voter"));
        JLabel registerLabel = new JLabel("Enter Voter ID:");
        registerLabel.setFont(customFont);
        registerLabel.setForeground(textColor);
        JTextField registerField = new JTextField(20);
        registerField.setFont(customFont);
        JButton registerButton = new JButton("Register Voter", registerIcon);
        registerButton.setFont(customFont);
        registerButton.setBackground(buttonColor);
        registerButton.setForeground(textColor);
        registerPanel.add(registerLabel);
        registerPanel.add(registerField);
        registerPanel.add(registerButton);

        // Cast Vote Panel
        JPanel votePanel = new JPanel(new FlowLayout());
        votePanel.setBackground(backgroundColor);
        votePanel.setBorder(BorderFactory.createTitledBorder("Cast Vote"));
        JLabel voterIDLabel = new JLabel("Voter ID:");
        voterIDLabel.setFont(customFont);
        voterIDLabel.setForeground(textColor);
        JTextField voterIDField = new JTextField(15);
        voterIDField.setFont(customFont);
        JLabel voteLabel = new JLabel("Vote:");
        voteLabel.setFont(customFont);
        voteLabel.setForeground(textColor);
        JTextField voteField = new JTextField(10);
        voteField.setFont(customFont);
        JButton voteButton = new JButton("Cast Vote", voteIcon);
        voteButton.setFont(customFont);
        voteButton.setBackground(buttonColor);
        voteButton.setForeground(textColor);
        votePanel.add(voterIDLabel);
        votePanel.add(voterIDField);
        votePanel.add(voteLabel);
        votePanel.add(voteField);
        votePanel.add(voteButton);

        // Display Blockchain Button
        JButton displayButton = new JButton("Display Blockchain", displayIcon);
        displayButton.setFont(customFont);
        displayButton.setBackground(buttonColor);
        displayButton.setForeground(textColor);

        // Validate Blockchain Button
        JButton validateButton = new JButton("Validate Blockchain", validateIcon);
        validateButton.setFont(customFont);
        validateButton.setBackground(buttonColor);
        validateButton.setForeground(textColor);

        // Reset Button
        JButton resetButton = new JButton("Reset Form", resetIcon);
        resetButton.setFont(customFont);
        resetButton.setBackground(Color.RED);
        resetButton.setForeground(textColor);

        // Text Area for Display
        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(customFont);
        outputArea.setBackground(Color.WHITE);
        outputArea.setForeground(Color.BLACK);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // Add Components to Frame
        frame.add(registerPanel);
        frame.add(votePanel);
        frame.add(displayButton);
        frame.add(validateButton);
        frame.add(resetButton);
        frame.add(scrollPane);

        // Action Listeners
        registerButton.addActionListener((ActionEvent e) -> {
            String voterID = registerField.getText();
            if (registeredVoters.contains(voterID)) {
                JOptionPane.showMessageDialog(frame, "Voter already registered!");
            } else {
                registeredVoters.add(voterID);
                JOptionPane.showMessageDialog(frame, "Voter registered successfully!");
            }
        });

        voteButton.addActionListener((ActionEvent e) -> {
            String voterID = voterIDField.getText();
            String vote = voteField.getText();
            if (!registeredVoters.contains(voterID)) {
                JOptionPane.showMessageDialog(frame, "Voter not registered!");
            } else {
                TheBlock newBlock = new TheBlock(voterID, vote, blockchain.getLastBlock().getCurrentHash());
                blockchain.addBlock(newBlock);
                JOptionPane.showMessageDialog(frame, "Vote cast successfully!");
            }
        });

        displayButton.addActionListener((ActionEvent e) -> {
            StringBuilder chainData = new StringBuilder();
            for (int i = 0; i < blockchain.chain.size(); i++) {
                TheBlock block = blockchain.chain.get(i);
                chainData.append("Block ").append(i).append(":\n");
                chainData.append("Hash: ").append(block.getCurrentHash()).append("\n");
                chainData.append("Previous Hash: ").append(block.getPreviousHash()).append("\n");
                chainData.append("--------------------------\n");
            }
            outputArea.setText(chainData.toString());
        });

        validateButton.addActionListener((ActionEvent e) -> {
            boolean isValid = blockchain.validateChain();
            String message = isValid ? "Blockchain is valid!" : "Blockchain is not valid!";
            JOptionPane.showMessageDialog(frame, message);
        });

        resetButton.addActionListener((ActionEvent e) -> {
            registerField.setText("");
            voterIDField.setText("");
            voteField.setText("");
            outputArea.setText("");
        });

        // Show Frame
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TheVote::new);
    }
}