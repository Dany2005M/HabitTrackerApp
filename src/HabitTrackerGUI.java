import javax.swing.*;

public class HabitTrackerGUI {

    final JFrame frame;
    final JPanel panel;

    final HabitManager manager = new HabitManager();
    public HabitTrackerGUI() {
        frame = new JFrame("Habit Tracker");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        panel = new JPanel();
        frame.add(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JTextField habitField = new JTextField(1);
        JButton addButton = new JButton("Add Habit");
        JButton removeButton = new JButton("Remove Habit");
        JButton markDoneButton = new JButton("Mark as Done");
        JButton nextDayButton = new JButton("Next Day");


        panel.add(habitField);
        panel.add(addButton);
        panel.add(removeButton);
        panel.add(markDoneButton);
        panel.add(nextDayButton);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> habitList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(habitList);
        panel.add(scrollPane);

        addButton.addActionListener(e -> {
            String habitName = habitField.getText();
            if(!habitName.isEmpty()) {
                manager.addHabit(habitName);
                listModel.addElement(habitName);
                habitField.setText("");
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
                manager.markHabitAsComplete(selectedHabit);
                JOptionPane.showMessageDialog(frame, selectedHabit + " marked as Done");

            }

        });

        

    }
    public static void main() {
        new HabitTrackerGUI();
    }


}
