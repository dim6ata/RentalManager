package westminsterrentalvehiclemanager;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.time.temporal.ChronoUnit;
import javax.swing.*;
import java.util.ArrayList;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import org.jdatepicker.*;

/**
 * 
 * @author dim6ata
 */
public class RentalGUI extends JFrame {

    /*-----------------------------------------------------------------*/
    //INSTANT VARIABLES:
    /*-----------------------------------------------------------------*/
    private ArrayList<Vehicle> list;
    private JTable tableCar, tableBike;
    private VehicleTableModel tableModelCar, tableModelBike;
    private JRadioButton radioC, radioB;
    private JScrollPane scrollPane;
    private JDatePicker calendarP, calendarD;
    private JTextArea box;
    private LocalDate currentD, chosenPDate, chosenDDate, pickUp, dropOff;
    private JButton button;
    private int index, currentRow, modelRow;
    private JLabel pDateLabel, dDateLabel, imgLabel;
    private JTextField filter;
    private ImageIcon logo;
    private double price;
    private TableRowSorter<VehicleTableModel> rowSortCar;
    private TableRowSorter<VehicleTableModel> rowSortBike;
    private String[] carArr = {"Make", "Plate Number", "Car Type", "Gear Type", "Num Seats",
        "Year", "Fuel Type", "Consumption", "Price"};
    private String[] bikeArr = {"Make", "Plate Number", "Bike Type", "Engine Size", "Max Load",
        "Year", "Fuel Type", "Consumption", "Price"};

    /*-----------------------------------------------------------------*/
    //CONSTRUCTOR:
    /*-----------------------------------------------------------------*/
    public RentalGUI(ArrayList<Vehicle> vehListG) {
        //Window Name:
        super("Westminster Rental Vehicle Manager");

        //initialises list 
        this.list = vehListG;        
        currentD = LocalDate.now();
       
        /*-----------------------------------------------------------------*/
        //BOX SETUP
        /*-----------------------------------------------------------------*/
        box = new JTextArea();
        box.setPreferredSize(new Dimension(400, 200));

        box.setFont(new Font("Courier", Font.BOLD, 25));
        box.setText("Welcome To\nWestminster\nRental!");
        box.setVisible(true);
        box.setEditable(false);
        box.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

        JPanel panelBox = new JPanel();
        panelBox.setLayout(new BorderLayout());        
        panelBox.add(box, BorderLayout.NORTH);

        panelBox.setOpaque(true);
        panelBox.setBackground(Color.CYAN);

        /*-----------------------------------------------------------------*/
        //IMAGE SETUP
        /*-----------------------------------------------------------------*/
        try {

            logo = new ImageIcon(getClass().getResource("WMV.png"));

        } catch (NullPointerException e) {
            System.out.println("image not found.");
        }

        imgLabel = new JLabel(logo, JLabel.CENTER);
        imgLabel.setIcon(logo);
        imgLabel.setPreferredSize(new Dimension(200, 100));

        /*-----------------------------------------------------------------*/
        //CALENDAR LAYOUT
        /*-----------------------------------------------------------------*/
        //date labels:
        pDateLabel = new JLabel("Pickup Date:");
        dDateLabel = new JLabel("Drop-Off Date:");
        pDateLabel.setVisible(false);
        dDateLabel.setVisible(false);

        //date pickers(calendars):
        calendarP = new JDatePicker();
        calendarP.setShowYearButtons(false);
        calendarP.getModel().setSelected(true);
        calendarP.getModel().setValue(null);//clears the viewport of date 1        
        calendarP.setVisible(false);

        calendarD = new JDatePicker();
        calendarD.setShowYearButtons(false);
        calendarD.getModel().setSelected(true);
        calendarD.getModel().setValue(null);
        calendarD.setVisible(false);

        //calendar panel pickup:
        JPanel calendarPPanel = new JPanel();
        calendarPPanel.setLayout(new BorderLayout());
        calendarPPanel.add(pDateLabel, BorderLayout.NORTH);
        calendarPPanel.add((Component) calendarP, BorderLayout.CENTER);
        calendarPPanel.setPreferredSize(new Dimension(230, 60));//changes where the calendar appears
        calendarPPanel.setOpaque(true);
        calendarPPanel.setBackground(Color.CYAN);
        calendarPPanel.add(dDateLabel, BorderLayout.SOUTH);

        //calendar panel drop off:
        JPanel calendarDPanel = new JPanel();
        calendarDPanel.setLayout(new BorderLayout());
        calendarDPanel.add((Component) calendarD, BorderLayout.NORTH);
        calendarDPanel.setOpaque(true);
        calendarDPanel.setBackground(Color.CYAN);
        calendarDPanel.setPreferredSize(new Dimension(230, 10));

        //calendar panel total/both:
        JPanel calTotal = new JPanel();
        calTotal.setLayout(new BorderLayout());
        calTotal.add(calendarPPanel, BorderLayout.NORTH);
        calTotal.add(calendarDPanel, BorderLayout.CENTER);
        calTotal.setOpaque(true);
        calTotal.setBackground(Color.CYAN);

        /*-----------------------------------------------------------------*/
        //TOP PANEL:
        /*-----------------------------------------------------------------*/
        //Text Box + Image + Calendar Total Panels: 
        JPanel panelTop = new JPanel();
        panelTop.setLayout(new BorderLayout());//maybe add some boundary here
        panelTop.add(panelBox, BorderLayout.WEST);
        panelTop.add(imgLabel, BorderLayout.CENTER);
        panelTop.add(calTotal, BorderLayout.EAST);
        panelTop.setOpaque(true);
        panelTop.setBackground(Color.CYAN);

        /*-----------------------------------------------------------------*/
        //RADIO BUTTON SETUP
        /*-----------------------------------------------------------------*/
        JLabel radioLabel = new JLabel("Select Vehicle Type:");
        radioC = new JRadioButton("Car");
        radioB = new JRadioButton("Bike");
        //radio button group - allows for one of them to be selected at the time
        ButtonGroup group = new ButtonGroup();
        group.add(radioC);
        group.add(radioB);
        
        radioC.setOpaque(true);
        radioB.setOpaque(true);
        radioC.setBackground(Color.CYAN);
        radioB.setBackground(Color.CYAN);

        JPanel panelRadio = new JPanel();
        //sets radio panel to be a grid layout of 3 elements in one row: 
        panelRadio.setLayout(new GridLayout(1, 3, 10, 2));
        panelRadio.add(radioLabel, BorderLayout.NORTH);
        panelRadio.add(radioC, BorderLayout.NORTH);
        panelRadio.add(radioB, BorderLayout.NORTH);
        panelRadio.setOpaque(true);
        panelRadio.setBackground(Color.CYAN);

        /*-----------------------------------------------------------------*/
        //FILTER MENU
        /*-----------------------------------------------------------------*/
        JLabel filterLabel = new JLabel("Filter:");
        filter = new JTextField();
        filter.setText("");
        filter.setEnabled(false);
        filter.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

        JPanel panelFilter = new JPanel(new BorderLayout());
        panelFilter.add(filterLabel, BorderLayout.WEST);
        panelFilter.add(filter, BorderLayout.CENTER);
        panelFilter.setPreferredSize(new Dimension(40, 40));
        panelFilter.setOpaque(true);
        panelFilter.setBackground(Color.CYAN);

        /*-----------------------------------------------------------------*/
        //BUTTON SETUP
        /*-----------------------------------------------------------------*/
        button = new JButton("Accept");
        button.setVisible(false);
        button.setEnabled(true);
        JPanel panelButton = new JPanel();
        panelButton.setLayout(new BorderLayout());
        button.setPreferredSize(new Dimension(70, 45));
        button.setBackground(Color.GREEN);//sets the button green        
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));//button border        
        button.setOpaque(true);
        panelButton.add(button, BorderLayout.NORTH);
        panelButton.setOpaque(true);
        panelButton.setBackground(Color.CYAN);

        /*-----------------------------------------------------------------*/
        //MID PANEL:
        /*-----------------------------------------------------------------*/
        //Contains Radio Panel + Filter Panel + Button Panel
        JPanel panelMid = new JPanel();
        panelMid.setLayout(new BorderLayout(2, 2));
        panelMid.add(panelRadio, BorderLayout.WEST);
        panelMid.add(panelFilter, BorderLayout.CENTER);
        panelMid.add(panelButton, BorderLayout.EAST);
        panelMid.setOpaque(true);
        panelMid.setBackground(Color.CYAN);

        /*-----------------------------------------------------------------*/
        //TABLE MODELING:
        /*-----------------------------------------------------------------*/
        //CAR Table List Modelling:
        tableModelCar = new VehicleTableModel(list, carArr);//Vehicle Table model for Car
        tableCar = new JTable(tableModelCar);//JTable for Car       
        tableCar.setRowSelectionAllowed(true);
        tableCar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableCar.setEnabled(false);
        tableCar.setShowGrid(false);
        //Bike Table:
        tableModelBike = new VehicleTableModel(list, bikeArr);//Vehicle Table model for Bike
        tableBike = new JTable(tableModelBike);//JTable for Bike        
        tableBike.setRowSelectionAllowed(true);
        tableBike.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableBike.setEnabled(false);
        tableBike.setShowGrid(false);

        //Scroll setup:
        scrollPane = new JScrollPane(tableCar);
        scrollPane.setPreferredSize(new Dimension(900, 300));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

        JPanel panelTable = new JPanel();
        panelTable.setLayout(new BorderLayout());
        panelTable.add(scrollPane, BorderLayout.SOUTH);
        panelTable.setOpaque(true);
        /*-----------------------------------------------------------------*/
        //GLOBAL PANEL
        /*-----------------------------------------------------------------*/
        //CONTAINS: TOP + MIDDLE + TABLE PANELS:
        JPanel globalPanel = new JPanel();
        globalPanel.setLayout(new BorderLayout(0, 5));

        //Add items to global panel. Can allow edit of overall view.
        globalPanel.add(panelTop, BorderLayout.NORTH);
        globalPanel.add(panelMid, BorderLayout.CENTER);
        globalPanel.add(panelTable, BorderLayout.SOUTH);
        globalPanel.setBorder(BorderFactory.createLineBorder(Color.black));//sets border to black. 
        globalPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        globalPanel.setOpaque(true);
        globalPanel.setBackground(Color.CYAN);

        this.setIconImage(logo.getImage());//this makes the icon appear when gui loads
        //adds panel to Jframe
        add(globalPanel, BorderLayout.CENTER);


        /*-----------------------------------------------------------------*/
        //LISTENERS:
        /*-----------------------------------------------------------------*/
        MyListener listener = new MyListener();

        //Listeners for Radio Buttons:
        radioC.addActionListener(listener);
        radioB.addActionListener(listener);
        //Listeners for calendar interaction:
        calendarP.addActionListener(listener);
        calendarD.addActionListener(listener);
        //Listener for button:
        button.addActionListener(listener);
        //Listeners for tables:
        tableCar.addMouseListener(listener);
        tableBike.addMouseListener(listener);
        //KeyListener for keyboard input:
        MyKeyListener keyListener = new MyKeyListener();
        tableCar.addKeyListener(keyListener);
        tableBike.addKeyListener(keyListener);
        //Creating Table Row sorters, so that tables can be searched. 
        rowSortCar = new TableRowSorter<>(tableModelCar);
        tableCar.setRowSorter(rowSortCar);
        rowSortBike = new TableRowSorter<>(tableModelBike);
        tableBike.setRowSorter(rowSortBike);
        //FILTERING/SEARCH DOCUMENT LISTENER:
        filter.getDocument().addDocumentListener(new DocumentListener() {
            //overriding Document Listener Methods - this allows for search to happen:
            @Override
            public void insertUpdate(DocumentEvent e) {//method that sorts table according to value entered in filter. 

                if (radioC.isSelected()) {

                    tableCar.setRowSorter(rowSortCar);//re-enables the sorting
                    filterUpdate(rowSortCar, tableCar);

                } else if (radioB.isSelected()) {

                    tableBike.setRowSorter(rowSortBike);
                    filterUpdate(rowSortBike, tableBike);

                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {//method that sorts table according to value deleted from filter. 

                if (radioC.isSelected()) {

                    filterUpdate(rowSortCar, tableCar);
                    filterUpdate(rowSortCar, tableCar);

                } else if (radioB.isSelected()) {

                    tableBike.setRowSorter(rowSortBike);
                    filterUpdate(rowSortBike, tableBike);

                }

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });

    }  //end constructor

    /*-----------------------------------------------------------------*/
    //FILTER UPDATER:
    /*-----------------------------------------------------------------*/
    private void filterUpdate(TableRowSorter sorter, JTable table) {//updates the filtered elements and their positions in the tables. 

        String text = filter.getText();

        if (text.trim().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));//(?i) makes it case insensitive

            //Translation of row coordinates of filtered items:
            table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent event) {
                    int viewRow = table.getSelectedRow();
                    if (viewRow >= 0) {
                        modelRow = table.convertRowIndexToModel(viewRow);
                    }
                }
            });
        }
        pack();
    }

    /*-----------------------------------------------------------------*/
    //TIME SELECTOR:
    /*-----------------------------------------------------------------*/
   
    private LocalDate timeSelector(JDatePicker date) { //Time evaluation method: 
        int day, month, year;
        String dModel;
        LocalDate dateChosen;
        if (date.getModel().getValue() == null) {
            dateChosen = null;
        } else {
            day = date.getModel().getDay();

            month = date.getModel().getMonth() + 1;

            year = date.getModel().getYear();

            dModel = String.format("%4d-%02d-%02d", year, month, day);//formats including leading zeros. 
//            try {
            dateChosen = LocalDate.parse(dModel);
//            } catch (DateTimeParseException e) {
//                System.out.println("Parse problem " + e.getMessage());
//
//            }

            if (dateChosen.isBefore(currentD)) {//validating if the value is in the past.
                JOptionPane.showMessageDialog(null, "You cannot select a date in the past! \nPlease select a new date!");
                dateChosen = null;
                date.getModel().setValue(null);
                return dateChosen;
            }
            if ((pickUp == null && dropOff == null)) {

                return dateChosen;//this is when we are happy to assign the values.

            } else {
                if ((dateChosen.isAfter(pickUp) && dateChosen.isBefore(dropOff))
                        || dateChosen.isEqual(pickUp) || dateChosen.isEqual(dropOff)) {

                    dateChosen = null;
                    JOptionPane.showMessageDialog(null, "The date you have selected \nis currently unavailable!\nTry again!");
                    date.getModel().setValue(null);
                    return dateChosen;

                } else {//this satisfies the condition which is above therefore the date would be valid. 
                    return dateChosen;
                }

            }

        }
        return dateChosen;
    }

    /*-----------------------------------------------------------------*/
    //ROW SELECTOR:
    /*-----------------------------------------------------------------*/
    private void rowSelector() {//method that is responsible for row selection calculations:

        /*----------------------------------------------------------------*/
        //CAR LIST FOLLOW ACTION
        /*----------------------------------------------------------------*/
        if (radioC.isSelected() || radioB.isSelected()) {//condition that checks if radio buttons are selected

            if (tableCar.isShowing()) {

                currentRow = tableCar.getSelectedRow();//rowAtPoint(e.getPoint());//gets the current row number

                if (tableCar.isRowSelected(currentRow) || tableCar.isRowSelected(modelRow)) {

                    button.setVisible(false);
                    calendarD.getModel().setValue(null);//these 2 you might need
                    calendarP.getModel().setValue(null);
                    calendarD.setVisible(false);
                    dDateLabel.setVisible(false);
                    chosenPDate = null;
                    chosenDDate = null;

                    pack();

                }

                if (filter.getText().length() == 0) {//decides which values to pass - when filter is used - modelRow or otherwise general currentRow value.

                    checkVehDate(tableCar, "car", currentRow);
                } else {

                    checkVehDate(tableCar, "car", modelRow);
                }

                calendarP.setVisible(true);
                pDateLabel.setVisible(true);

                /*----------------------------------------------------------------*/
                //BIKE LIST FOLLOW ACTION
                /*----------------------------------------------------------------*/
            } else if (tableBike.isShowing()) {

                currentRow = tableBike.getSelectedRow();

                if (tableBike.isRowSelected(currentRow) || tableBike.isRowSelected(modelRow)) {
                    button.setVisible(false);
                    calendarD.getModel().setValue(null);//these 2 you might need
                    calendarP.getModel().setValue(null);
                    calendarD.setVisible(false);
                    dDateLabel.setVisible(false);
                    chosenPDate = null;
                    chosenDDate = null;

                    pack();

                }

                if (filter.getText().length() == 0) {
                    //calls function to calculate the details of the vehicle that is currently selected
                    //passes currentRow if the filter is not engaged
                    checkVehDate(tableBike, "bike", currentRow);
                } else {
                    //passes model row which is when the filter is enabled - (converted index according to filter selection criteria)
                    checkVehDate(tableBike, "bike", modelRow);
                }

                calendarP.setVisible(true);
                pDateLabel.setVisible(true);
            }

        } else {//if radio buttons are not selected 

            box.setText("Welcome To\nWestminster\nRental!");

        }
        pack();//allows for items to be redrawn. 
    }
    
    //method that checks if a vehicle has already been booked:
    private void checkVehDate(JTable table, String vehType, int row) {

        for (int i = 0; i < list.size(); i++) {

            if (table.getModel().getValueAt(row, 1).equals(list.get(i).getVehPlateNum())) {

                index = i;
                break;

            }
        }

        LocalDate pDate = list.get(index).getSchedule().getPickUpDate();
        LocalDate dDate = list.get(index).getSchedule().getDropOffDate();

        if (pDate == null && dDate == null) {
            box.setText(String.format("The selected %s\nwith plate: %s\nis currently available.\nSelect a Pickup Date", vehType, list.get(index).getVehPlateNum()));
            pickUp = null;
            dropOff = null;
        } else {
            box.setText(String.format("The selected %s\nwith plate: %s\nis reserved between \n%s and \n%s\nSelect a Pickup Date", vehType, list.get(index).getVehPlateNum(), pDate, dDate));
            pickUp = pDate;
            dropOff = dDate;
        }

    }

    /*-----------------------------------------------------------------*/
    //RESET METHOD:
    /*-----------------------------------------------------------------*/
    private void reset() {//method that resets GUI after each use. 
        box.setText("");
        calendarP.setVisible(false);
        pDateLabel.setVisible(false);
        dDateLabel.setVisible(false);
        calendarD.setVisible(false);
        calendarP.getModel().setValue(null);
        calendarD.getModel().setValue(null);
        chosenPDate = null;
        chosenDDate = null;
        box.setText("Welcome To\nWestminster\nRental!");

        button.setVisible(false);

    }

    /*-----------------------------------------------------------------*/
    //MY LISTENER INNER CLASS - ACTION + MOUSE LISTENER
    /*-----------------------------------------------------------------*/
    private class MyListener extends MouseAdapter implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {//action listener for radioButtons and JDatePickers
            /*----------------------------------------------------------------*/
            //RADIO BUTTON FOLLOW ACTION
            /*----------------------------------------------------------------*/

            if (radioC.isSelected()) {

                if (!tableCar.isRowSelected(currentRow)) {//is the current row number is not selected

                    tableBike.getSelectionModel().clearSelection();//clears current selection of bikes                    
                    scrollPane.setViewportView(tableCar);
                    tableCar.setEnabled(true);
                    tableModelCar.setRowSetter(1);//sets Vehicle Table Model To Switch to Car List
                    filter.setEnabled(true);
                    filter.setText("");

                    box.setText("Select A Vehicle:");
                    box.setVisible(true);

                    tableCar.setRowSorter(null);//disables the sorting - in case user changes category

                } else {//this is when a row is selected and radioC selected. 
                    tableBike.getSelectionModel().clearSelection();//clears current selection of bikes                   
                    //scrollPane.setViewportView(tableCar);//this is re-enabling if radio is swapped
                    tableModelCar.setRowSetter(1);//sets Vehicle Table Model To Switch to Car List
                    tableCar.setEnabled(true);
                    filter.setEnabled(true);
                    filter.setText("");
                    tableCar.setRowSorter(null);
                }

                pack();

            } else if (radioB.isSelected()) {
                if (!tableBike.isRowSelected(currentRow)) {//if current row is not selected - clears

                    tableCar.getSelectionModel().clearSelection();//clears current selection of cars
                    scrollPane.setViewportView(tableBike);
                    tableBike.setEnabled(true);
                    tableModelBike.setRowSetter(2);//sets Vehicle Table Model To Switch to Bike List
                    box.setText("Select A Vehicle:");
                    box.setVisible(true);
                    tableBike.setRowSorter(null);

                    filter.setEnabled(true);
                    filter.setText("");
                } else {//when radioB selected and when a row is selected:
                    tableCar.getSelectionModel().clearSelection();//clears current selection of cars

                    tableModelBike.setRowSetter(2);//sets Vehicle Table Model To Switch to Bike List

                    tableBike.setEnabled(true);
                    box.setVisible(true);
                    filter.setEnabled(true);
                    filter.setText("");
                    tableBike.setRowSorter(null);

                }

                pack();
            }
            /*----------------------------------------------------------------*/
            //CALENDAR FOLLOW ACTION
            /*----------------------------------------------------------------*/

            if (calendarP.isVisible()) {//only works when the Pickup Up calendar is visible:
                chosenPDate = timeSelector(calendarP);//calls time selector to check first conditions against vehicle availability

                if (chosenPDate != null) {//in the cases when we have returned a good value

                    box.setText("You have selected\npickup date: \n" + chosenPDate + "\nSelect a drop-off date!");

                    dDateLabel.setVisible(true);
                    calendarD.setVisible(true);
                    chosenDDate = null;//makes the value chosen in DDate null
                    //display select a dropOffDate picker.
                } else {

                }

            }
            if (calendarD.isVisible()) {
                if (chosenPDate == null) {
                    JOptionPane.showMessageDialog(null, "You cannot select a drop off date before selecting the pickup one!");
                    calendarD.getModel().setValue(null);
                    calendarD.setVisible(false);
                    dDateLabel.setVisible(false);

                } else {

                    chosenDDate = timeSelector(calendarD);
                    if (chosenDDate != null && chosenPDate != null) {//condition that checks for nulls to avoid throwing an error below:
                        if (chosenDDate.isBefore(chosenPDate)) {//need to go back to remain in current state just ask for another drop-off

                            JOptionPane.showMessageDialog(null, "Incorrect date. Select drop-off date that occurs after pickup date!");
                            calendarD.getModel().setValue(null);
                            chosenDDate = null;

                        } else {//this is when we can display the final calculation and enable the button. 

                            long numDays = ChronoUnit.DAYS.between(chosenPDate, chosenDDate) + 1;
                            price = (numDays) * list.get(index).getVehPricePerDay();
                            box.setText(String.format("%s - %s\nDates from %s \nto %s\nPrice: £%.2f \nPress Accept \nto confirm.",
                                    list.get(index).getVehMake(), list.get(index).getVehPlateNum(), chosenPDate.toString(), chosenDDate.toString(),
                                    price));
                            button.setVisible(true);
                            pack();
                        }
                    }
                }
            }

            /*----------------------------------------------------------------*/
            //BUTTON FOLLOW ACTION
            /*----------------------------------------------------------------*/
            if (button.isVisible()) {

                if (e.getSource() == button) {

                    int lastCheck = JOptionPane.showConfirmDialog(null, String.format("Thank you for selecting:\n%s - %s\nFrom %s to %s\nPrice: £%.2f \nPlease confirm your order!",
                            list.get(index).getVehMake(), list.get(index).getVehPlateNum(), chosenPDate.toString(), chosenDDate.toString(),
                            price));

                    switch (lastCheck) {
                        case JOptionPane.YES_OPTION:
                            list.get(index).getSchedule().setPickUpDate(chosenPDate);
                            list.get(index).getSchedule().setDropOffDate(chosenDDate);
                            JOptionPane.showMessageDialog(null, "Thank you for your order!");
                            reset();
                            break;
                        //nothing happens if they press cancel
                        case JOptionPane.CANCEL_OPTION:
                            break;
                        default:
                            //reset
                            reset();
                            break;
                    }
                }
            }

        }

        //  }//end check if row selected condition. 
        @Override
        public void mouseReleased(MouseEvent e) {//action listener for mouse actions:

            rowSelector();

        }

    }

    /*-----------------------------------------------------------------*/
    //KEY LISTENER INNER CLASS
    /*-----------------------------------------------------------------*/
    private class MyKeyListener extends KeyAdapter {//keyboard listener for up and down key selection of table elements:

        @Override
        public void keyReleased(KeyEvent e) {

            int code = e.getKeyCode();

            switch (code) {

                case 38://up arrow

                    rowSelector();
                    break;

                case 40://down arrow
                    rowSelector();
                    break;
            }

        }

    }

}
