package westminsterrentalvehiclemanager;
/**
 * 
 * @author dim6ata
 */
public class Motorbike extends Vehicle {

    //Instance Variables:
    private String bikeType;
    private int bikeEngineSize;
    private int bikeMaxLoad;

    //Constructor:
    public Motorbike(String plateNum, String vehMake) {
        super(plateNum, vehMake);
    }
    
    //Methods:
    public String getBikeType() {
        return bikeType;
    }

    public void setBikeType(String bikeType) {
        if (bikeType.equals("MOPPET") || bikeType.equals("SPORTSBIKE") || bikeType.equals("DIRTBIKE") || bikeType.equals("CHOPPER")) {
            this.bikeType = bikeType;
        } else {
            this.bikeType = "N/A";
        }
    }

    public int getBikeEngineSize() {
        return bikeEngineSize;
    }

    public void setBikeEngineSize(int bikeEngineSize) {

        this.bikeEngineSize = (bikeEngineSize >= 100 && bikeEngineSize <= 5000) ? bikeEngineSize : 0;
    }

    public int getBikeMaxLoad() {
        return bikeMaxLoad;
    }

    public void setBikeMaxLoad(int bikeMaxLoad) {

        this.bikeMaxLoad = (bikeMaxLoad >= 80 && bikeMaxLoad <= 300) ? bikeMaxLoad : 0;
    }

    @Override
    public String toString() {

        return "Motorbike{" + super.getVehMake()
                + "," + super.getVehPlateNum()
                + "," + ((bikeType == null) ? "N/A" : bikeType)
                + "," + bikeEngineSize
                + "," + bikeMaxLoad
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
                super.getVehMake(), super.getVehPlateNum(), "Motorbike");
    }

}
