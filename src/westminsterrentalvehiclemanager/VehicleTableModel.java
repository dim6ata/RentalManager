package westminsterrentalvehiclemanager;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
/**
 * 
 * @author dim6ata
 */
public class VehicleTableModel extends AbstractTableModel {

    private String[] columnNames;
    private ArrayList<Vehicle> list, listCar, listBike;
    private int rowSetter;

    public VehicleTableModel(ArrayList<Vehicle> list, String[] colNames) {
        this.list = list;
        listCar = new ArrayList<>();
        listBike = new ArrayList<>();
        rowSetter = 0;
        columnNames = colNames;//initialises the column headers.

        //initialises car and bike lists. 
        for (Vehicle v : this.list) {

            if (v instanceof Car) {

                listCar.add(v);

            } else {
                listBike.add(v);
            }
        }

    }

    public ArrayList<Vehicle> getCarList() {

        return listCar;

    }

    public ArrayList<Vehicle> getBikeList() {
        return listBike;
    }

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    @Override
    public int getRowCount() {
        switch (rowSetter) {
            case 1:
                return listCar.size();
            case 2:
                return listBike.size();
            default:
                return list.size();

        }
    }

    public void setRowSetter(int i) {//determines whether car or bike values will populate the tables.
        rowSetter = i;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        //actionlistener that will switch the indexes between Car and Bike. 
        Object temp = null;

        switch (rowSetter) {
            //Car Table Entries:
            case 1:
                switch (columnIndex) {
                    case 0:
                        temp = listCar.get(rowIndex).getVehMake();
                        break;
                    case 1:
                        temp = listCar.get(rowIndex).getVehPlateNum();
                        break;
                    case 2:
                        temp = Car.class.cast(listCar.get(rowIndex)).getCarType();
                        break;
                    case 3:
                        temp = Car.class.cast(listCar.get(rowIndex)).getCarGearType();
                        break;
                    case 4:
                        temp = Car.class.cast(listCar.get(rowIndex)).getCarNumSeats();
                        break;
                    case 5:
                        temp = listCar.get(rowIndex).getVehProdYear();
                        break;
                    case 6:
                        temp = listCar.get(rowIndex).getVehFuelType();
                        break;
                    case 7:
                        temp = listCar.get(rowIndex).getVehConsump();
                        break;
                    case 8:
                        temp = listCar.get(rowIndex).getVehPricePerDay();
                        break;
                    default:
                        break;
                }
                break;
            //Bike Table Entries:
            case 2:
                switch (columnIndex) {
                    case 0:
                        temp = listBike.get(rowIndex).getVehMake();
                        break;
                    case 1:
                        temp = listBike.get(rowIndex).getVehPlateNum();
                        break;
                    case 2:
                        temp = Motorbike.class.cast(listBike.get(rowIndex)).getBikeType();
                        break;
                    case 3:
                        temp = Motorbike.class.cast(listBike.get(rowIndex)).getBikeEngineSize();
                        break;
                    case 4:
                        temp = Motorbike.class.cast(listBike.get(rowIndex)).getBikeMaxLoad();
                        break;
                    case 5:
                        temp = listBike.get(rowIndex).getVehProdYear();
                        break;
                    case 6:
                        temp = listBike.get(rowIndex).getVehFuelType();
                        break;
                    case 7:
                        temp = listBike.get(rowIndex).getVehConsump();
                        break;
                    case 8:
                        temp = listBike.get(rowIndex).getVehPricePerDay();
                        break;
                    default:
                        break;
                }
                break;

            default:
                break;
        }

        return temp;
    }

    @Override
    public String getColumnName(int col) {
        //action listener for radiobutton selection to switch between Car and Bike
        return columnNames[col];
    }

}
