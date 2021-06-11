package westminsterrentalvehiclemanager;

//STUDENT: w1696151

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class WestminsterRentalVehicleManagerTest {

    public WestminsterRentalVehicleManagerTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
   
    /**
     * Test of addCar method, of class WestminsterRentalVehicleManager.
     */
    @Test
    public void testAddCar() {
        WestminsterRentalVehicleManager newList = new WestminsterRentalVehicleManager();
        
        //Declare Vehicle
        ArrayList<Vehicle> testList = new ArrayList<>();
        String p = "CD12RDF";//plate
        String m = "I am a Car Make";//make
        String t = "SEDAN";//type
        String g = "AUTOMATIC";//gear        
        int n = 4;//number of seats
        int y = 0;
        String f = "";
        double price = 0;
        double cons = 0;
        Schedule sch = new Schedule(null, null);
        int index = 0;
        
        //USING addCar:
        newList.addCar(p, m, t, g, n);                       
        newList.getVehList().get(index).setVehProdYear(y);
        newList.getVehList().get(index).setVehFuelType(f);
        newList.getVehList().get(index).setVehPricePerDay(price);
        newList.getVehList().get(index).setVehConsump(cons);
        newList.getVehList().get(index).setSchedule(sch);

        //Expected Output Setup:
        Car carTest = new Car(p,m);
        carTest.setCarType(t);
        carTest.setCarGearType(g);
        carTest.setCarNumSeats(n);        
        testList.add(carTest);
        testList.get(index).setVehProdYear(y);
        testList.get(index).setVehFuelType(f);
        testList.get(index).setVehPricePerDay(price);
        testList.get(index).setVehConsump(cons);
        testList.get(index).setSchedule(sch);
           
        //Check if the addCar works properly. 
        Assert.assertEquals(testList.toString(), newList.getVehList().toString());
        
    }

    /**
     * Test of addBike method, of class WestminsterRentalVehicleManager.
     */
    @Test
    public void testAddBike() {
        
        WestminsterRentalVehicleManager newList = new WestminsterRentalVehicleManager();
        
        //Declare Vehicle
        ArrayList<Vehicle> testList = new ArrayList<>();
        String p = "CD12RDF";//plate
        String m = "I am a Bike Make";//make
        String t = "SPORTSBIKE";//type
        int e = 300;//engine size        
        int max = 150;//max load
        int y = 0;
        String f = "";
        double price = 0;
        double cons = 0;
        Schedule sch = new Schedule(null, null);
        int index = 0;
        
        //USING addBike:
        newList.addBike(p, m, t, e, max);                       
        newList.getVehList().get(index).setVehProdYear(y);
        newList.getVehList().get(index).setVehFuelType(f);
        newList.getVehList().get(index).setVehPricePerDay(price);
        newList.getVehList().get(index).setVehConsump(cons);
        newList.getVehList().get(index).setSchedule(sch);

        //Expected Output Setup:
        Motorbike bikeTest = new Motorbike(p,m);
        bikeTest.setBikeType(t);
        bikeTest.setBikeEngineSize(e);
        bikeTest.setBikeMaxLoad(max);
        testList.add(bikeTest);
        testList.get(index).setVehProdYear(y);
        testList.get(index).setVehFuelType(f);
        testList.get(index).setVehPricePerDay(price);
        testList.get(index).setVehConsump(cons);
        testList.get(index).setSchedule(sch);
           
        //Check if the addBike works properly. 
        Assert.assertEquals(testList.toString(), newList.getVehList().toString());    
        
    }

    /**
     * Test of addVehDetails method, of class WestminsterRentalVehicleManager.
     */
    @Test
    public void testAddVehDetails() {
        
        WestminsterRentalVehicleManager newList = new WestminsterRentalVehicleManager();
        
        //Declare Vehicle
        ArrayList<Vehicle> testList = new ArrayList<>();
        String p = "CD12RDF";//plate
        String m = "I am a Car Make";//make
        String t = "SEDAN";//type
        String g = "AUTOMATIC";//gear        
        int n = 4;//number of seats
        int y = 2002;
        String f = "PETROL";
        double price = 26.99;
        double cons = 4.0;
        LocalDate pick = LocalDate.parse("2020-03-14");
        LocalDate drop = LocalDate.parse("2020-03-16");
        Schedule sch = new Schedule(pick, drop);
        
        int index = 0;
        
        //USING addCar() and addVehDetails() to be tested:
        newList.addCar(p, m, t, g, n);                       
        newList.addVehDetails(y, f, cons, price, pick, drop);

        //Expected Output Setup:
        Car carTest = new Car(p,m);
        carTest.setCarType(t);
        carTest.setCarGearType(g);
        carTest.setCarNumSeats(n);        
        testList.add(carTest);
        testList.get(index).setVehProdYear(y);
        testList.get(index).setVehFuelType(f);
        testList.get(index).setVehPricePerDay(price);
        testList.get(index).setVehConsump(cons);
        testList.get(index).setSchedule(sch);
           
        //Check if the addCar works properly. 
        Assert.assertEquals(testList.toString(), newList.getVehList().toString());

    }

    /**
     * Test of printVehicleList method, of class
     * WestminsterRentalVehicleManager.
     */
    @Test
    public void testPrintVehicleList() {

        //Output to be captured
        OutputStream outStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outStream);
        System.setOut(printStream);
      
        WestminsterRentalVehicleManager newList = new WestminsterRentalVehicleManager();
        
        //Declare Vehicle
        ArrayList<Vehicle> testList = new ArrayList<>();
        String p = "CD12RDF";//plate
        String m = "I am a Car Make";//make
        String t = "SEDAN";//type
        String g = "AUTOMATIC";//gear        
        int n = 4;//number of seats
        
        //USING addCar() and addVehDetails() to be tested:
        newList.addCar(p, m, t, g, n);                       
        
             
        //Expected Output Setup:
        Car carTest = new Car(p,m);
        carTest.setCarType(t);
        carTest.setCarGearType(g);
        carTest.setCarNumSeats(n);        
        testList.add(carTest);
        
        //SECOND VEHICLE ADDED TO COMPARE
        
        p = "CF13RDF";//plate
        m = "Another Car Make";//make
        t = "SEDAN";//type
        g = "AUTOMATIC";//gear        
        n = 4;//number of seats
        
        
        Car carTest2 = new Car(p,m);
        carTest.setCarType(t);
        carTest.setCarGearType(g);
        carTest.setCarNumSeats(n); 
        
        newList.addCar(p, m, t, g, n); 
        testList.add(carTest2);
        
        Collections.sort(testList);//sorts out expected list
        
        
        newList.printVehicleList();//checks the actual printVehicleList()

        assertEquals("\n"+printTester(testList)+"\n", outStream.toString());


        PrintStream originalOut = System.out;
        System.setOut(originalOut);

    }
    
    private String printTester(ArrayList<Vehicle> printList){
        String s = "";
        for (Vehicle veh : printList) {
            s = String.format("%s%s\n",s, veh.toPrint());
        }
        s = String.format("%s%s", s,"Current number of vehicles: " + printList.size());
        return s;
    }
 

}
