package westminsterrentalvehiclemanager;

import java.time.LocalDate;
/**
 * 
 * @author dim6ata
 */
public class Schedule {

    private LocalDate currentD;
    private LocalDate pickUpDate;
    private LocalDate dropOffDate;

    public Schedule(LocalDate p, LocalDate d) {

        currentD = LocalDate.now();
        this.pickUpDate = p;
        this.dropOffDate = d;
    }

    public LocalDate getCurrentD() {
        return currentD;
    }

    public void setCurrentD(LocalDate currentD) {
        this.currentD = currentD;
    }

    public LocalDate getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(LocalDate pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public LocalDate getDropOffDate() {
        return dropOffDate;
    }

    public void setDropOffDate(LocalDate dropOffDate) {
        this.dropOffDate = dropOffDate;
    }

    @Override
    public String toString() {
        return pickUpDate + "," + dropOffDate;
    }

}
