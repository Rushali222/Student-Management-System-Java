package ui;

import dao.StudentDAO;
import model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class SwingAdminDashboard extends JFrame {

    private final StudentDAO dao = new StudentDAO();
    private JTable table;
    private DefaultTableModel tableModel;

    private final JTextField txtId = new JTextField();
    private final JTextField txtName = new JTextField();
    private final JTextField txtAge = new JTextField();
    private final JTextField txtBranch = new JTextField();
    private final JTextField txtEmail = new JTextField();
    private final JTextField txtPhone = new JTextField();
    private final JTextField txtSearch = new JTextField();

    public SwingAdminDashboard() {
        setTitle("Student Management System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
        loadTable();
    }

    private void initUI() {
        // Left form panel
        JPanel leftPanel = new JPanel(new GridLayout(13, 1, 5, 5));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        txtId.setEditable(false);

        leftPanel.add(new JLabel("ID:"));
        leftPanel.add(txtId);

        leftPanel.add(new JLabel("Name:"));
        leftPanel.add(txtName);

        leftPanel.add(new JLabel("Age:"));
        leftPanel.add(txtAge);

        leftPanel.add(new JLabel("Branch:"));
        leftPanel.add(txtBranch);

        leftPanel.add(new JLabel("Email:"));
        leftPanel.add(txtEmail);

        leftPanel.add(new JLabel("Phone:"));
        leftPanel.add(txtPhone);

        // Buttons
        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");

        JPanel btnPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        btnPanel.add(btnAdd);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);

        // Table
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Age", "Branch", "Email", "Phone"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // prevent editing in table directly
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);

        // Search area
        JPanel topSearchPanel = new JPanel(new BorderLayout(5, 5));
        topSearchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topSearchPanel.add(txtSearch, BorderLayout.CENTER);
        JButton btnSearch = new JButton("Search");
        JButton btnRefresh = new JButton("Refresh");
        JPanel searchBtns = new JPanel(new GridLayout(1, 2, 5, 5));
        searchBtns.add(btnSearch);
        searchBtns.add(btnRefresh);
        topSearchPanel.add(searchBtns, BorderLayout.EAST);

        // Layout
        setLayout(new BorderLayout());
        add(leftPanel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
        add(topSearchPanel, BorderLayout.NORTH);

        // Event listeners
        btnAdd.addActionListener(e -> onAdd());
        btnUpdate.addActionListener(e -> onUpdate());
        btnDelete.addActionListener(e -> onDelete());
        btnSearch.addActionListener(e -> onSearch());
        btnRefresh.addActionListener(e -> {
            txtSearch.setText("");
            loadTable();
        });

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtId.setText(String.valueOf(tableModel.getValueAt(row, 0)));
                    txtName.setText(String.valueOf(tableModel.getValueAt(row, 1)));
                    txtAge.setText(String.valueOf(tableModel.getValueAt(row, 2)));
                    txtBranch.setText(String.valueOf(tableModel.getValueAt(row, 3)));
                    txtEmail.setText(String.valueOf(tableModel.getValueAt(row, 4)));
                    txtPhone.setText(String.valueOf(tableModel.getValueAt(row, 5)));
                }
            }
        });
    }

    private void loadTable() {
        List<Student> list = dao.getAllStudents();
        tableModel.setRowCount(0);
        for (Student s : list) {
            tableModel.addRow(new Object[]{
                    s.getId(), s.getName(), s.getAge(), s.getBranch(), s.getEmail(), s.getPhone()
            });
        }
    }

    private boolean validateInputs(boolean requireId) {
        if (requireId && txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a record first.");
            return false;
        }
        String name = txtName.getText().trim();
        String ageStr = txtAge.getText().trim();
        String email = txtEmail.getText().trim();
        String phone = txtPhone.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name is required.");
            return false;
        }
        try {
            int age = Integer.parseInt(ageStr);
            if (age < 0 || age > 150) {
                JOptionPane.showMessageDialog(this, "Enter a valid age.");
                return false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Age must be a number.");
            return false;
        }
        if (!email.contains("@") || email.length() < 5) {
            JOptionPane.showMessageDialog(this, "Enter a valid email.");
            return false;
        }
        if (phone.length() < 6) {
            JOptionPane.showMessageDialog(this, "Enter a valid phone number.");
            return false;
        }
        return true;
    }

    private void clearForm() {
        txtId.setText("");
        txtName.setText("");
        txtAge.setText("");
        txtBranch.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
    }

    private void onAdd() {
        if (!validateInputs(false)) return;
        try {
            Student s = new Student(
                    txtName.getText().trim(),
                    Integer.parseInt(txtAge.getText().trim()),
                    txtBranch.getText().trim(),
                    txtEmail.getText().trim(),
                    txtPhone.getText().trim()
            );

            boolean ok = dao.addStudent(s);
            if (ok) {
                loadTable();
                clearForm();
                JOptionPane.showMessageDialog(this, "Student added successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add student.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding student: " + e.getMessage());
        }
    }

    private void onUpdate() {
        if (!validateInputs(true)) return;
        try {
            Student s = new Student(
                    Integer.parseInt(txtId.getText().trim()),
                    txtName.getText().trim(),
                    Integer.parseInt(txtAge.getText().trim()),
                    txtBranch.getText().trim(),
                    txtEmail.getText().trim(),
                    txtPhone.getText().trim()
            );

            boolean ok = dao.updateStudent(s);
            if (ok) {
                loadTable();
                clearForm();
                JOptionPane.showMessageDialog(this, "Student updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Update failed.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating student: " + e.getMessage());
        }
    }

    private void onDelete() {
        String idStr = txtId.getText().trim();
        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Select a record to delete.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this student?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            int id = Integer.parseInt(idStr);
            boolean ok = dao.deleteStudent(id);
            if (ok) {
                loadTable();
                clearForm();
                JOptionPane.showMessageDialog(this, "Student deleted.");
            } else {
                JOptionPane.showMessageDialog(this, "Delete failed.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error deleting student: " + e.getMessage());
        }
    }

    private void onSearch() {
        String keyword = txtSearch.getText().trim();
        List<Student> list = keyword.isEmpty() ? dao.getAllStudents() : dao.searchStudents(keyword);

        tableModel.setRowCount(0);
        for (Student s : list) {
            tableModel.addRow(new Object[]{
                    s.getId(), s.getName(), s.getAge(), s.getBranch(), s.getEmail(), s.getPhone()
            });
        }
    }
}
