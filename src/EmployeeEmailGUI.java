import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class EmployeeEmailGUI extends JFrame
{
   JTextArea  employeeTextArea   = new JTextArea ();

   JLabel     idLabel           = new JLabel     ("ID: ");
   JTextField idTextField       = new JTextField (10);
   JLabel     nameLabel         = new JLabel     ("Name: ");
   JTextField nameTextField     = new JTextField (10);

   JButton    testDataButton    = new JButton ("Test Data");
   JButton    addButton         = new JButton ("Add");
   JButton    deleteButton      = new JButton ("Delete");
   JButton    editButton        = new JButton ("Edit");
   JButton    editSaveButton    = new JButton ("Save");
   JButton    displayAllButton  = new JButton ("Display All");
   JButton    exitButton        = new JButton ("Exit");

   private LinkedList<EmployeeEmail> employeeLinkedList = new LinkedList<EmployeeEmail> ();
   private int editIndex;

   public EmployeeEmailGUI ()
   {
      JPanel flow1Panel = new JPanel (new FlowLayout (FlowLayout.CENTER));
      JPanel flow2Panel = new JPanel (new FlowLayout (FlowLayout.CENTER));
      JPanel gridPanel  = new JPanel (new GridLayout (2, 1));

      employeeTextArea.setEditable (false);

      flow1Panel.add (idLabel);
      flow1Panel.add (idTextField);
      flow1Panel.add (nameLabel);
      flow1Panel.add (nameTextField);

      flow2Panel.add (testDataButton);
      flow2Panel.add (addButton);
      flow2Panel.add (editButton);
      flow2Panel.add (editSaveButton);
      flow2Panel.add (deleteButton);
      flow2Panel.add (displayAllButton);
      flow2Panel.add (exitButton);

      gridPanel.add (flow1Panel);
      gridPanel.add (flow2Panel);

      editSaveButton.setEnabled (false);

      add (employeeTextArea, BorderLayout.CENTER);
      add (gridPanel,       BorderLayout.SOUTH);


      addButton.addActionListener        (event -> addEmployee ());
      displayAllButton.addActionListener (event -> displayAll ());
      editButton.addActionListener       (event -> editEmployee ());
      editSaveButton.addActionListener   (event -> editSaveEmployee ());
      exitButton.addActionListener       (event -> exitApplication ());
      deleteButton.addActionListener     (event -> deleteEmployee ());
      testDataButton.addActionListener   (event -> addTestData ());

      setTitle ("Employee Linked List ");
   }

   private boolean isEmployeeIdInLinkedList (String idStr)
   {
      boolean inList = false;

      for (EmployeeEmail emp : employeeLinkedList)
      {
         if (emp.getId ().compareToIgnoreCase (idStr) == 0)
         {
            inList = true;
         }
      }

      return inList;
   }

   private void addEmployee ()
   {
      if (isEmployeeIdInLinkedList (idTextField.getText()) == true)
      {
         JOptionPane.showMessageDialog (EmployeeEmailGUI.this,
                              "Error: employee ID is already in the database.");
      }
      else
      {
         try
         {
            EmployeeEmail emp = new EmployeeEmail (nameTextField.getText(),
                                                  idTextField.getText());

            employeeLinkedList.add (emp);

            displayAll ();

            nameTextField.setText("");
            idTextField.setText("");


         }
         catch (EmployeeEmailException error)
         {
            JOptionPane.showMessageDialog (EmployeeEmailGUI.this, error.toString ());
         }
      }
   }

   private void deleteEmployee ()
   {
      if (employeeLinkedList.size() == 0)
      {
         JOptionPane.showMessageDialog (EmployeeEmailGUI.this,
                                        "Error: Database is empty.");
      }
      else if (isEmployeeIdInLinkedList (idTextField.getText()) == false)
      {
         JOptionPane.showMessageDialog (EmployeeEmailGUI.this,
                                       "Error: employee ID is not in the database.");
      }
      else
      {
         for (int s = 0; s < employeeLinkedList.size(); s++)
         {
            String currId = employeeLinkedList.get (s).getId ();

            if (currId.compareToIgnoreCase (idTextField.getText()) == 0)
            {
               employeeLinkedList.remove (s);
            }
         }
         displayAll ();
         nameTextField.setText("");
         idTextField.setText("");
      }
   }
   private void editEmployee ()
   {
      if (employeeLinkedList.size() == 0)
      {
         JOptionPane.showMessageDialog (EmployeeEmailGUI.this,
                                        "Error: Database is empty.");
      }
      else if (isEmployeeIdInLinkedList (idTextField.getText()) == false)
      {
         JOptionPane.showMessageDialog (EmployeeEmailGUI.this,
                                        "Error: employee ID is not in the database.");
      }
      else
      {
         editIndex = -1;

         for (int s = 0; s < employeeLinkedList.size(); s++)
         {
            String currId = employeeLinkedList.get (s).getId ();

            if (currId.compareToIgnoreCase (idTextField.getText()) == 0)
            {
               editIndex = s;
               s = employeeLinkedList.size();
            }
         }
         if (editIndex >= 0)
         {
            editSaveButton.setEnabled   (true);
            editButton.setEnabled       (false);
            testDataButton.setEnabled   (false);
            addButton.setEnabled        (false);
            deleteButton.setEnabled     (false);
            displayAllButton.setEnabled (false);
            exitButton.setEnabled       (false);

            nameTextField.setText (employeeLinkedList.get (editIndex).getName () );
         }
      }
   }

   private void editSaveEmployee()
   {
      employeeLinkedList.get (editIndex).setName (nameTextField.getText() );
      employeeLinkedList.get (editIndex).setId   (idTextField.getText() );

      displayAll ();

      nameTextField.setText ("");
      idTextField.setText   ("");

      editSaveButton.setEnabled   (false);

      editButton.setEnabled       (true);
      testDataButton.setEnabled   (true);
      addButton.setEnabled        (true);
      deleteButton.setEnabled     (true);
      displayAllButton.setEnabled (true);
      exitButton.setEnabled       (true);
   }

   private void addTestData ()
   {
      nameTextField.setText ("Ahmed");
      idTextField.setText   ("s123");
      addEmployee ();

      nameTextField.setText ("Ali");
      idTextField.setText   ("k124");
      addEmployee();

      nameTextField.setText ("Basem");
      idTextField.setText   ("d122");
      addEmployee ();
   }

   private void displayAll ()
   {
      employeeTextArea.setText ("");

      for (EmployeeEmail emp : employeeLinkedList)
      {
         employeeTextArea.append (emp + "\n");
      }
   }

   private void exitApplication ()
   {
      System.exit (0);
   }

   public static void main (String[] args)
   {
      EmployeeEmailGUI app = new EmployeeEmailGUI ();
      app.setVisible  (true);
      app.setSize     (600, 400);
      app.setLocation (250, 150);
      app.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
   }
}