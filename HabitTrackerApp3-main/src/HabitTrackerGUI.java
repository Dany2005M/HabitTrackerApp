import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class HabitTrackerGUI {

    private final JFrame frame;
    private final JPanel panel;
    private final JPanel inputPanel;
    private final JPanel listPanel;
    private final JPanel buttonPanel;
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
        PlaceholderTextField habitField = new PlaceholderTextField("Please type the habit you want to add", 15);
        JButton addButton = new JButton("Add Habit");
        inputPanel.add(habitField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);

        buttonPanel = new JPanel(new GridLayout(1,4,10,10));
        JButton removeButton = new JButton("Remove Habit");
        JButton markDoneButton = new JButton("Mark as Done");
        JButton nextDayButton = new JButton("Next Day");
        buttonPanel.add(removeButton);
        buttonPanel.add(markDoneButton);
        buttonPanel.add(nextDayButton);
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
            String rawHabitName = selectedHabit.replaceAll("\\s*✅$", "").trim();
            int selectedIndex = habitList.getSelectedIndex();
            if(manager.habitInList(rawHabitName) != null) {
                if(manager.habitInList(rawHabitName).isDone()){
                    JOptionPane.showMessageDialog(frame, manager.habitInList(rawHabitName).getName() + " has already been marked as Done");
                }
                else {
                    manager.markHabitAsComplete(selectedHabit);
                    String doneHabit = selectedHabit + " ✅";
                    listModel.set(selectedIndex, doneHabit);
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


        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }
    public static void main(String[] args) {
        new HabitTrackerGUI();
    }


}

class PlaceholderTextField extends JTextField {

    private String placeholder;
    private boolean placeholderShown;

    public PlaceholderTextField(String placeholder, int columns){
        super(columns);
        this.placeholder = placeholder;
        showPlaceholder();



        this.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e){
                if(placeholderShown){
                    setText("");
                    setForeground(Color.BLACK);
                    placeholderShown= false;
                }
            }
            @Override
            public void focusLost(FocusEvent e){

                if(getText().isEmpty()){
                    showPlaceholder();
                }

            }
    });
}

private void showPlaceholder(){
        setText(placeholder);
        setForeground(Color.GRAY);
        placeholderShown = true;
}
@Override
public String getText(){
        return placeholderShown ? "" : super.getText();
}

}
