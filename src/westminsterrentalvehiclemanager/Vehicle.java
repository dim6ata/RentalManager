package westminsterrentalvehiclemanager;
/**
 * 
 * @author dim6ata
 */
public abstract class Vehicle implements Comparable<Vehicle> {

    //Instance Variables:
    private String vehPlateNum;
    private String vehMake;
    private int vehProdYear;
    private String vehFuelType;
    private double vehPricePerDay;
    private double vehConsump;
    private Schedule schedule;

    //Constructors:       
    public Vehicle(String plateNum, String vehMake) {

        this.vehPlateNum = plateNum;
        this.vehMake = vehMake;
    }

    //Methods:
    public String getVehPlateNum() {
        return vehPlateNum;
    }

    public void setVehPlateNum(String vehPlateNum) {
        this.vehPlateNum = vehPlateNum;
    }

    public String getVehMake() {
        return vehMake;
    }

    public void setVehMake(String vehMake) {
        this.vehMake = vehMake;
    }

    public int getVehProdYear() {
        return vehProdYear;
    }

    public void setVehProdYear(int vehProdYear) {
        this.vehProdYear = (vehProdYear >= 1950 && vehProdYear <= 2019) ? vehProdYear : 0;
    }

    public String getVehFuelType() {
        return vehFuelType;
    }

    public void setVehFuelType(String vehFuelType) {

        this.vehFuelType = (vehFuelType.equals("DIESEL") || vehFuelType.equals("PETROL")
                || vehFuelType.equals("NATURAL GAS") || vehFuelType.equals("HYBRID")
                || vehFuelType.equals("ELECTRIC")) ? vehFuelType : "N/A";
    }

    public double getVehConsump() {
        return vehConsump;
    }

    public void setVehConsump(double vehConsump) {

        this.vehConsump = (vehConsump >= 4.0 && vehConsump <= 30.0) ? vehConsump : 0.0;
    }

    public double getVehPricePerDay() {
        return vehPricePerDay;
    }

    public void setVehPricePerDay(double vehPricePerDay) {
        this.vehPricePerDay = (vehPricePerDay >= 25.99 && vehPricePerDay <= 129.99) ? vehPricePerDay : 0.0;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public abstract String toPrint();//abstract method used to print statement of vehicle list. 

    @Override
    public int compareTo(Vehicle v1) {//to compare values of vehicle

        if (this.getVehMake().equals(v1.getVehMake())) {
            return 0;
        } else if (this.getVehMake().compareTo(v1.getVehMake()) > 0) {//string comparison
            return 1;
        } else {
            return -1;
        }
    }

//    @Override
//    public String toString() {
//
//        return "Vehicle{" + "Plate Number: " + vehPlateNum
//                + ", Vehicle Make: " + vehMake
//                + ", Fuel Type: " + vehFuelType
//                + ", Consumption: " + vehConsump
//                + ", Production Year: " + vehProdYear
//                + ", Price per day: " + vehPricePerDay + '}';
//    }
    
}
