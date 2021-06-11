package westminsterrentalvehiclemanager;

/**
 * 
 * @author dim6ata
 */
public class Car extends Vehicle {

    //Instance Variables:
    private String carType;
    private int carNumSeats;
    private String carGearType;

    //Constructor:
    public Car(String plateNum, String vehMake) {
        super(plateNum, vehMake);
    }
    
    //Methods
    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {

        if (carType.equals("SEDAN") || carType.equals("LIMOUSINE") || carType.equals("HATCHBACK")
                || carType.equals("CONVERTIBLE") || carType.equals("SUV")
                || carType.equals("SPORTSCAR") || carType.equals("PICKUP") || carType.equals("MINICAR")) {
            this.carType = carType;
        } else {

            this.carType = "N/A";
        }

    }

    public int getCarNumSeats() {
        return carNumSeats;
    }

    public void setCarNumSeats(int carNumSeats) {
        if (carNumSeats >= 2 && carNumSeats <= 10) {
            this.carNumSeats = carNumSeats;
        } else {
            this.carNumSeats = 0;
        }
    }

    public String getCarGearType() {

        return carGearType;
    }

    public void setCarGearType(String carGearType) {

        if (carGearType.equals("AUTOMATIC") || carGearType.equals("MANUAL")) {
            this.carGearType = carGearType;
        } else {
            this.carGearType = "N/A";
        }
    }

    @Override
    public String toString() {

        return "Car{" + super.getVehMake() //used to write to file
                + "," + super.getVehPlateNum()
                + "," + ((carType == null) ? "N/A" : carType)
                + "," + ((carGearType == null) ? "N/A" : carGearType)
                + "," + carNumSeats
                + "," + super.getVehProdYear()
                + "," + ((super.getVehFuelType() == null) ? "N/A" : super.getVehFuelType())
                + "," + super.getVehConsump()
                + "," + super.getVehPricePerDay()
                + "," + super.getSchedule()
                + '}';
    }

    @Override
    public String toPrint() {
        return String.format("Make: %-15s | Plate Number: %9s | Type: %s",
                 super.getVehMake(), super.getVehPlateNum(), "Car");
    }

}
