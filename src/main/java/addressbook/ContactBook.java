package addressbook;
import java.util.*;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
class Contact {
	private String name;
	private String phoneNumber;
	
	public Contact(String name, String phoneNumber) {
		this.name = name;
		this.phoneNumber = phoneNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	@Override
    public String toString() {
		return name + "-" + phoneNumber;
    }
}
public class ContactBook extends JFrame implements ActionListener {
	public JTextField nameField, phoneField;
	public JButton addButton, editButton, deleteButton, download;
	public ArrayList<Contact> contacts;
	public JList<String> contactList;
	public DefaultListModel<String> listModel;
	public JLabel nameLabel, phoneLabel;
	
	public ContactBook() {
		super("Contact Book");
		contacts = new ArrayList();
		listModel = new DefaultListModel();
		
		nameLabel = new JLabel("Enter name");
		phoneLabel = new JLabel("Enter phone number");
		
		nameField = new JTextField(20);
		phoneField = new JTextField(20);
		
		addButton = new JButton("Add");
		editButton = new JButton("Edit");
		deleteButton = new JButton("Delete");
		download = new JButton("Download");
		
		contactList = new JList(listModel);
		
		nameLabel.setBounds(20, 20, 150, 20);
		phoneLabel.setBounds(20, 50, 150, 20);
		nameField.setBounds(150, 20, 150, 20);
		phoneField.setBounds(150, 50, 150, 20);
		addButton.setBounds(320, 20, 80, 20);
		editButton.setBounds(320, 50, 80, 20);
		deleteButton.setBounds(320, 80, 80, 20);
		contactList.setBounds(50, 150, 320, 200);
		download.setBounds(140, 360, 130, 20);

		
		addButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				String phoneNumber = phoneField.getText();
				if(!name.isEmpty() && !phoneNumber.isEmpty() && phoneNumber.length()<=10) {
					Contact contact = new Contact(name, phoneNumber);
					contacts.add(contact);
					listModel.addElement(contact.getName()+"\t"+"-      "+contact.getPhoneNumber());
					clearFields();
				} else {
					JOptionPane.showMessageDialog(null, "Name and phone number cannot be empty or phone number cannot exceed 10 digit");
				}
			}
		});
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = contactList.getSelectedIndex();
				if(selectedIndex != -1) {
					Contact selectedContact = contacts.get(selectedIndex);
					String newName = nameField.getText();
					String newPhone = phoneField.getText();
					selectedContact.setName(newName);
					selectedContact.setPhoneNumber(newPhone);
					listModel.set(selectedIndex, selectedContact.getName() +"\t"+"-      "+selectedContact.getPhoneNumber());
				} else {
					JOptionPane.showMessageDialog(null, "Please select a contact to edit");
				}
			}
		});
		
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = contactList.getSelectedIndex();
				if(selectedIndex!=-1) {
					contacts.remove(selectedIndex);
					listModel.remove(selectedIndex);
					clearFields();
				} else {
					JOptionPane.showMessageDialog(null, "Please select a contact to delete");
				}
			}
		});
		
		download.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
		            JFileChooser fileChooser = new JFileChooser();
		            int returnValue = fileChooser.showSaveDialog(null);
		            if (returnValue == JFileChooser.APPROVE_OPTION) {
		                File file = fileChooser.getSelectedFile();
		                FileOutputStream fos = new FileOutputStream(file);
		                BufferedOutputStream bos = new BufferedOutputStream(fos);

		                StringBuilder pdfContent = new StringBuilder();
		                for (Contact contact : contacts) {
		                    pdfContent.append(contact.getName()).append("-      ").append(contact.getPhoneNumber()).append("\n");
		                }

		                bos.write(pdfContent.toString().getBytes());
		                bos.close();
		                fos.close();

		                JOptionPane.showMessageDialog(null, "Contact list exported to txt successfully!");
		            }
		        } catch (IOException ex) {
		            ex.printStackTrace();
		            JOptionPane.showMessageDialog(null, "Error exporting contact list to PDF.");
		        }
			}
		});
		JScrollPane scrollPane = new JScrollPane(contactList);
		
		add(nameLabel);
		add(phoneLabel);
		add(nameField);
		add(phoneField);
		add(addButton);
		add(editButton);
		add(deleteButton);
		add(download);
		add(contactList);
		add(scrollPane);
		
		setSize(480, 580);
		setLayout(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	}
	
	public void clearFields() {
		nameField.setText("");
		phoneField.setText("");
	}
	

	public static void main(String[] args) {
		new ContactBook();	
	}

	public void actionPerformed(ActionEvent e) {
		
	}
}
