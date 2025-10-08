import javax.swing.*;
import java.awt.*;

public class HabitTrackerGUI {

    private final JFrame frame;
    private final JPanel panel;
    private JPanel inputPanel;
    private JPanel listPanel;
    private JPanel buttonPanel;
    final HabitManager manager = new HabitManager();

    public HabitTrackerGUI() {
        frame = new JFrame("Habit Tracker");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(panel);



        JLabel dayLabel = new JLabel("Day " + manager.getDaycounter());
        dayLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        dayLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
        panel.add(dayLabel, BorderLayout.NORTH);
        dayLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        inputPanel = new JPanel(new BorderLayout(5, 5));
        JTextField habitField = new JTextField(1);
        JButton addButton = new JButton("Add Habit");
        inputPanel.add(habitField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);

        buttonPanel = new JPanel(new GridLayout(1,4,10,10));
        JButton removeButton = new JButton("Remove Habit");
        JButton markDoneButton = new JButton("Mark as Done");
        JButton nextDayButton = new JButton("Next Day");
        JButton weeklySummaryButton = new JButton("Weekly Summary");
        buttonPanel.add(removeButton);
        buttonPanel.add(markDoneButton);
        buttonPanel.add(nextDayButton);
        buttonPanel.add(weeklySummaryButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> habitList = new JList<>(listModel);
        habitList.setVisibleRowCount(8);
        habitList.setFixedCellHeight(25);
        habitList.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(habitList);
        listPanel = new JPanel(new BorderLayout());
        listPanel.add(inputPanel, BorderLayout.NORTH);
        listPanel.add(scrollPane, BorderLayout.CENTER);
        panel.add(listPanel, BorderLayout.CENTER);


        addButton.addActionListener(e -> {
            String habitName = habitField.getText();
            if(!habitName.isEmpty()) {
                manager.addHabit(habitName);
                listModel.addElement(habitName);
                habitField.setText("");
            }
            else{
                JOptionPane.showMessageDialog(null, "Please enter a habit name");
            }
        });

        removeButton.addActionListener(e -> {
            String selectedHabit = habitList.getSelectedValue();
            if(selectedHabit != null) {
                manager.removeHabit(selectedHabit);
                listModel.removeElement(selectedHabit);
            }
            else{
                JOptionPane.showMessageDialog(frame, "Please select a Habit to remove");
            }

        });

        markDoneButton.addActionListener(e -> {
            String selectedHabit = habitList.getSelectedValue();
            if(selectedHabit != null) {
                if(manager.habitInList(selectedHabit).isDone()){
                    JOptionPane.showMessageDialog(frame, selectedHabit + " has already been marked as Done");
                }
                else {
                    manager.markHabitAsComplete(selectedHabit);
                    JOptionPane.showMessageDialog(frame, selectedHabit + " marked as Done");
                }
            }
            else{
                JOptionPane.showMessageDialog(frame, "Please select a Habit to Mark as Done");
            }

        });

        nextDayButton.addActionListener(e -> {

            manager.nextDay();
            dayLabel.setText("Day " + manager.getDaycounter());
            if((manager.getDaycounter()-1) % 7 == 0){
                manager.weeklySummary();
                JOptionPane.showMessageDialog(frame, manager.getSummary().toString());

            }
        });

        weeklySummaryButton.addActionListener(e -> {
            if (manager.getDaycounter() % 7 == 0){
                manager.weeklySummary();
            JOptionPane.showMessageDialog(frame, manager.getSummary().toString());
            }
        else{
            JOptionPane.showMessageDialog(frame, "You need to finish a week");
            }
        });
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }
    public static void main(String[] args) {
        new HabitTrackerGUI();
    }


}
