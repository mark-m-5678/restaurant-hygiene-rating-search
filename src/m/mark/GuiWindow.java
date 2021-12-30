package m.mark;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

public class GuiWindow {
    private JPanel mainPanel;
    private JTextField postcodeInput;
    private JButton confirmButton;
    private JPanel inputPanel;
    private JTextArea outputField;
    private JScrollPane outputScrollPane;

    public GuiWindow() {
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (postcodeInput.getText().replaceAll("\\s", "").length() < 3 && postcodeInput.getText().length() != 0) {
                    // Search input is too short
                    outputField.setText("Please enter at least 3 characters to search for");
                } else if (postcodeInput.getText().length() != 0) {
                    // Get output
                    try {
                        String postcodeSearch = postcodeInput.getText().toUpperCase(Locale.ROOT).replaceAll("[^0-9a-zA-Z]", "").replaceAll("\\s", "");
                        ApiClient client = new ApiClient(postcodeSearch);
                        JSONArray response = client.getResponse();
                        StringBuilder output = new StringBuilder();
                        output.append("Results for postcode: ").append(postcodeSearch).append("\n");
                        output.append("\n");
                        for (int i = 0; i < response.length(); i++) {
                            output.append("---------------------------------------------------").append("\n");
                            JSONObject business = response.getJSONObject(i);
                            output.append("Business name: ").append(business.getString("BusinessName")).append("\n");
                            output.append("Address: ").append(business.getString("AddressLine2"));
                            output.append(", ").append(business.getString("AddressLine3")).append(", ").append(business.getString("PostCode")).append("\n");
                            output.append("Rating: ").append(business.getString("RatingValue")).append("/5").append("\n");
                            output.append("Rating date: ").append(business.getString("RatingDate")).append("\n");
                        }
                        // Set output panel
                        outputField.setText(output.toString());
                    } catch (FuckedUpException exception) {
                        // Error handling things
                    }
                } else if (postcodeInput.getText().length() == 0) {
                    // Input field is null
                    outputField.setText("Search cannot be empty");
                }
            }
        });
    }

    public static void show() {
        JFrame frame = new JFrame("Food Hygiene Rating Lookup");
        frame.setContentPane(new GuiWindow().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(600, 400));
        frame.pack();
        frame.setVisible(true);
    }
}
