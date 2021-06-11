package westminsterrentalvehiclemanager;

/*-----------------------------------------------------------------------------------*/
                                //REFERENCES
/*-----------------------------------------------------------------------------------*/

 /*https://www.baeldung.com/java-type-casting - how to cast objects   
   https://www.programcreek.com/java-api-examples/index.php?api=org.jdatepicker.JDatePicker - how to use date picker 
   https://jdatepicker.org/ - download link and materials on JDatePicker
   https://stackoverflow.com/questions/2442599/how-to-set-jframe-to-appear-centered-regardless-of-monitor-resolution#:~:targetText=Just%20click%20on%20form%20and,tab%20and%20check%20Generate%20Center%20.&targetText=If%20the%20component%20is%20null%2C%20or%20the%20GraphicsConfiguration%20associated%20with,the%20center%20of%20the%20screen. - how to center screen
   https://stackoverflow.com/questions/22066387/how-to-search-an-element-in-a-jtable-java - how to search a JTable
   https://docs.oracle.com/javase/tutorial/uiswing/components/table.html#sorting - how to filter and translate row coordinates
*/

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;
import javax.swing.JFrame;

/**
 * 
 * @author dim6ata
 */
public class WestminsterRentalVehicleManager implements RentalVehicleManager {

    private final int MAX_VEH;//maximum number of vehicles in parking lot.

    private ArrayList<Vehicle> vehList;//list holding vehicles

    public ArrayList<Vehicle> getVehList() {
        return vehList;
    }

    public void setVehList(ArrayList<Vehicle> vehList) {
        this.vehList = vehList;
    }

    //main constructor:
    public WestminsterRentalVehicleManager() {

        vehList = new ArrayList<>();
        MAX_VEH = 50;
    }

    //constructor to be used if company wants to have different sites and park places of different sizes. 
    public WestminsterRentalVehicleManager(int max) {

        vehList = new ArrayList<>();
        MAX_VEH = max;
    }

    //loads the list of existing vehicles: 
    private void loadList() throws FileNotFoundException {

        String container, plate, make, type, gear, bikeType, fuelType, pDate, dDate;
        int seats, engine, load, yearProd;
        String[] tempArr;
        double price, consumption;
        int index = 1;
        LocalDate pickUp, dropOff;

        try (Scanner input = new Scanner(new FileReader("VehicleList.txt"))) {
            while (input.hasNext()) {

                container = input.nextLine();
                try {
                    if (container.contains("Car")) {

                        tempArr = container.split("[^A-Za-z0-9\\/\\s-.]+");//avoids commas(,) so that it can split elements by them. 
                        plate = tempArr[index + 1];
                        make = tempArr[index];
                        type = tempArr[index + 2];
                        gear = tempArr[index + 3];
                        seats = Integer.parseInt(tempArr[index + 4]);
                        yearProd = Integer.parseInt(tempArr[index + 5]);
                        fuelType = tempArr[index + 6];
                        consumption = Double.parseDouble(tempArr[index + 7]);
                        price = Double.parseDouble(tempArr[index + 8]);
                       
                        pDate = tempArr[index + 9];
                        dDate = tempArr[index + 10];//check if you need to add 

                        pDate = (pDate.equals("null")) ? null : pDate;
                        dDate = (dDate.equals("null")) ? null : dDate;
                        if (pDate == null) {
                            pickUp = null;
                        } else {
                            pickUp = LocalDate.parse(pDate);//this will go straight to Schedule 1 for pickup one for dropOff
                        }
                        if (dDate == null) {
                            dropOff = null;
                        } else {
                            dropOff = LocalDate.parse(dDate);
                        }

                        addCar(plate, make, type, gear, seats);
                        addVehDetails(yearProd, fuelType, consumption, price, pickUp, dropOff);

                    } else if (container.contains("Motorbike")) {

                        tempArr = container.split("[^A-Za-z0-9\\/\\s-.]+");
                        plate = tempArr[index + 1];
                        make = tempArr[index];
                        bikeType = tempArr[index + 2];
                        engine = Integer.parseInt(tempArr[index + 3]);
                        load = Integer.parseInt(tempArr[index + 4]);
                        yearProd = Integer.parseInt(tempArr[index + 5]);
                        fuelType = tempArr[index + 6];
                        consumption = Double.parseDouble(tempArr[index + 7]);
                        price = Double.parseDouble(tempArr[index + 8]);

                        pDate = tempArr[index + 9];
                        dDate = tempArr[index + 10];//check if you need to add 

                        pDate = (pDate.equals("null")) ? null : pDate;
                        dDate = (dDate.equals("null")) ? null : dDate;
                        if (pDate == null) {
                            pickUp = null;
                        } else {
                            pickUp = LocalDate.parse(pDate);//this will go straight to Schedule 1 for pickup one for dropOff
                        }
                        if (dDate == null) {
                            dropOff = null;
                        } else {
                            dropOff = LocalDate.parse(dDate);
                        }

                        addBike(plate, make, bikeType, engine, load);
                        addVehDetails(yearProd, fuelType, consumption, price, pickUp, dropOff);

                    } else {
                        System.out.println("The file doesn't have any matching entries.");
                    }
                } catch (IndexOutOfBoundsException e) { //catch{
                    System.out.println("Some elements could not be loaded.");
                }
            }

            System.out.println("Your file has been loaded.");

        } catch (IOException e) {
            System.out.println("The file has not been found.");

        }

    }

    //main menu method:
    private void menu() {

        String menuChar;
        Scanner inp = new Scanner(System.in);

        Outer:
        while (true) {
            System.out.println("\nMAIN MENU");
            System.out.println("Press A to ADD vehicle\n"
                    + "Press D to DELETE a vehicle\n"
                    + "Press P to PRINT vehicle list\n"
                    + "Press S to SAVE vehicle list\n"
                    + "Press G to CALL GUI\n"
                    + "Press U to UPDATE a vehicle\n"
                    + "Press Q to QUIT\n"
            );
            System.out.print("Enter one of the above options: ");

            menuChar = inp.nextLine();
            menuChar = menuChar.toUpperCase();
            switch (menuChar) {

                case "A":
                    if (vehList.size() == MAX_VEH) {
                        System.out.println("No more parking slots available.");
                    } else {
                        addVehicle();
                        int index = vehList.size() - 1;
                        System.out.printf("Vehicle Make: %s | Plate Number: %s - has been added to the list of vehicles\n",
                                vehList.get(index).getVehMake(), vehList.get(index).getVehPlateNum());
                    }
                    break;

                case "D":
                    if (vehList.isEmpty()) {
                        System.out.println("No vehicles can be deleted currently.");
                    } else {
                        Loop:
                        while (true) {

                            System.out.print("\nAre you sure you want to delete a vehicle? Enter Y/N: ");
                            String choice = inp.nextLine();
                            choice = choice.toUpperCase();

                            switch (choice) {

                                case "Y":
                                    System.out.print("DELETE MENU:");
                                    printVehicleList();
                                    deleteVehicle();

                                case "N":

                                    break Loop;

                                default:
                                    System.out.print("Incorrect entry, try again: ");
                                    break;

                            }

                        }

                    }
                    break;

                case "P":
                    System.out.println("PRINT MENU:");
                    printVehicleList();
                    break;

                case "S":
                    saveVehList();
                    break;

                case "G":
                    callGui();
                    break;

                case "U":
                    printVehicleList();
                    System.out.print("Enter Plate Number of Vehicle You Want to Update: ");
                    int toUpdate = checkUpdate();

                    if (vehList.get(toUpdate).getClass().toString().contains("Car")) {
                        updateMenu(toUpdate, "C");
                    } else if (vehList.get(toUpdate).getClass().toString().contains("Motorbike")) {
                        updateMenu(toUpdate, "M");
                    }
                    break;

                case "Q":
                    break Outer;

                default:
                    System.out.print("Incorrect entry. Try again:");
                    break;
            }
        }
    }

    //method that allows users to choose the type of vehicle to add and presents them with all options.
    @Override
    public void addVehicle() {

        System.out.println("\nPress C to add a new car\n"
                + "Press M to add a new motorbike");
        System.out.print("Enter one of the above options: ");

        boolean flag = true;
        String plate, make, carType, gearType, bikeType;
        int numSeats, bikeEngine, bikeLoad;

        while (flag) {

            Scanner inp = new Scanner(System.in);
            String choice = inp.nextLine();
            choice = choice.toUpperCase();

            switch (choice) {

                case "C":

                    plate = plateNumEval();

                    System.out.print("Enter Car Make: ");
                    make = strInputVal();

                    carType = carTypeEval();

                    gearType = gearTypeEval();

                    numSeats = numSeatsEval();

                    addCar(plate, make, carType, gearType, numSeats);

                    flag = false;

                    break;

                case "M":

                    plate = plateNumEval();

                    System.out.print("Enter Bike Make: ");
                    make = strInputVal();

                    bikeType = bikeTypeEval();
                    bikeEngine = bikeEngineEval();
                    bikeLoad = bikeLoadEval();

                    addBike(plate, make, bikeType, bikeEngine, bikeLoad);

                    flag = false;
                    break;

                default:
                    System.out.print("Incorrect entry, try again: ");
                    break;

            }

        }
        double price, consumption;
        int yearProd;
        LocalDate pickUp, dropOff;
        pickUp = null;
        dropOff = null;
        String fuelType;
        yearProd = yearEval();
        fuelType = fuelTypeEval();
        consumption = consumpEval();
        price = priceEval();

        addVehDetails(yearProd, fuelType, consumption, price, pickUp, dropOff);

    }

    //adds all car specific elements to the list of vehicles. 
    public void addCar(String plate, String make, String type, String gear, int seats) {

        Car car = new Car(plate, make);
        vehList.add(car);
        car.setCarType(type);
        car.setCarGearType(gear);
        car.setCarNumSeats(seats);

    }

    //adds all bike specific elements to the list of vehicles
    public void addBike(String plate, String make, String bikeType, int engine, int load) {

        Motorbike bike = new Motorbike(plate, make);
        vehList.add(bike);
        bike.setBikeType(bikeType);
        bike.setBikeEngineSize(engine);
        bike.setBikeMaxLoad(load);

    }

    //adds all generic vehicle elements to the list of vehicles
    public void addVehDetails(int yearProd, String fuelType, double consumption, double price, LocalDate pickUp, LocalDate dropOff) {


        int index = vehList.size() - 1;
        Schedule schedule = new Schedule(pickUp, dropOff);//so here it will vary betwen null and yyyy-MM-dd

        vehList.get(index).setVehProdYear(yearProd);
        vehList.get(index).setVehFuelType(fuelType);
        vehList.get(index).setVehConsump(consumption);
        vehList.get(index).setVehPricePerDay(price);
        vehList.get(index).setSchedule(schedule);

    }

    //method responsible for the deletion of vehicles from the system. 
    @Override
    public void deleteVehicle() {

        System.out.print("Enter Plate Number of Vehicle You Want to Delete: ");
        int toDelete = checkUpdate();

        System.out.println("The vehicle deleted was: " + vehList.get(toDelete));
        vehList.remove(toDelete);
        System.out.println("There are " + (MAX_VEH - vehList.size()) + " parking lots available. ");

    }

    //method responsible for printing vehicle list when called from menu
    @Override
    public void printVehicleList() {
        System.out.println("");
        ArrayList<Vehicle> printList = new ArrayList<>();
        printList = (ArrayList<Vehicle>) vehList.clone();
        Collections.sort(printList);
        for (Vehicle veh : printList) {
            System.out.println(veh.toPrint());
        }
        System.out.println("Current number of vehicles: " + vehList.size());

    }

    //method that saves current state of vehicle list:
    @Override
    public void saveVehList() {
        try {//tests if the file exists
            File fileName = new File("VehicleList.txt");
            FileWriter fileWriter;
            fileWriter = new FileWriter(fileName);
            try (Writer output = new BufferedWriter(fileWriter)) {
                for (Vehicle item : vehList) {

                    output.write(item + "\n");
                }
                System.out.println("Your file has been saved!");

            } catch (IOException e) {
                System.out.println("The file could not be saved!");
            }

        } catch (FileNotFoundException e) {
            System.out.println("The file cannot be found." + e.getMessage());
        } catch (IOException ex) {
            System.out.println("An error has occurred when accessing file" + ex);
        }
    }

    //method that calls GUI
    private void callGui() {

        RentalGUI myFrame = new RentalGUI(vehList);
        
        myFrame.setSize(920, 590);       
        myFrame.pack();
        myFrame.setVisible(true);
        myFrame.setLocationRelativeTo(null);//places frame at the centre of the screen. 
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
    }

    //a submenu that allows user to update individual values of vehicles that are already on the system:
    private void updateMenu(int toUpdate, String token) {

        boolean flag;

        String plate, make, carType, gearType, bikeType, fuelType;
        int numSeats, bikeEngine, bikeLoad, yearProd;
        double price, consumption;
        Vehicle veh = vehList.get(toUpdate);
        Car car;
        Motorbike bike;

        flag = token.equals("C");

        LOOP:
        while (true) {
            System.out.println("Current information for vehicle: " + vehList.get(toUpdate));
            if (token.equals("C")) {
                System.out.println(
                        "\nUPDATE SUBMENU:\n"
                        + "Press 1 to Update Car Make\n"
                        + "Press 2 to Update Plate Number\n"
                        + "Press 3 to Update Car Type\n"
                        + "Press 4 to Update Gear Type\n"
                        + "Press 5 to Update Number of Seats\n"
                        + "Press 6 to Update Year of Production\n"
                        + "Press 7 to Update Car Fuel Type\n"
                        + "Press 8 to Update Fuel Consumption\n"
                        + "Press 9 to Update Car Price Per Day\n"
                        + "Press Q to Quit Update Menu\n");

            } else {
                System.out.println(
                        "\nUPDATE SUBMENU:\n"
                        + "Press 1 to Update Bike Make\n"
                        + "Press 2 to Update Plate Number\n"
                        + "Press 3 to Update Bike Type\n"
                        + "Press 4 to Update Bike Engine Size\n"
                        + "Press 5 to Update Bike Max Load\n"
                        + "Press 6 to Update Year of Production\n"
                        + "Press 7 to Update Bike Fuel Type\n"
                        + "Press 8 to Update Bike Consumption\n"
                        + "Press 9 to Update Bike Price Per Day\n"
                        + "Press Q to Quit Update Menu\n");

            }

            System.out.print("Enter one of the above options: ");

            Scanner inp = new Scanner(System.in);
            String choice = inp.nextLine();
            choice = choice.toUpperCase();

            switch (choice) {

                case "1":
                    System.out.print("Enter Vehicle Make: ");
                    make = strInputVal();
                    vehList.get(toUpdate).setVehMake(make);
                    break;
                case "2":
                    plate = plateNumEval();
                    vehList.get(toUpdate).setVehPlateNum(plate);
                    break;
                case "3":
                    if (flag) {
                        if (Car.class.isInstance(veh)) {
                            car = Car.class.cast(veh);
                            carType = carTypeEval();
                            car.setCarType(carType);
                        } else {
                            System.out.println("Cannot Cast to Car.");
                        }
                    } else {
                        if (Motorbike.class.isInstance(veh)) {
                            bike = Motorbike.class.cast(veh);
                            bikeType = bikeTypeEval();
                            bike.setBikeType(bikeType);
                        } else {
                            System.out.println("Cannot Cast to Bike.");
                        }

                    }
                    break;

                case "4":
                    if (flag) {
                        if (Car.class.isInstance(veh)) {
                            car = Car.class.cast(veh);
                            gearType = gearTypeEval();
                            car.setCarGearType(gearType);
                        } else {
                            System.out.println("Cannot Cast to Car.");
                        }

                    } else {
                        if (Motorbike.class.isInstance(veh)) {
                            bike = Motorbike.class.cast(veh);
                            bikeEngine = bikeEngineEval();
                            bike.setBikeEngineSize(bikeEngine);
                        } else {
                            System.out.println("Cannot Cast to Bike.");
                        }
                    }
                    break;

                case "5":
                    if (flag) {
                        if (Car.class.isInstance(veh)) {
                            car = Car.class.cast(veh);
                            numSeats = numSeatsEval();
                            car.setCarNumSeats(numSeats);
                        } else {
                            System.out.println("Cannot Cast to Car.");
                        }
                    } else {
                        if (Motorbike.class.isInstance(veh)) {
                            bike = Motorbike.class.cast(veh);
                            bikeLoad = bikeLoadEval();
                            bike.setBikeMaxLoad(bikeLoad);
                        } else {
                            System.out.println("Cannot Cast to Bike.");
                        }

                    }
                    break;

                case "6":
                    yearProd = yearEval();
                    vehList.get(toUpdate).setVehProdYear(yearProd);
                    break;

                case "7":
                    fuelType = fuelTypeEval();
                    vehList.get(toUpdate).setVehFuelType(fuelType);
                    break;

                case "8":
                    consumption = consumpEval();
                    vehList.get(toUpdate).setVehConsump(consumption);
                    break;

                case "9":
                    price = priceEval();
                    vehList.get(toUpdate).setVehPricePerDay(price);
                    break;

                case "Q":

                    break LOOP;

                default:
                    System.out.print("Incorrect entry, try again: ");
                    break;

            }

        }
        System.out.println("Your record has been updated.");
    }

    //evaluates plate number until : 
    private String plateNumEval() {

        String plate;
        while (true) {
            System.out.print("Enter Plate Number: ");
            plate = plateInputVal();
            if (checkPlate(plate)) {//checks if plate already exists
                break;

            }
        }
        return plate;

    }

    //checks against all plate items on list and returns true only if the plate isn't already taken
    private boolean checkPlate(String plate) {//checks if plate number already exists on the system.

        for (int i = 0; i < vehList.size(); i++) {
            if (plate.contains(vehList.get(i).getVehPlateNum())) {
                System.out.println("This Plate Number is already on the system.");
                return false;
            }
        }
        return true;

    }

    //asks user for choosing a specific car type:
    private String carTypeEval() {

        System.out.println("\nCar Type Options:"
                + "\n 1: SEDAN\n 2: SUV\n 3: CONVERTIBLE\n 4: HATCHBACK\n "
                + "5: SPORTSCAR\n 6: PICKUP\n 7: LIMOUSINE\n 8: MINICAR");
        System.out.print("Enter one of the above options:");
        while (true) {

            Scanner inp = new Scanner(System.in);
            String choice = inp.nextLine();

            switch (choice) {

                case "1":

                    return "SEDAN";

                case "2":

                    return "SUV";

                case "3":

                    return "CONVERTIBLE";

                case "4":

                    return "HATCHBACK";

                case "5":

                    return "SPORTSCAR";

                case "6":

                    return "PICKUP";

                case "7":

                    return "LIMOUSINE";

                case "8":

                    return "MINICAR";
                default:
                    System.out.print("Incorrect entry, try again: ");
                    break;

            }

        }
    }

    //asks user for choosing a specific gear type:
    private String gearTypeEval() {

        System.out.println("\nGear Type Options:"
                + "\n 1: AUTOMATIC\n 2: MANUAL");
        System.out.print("Enter one of the above options:");
        while (true) {

            Scanner inp = new Scanner(System.in);
            String choice = inp.nextLine();

            switch (choice) {

                case "1":

                    return "AUTOMATIC";

                case "2":

                    return "MANUAL";

                default:
                    System.out.print("Incorrect entry, try again: ");
                    break;

            }

        }

    }

    //asks user to enter number of vehicle seats and evaluates them:
    private int numSeatsEval() {

        int numSeats;
        System.out.print("Enter number of car seats, between 2 and 10: ");

        while (true) {

            numSeats = intInputVal();
            if (numSeats >= 2 && numSeats <= 10) {
                break;
            }
            System.out.print("Incorrect number of seats, try again: ");

        }
        return numSeats;

    }

    //asks user for choosing a specific bike type:
    private String bikeTypeEval() {

        System.out.println("\nBike Type Options:"
                + "\n 1: MOPPET\n 2: SPORTSBIKE\n 3: DIRTBIKE\n 4: CHOPPER\n");
        System.out.print("Enter one of the above options:");
        while (true) {

            Scanner inp = new Scanner(System.in);
            String choice = inp.nextLine();

            switch (choice) {

                case "1":

                    return "MOPPET";

                case "2":

                    return "SPORTSBIKE";

                case "3":

                    return "DIRTBIKE";

                case "4":

                    return "CHOPPER";

                default:
                    System.out.print("Incorrect entry, try again: ");
                    break;

            }

        }

    }

    //input and evaluation of bike engine size:
    private int bikeEngineEval() {

        int engineSize;
        System.out.print("Enter engine size between 100 and 5000cc: ");

        while (true) {

            engineSize = intInputVal();
            if (engineSize >= 100 && engineSize <= 5000) {
                break;
            }
            System.out.print("Incorrect engine size, try again: ");

        }
        return engineSize;

    }

    //input and evaluation of the maximum load that a bike can take:
    private int bikeLoadEval() {

        int bikeLoad;
        System.out.print("Enter the Maximum Bike Load in KG between 80 and 300: ");

        while (true) {

            bikeLoad = intInputVal();

            if (bikeLoad >= 80 && bikeLoad <= 300) {
                break;
            }
            System.out.print("Incorrect load unit, try again: ");

        }
        return bikeLoad;

    }

    //input and evaluation of vehicle year of production: 
    private int yearEval() {
        int year;
        System.out.print("Enter Year of production: ");

        while (true) {

            year = intInputVal();

            if (year >= 1950 && year <= 2019) {
                break;
            }
            System.out.print("Incorrect year, try again: ");

        }
        return year;

    }

    //input and evaluation of vehicle fuel type: 
    private String fuelTypeEval() {

        System.out.println("\nFuel Type Options:"
                + "\n 1: DIESEL\n 2: PETROL\n 3: NATURAL GAS\n "
                + "4: HYBRID\n 5: ELECTRIC\n");
        System.out.print("Enter one of the above options:");
        while (true) {

            Scanner inp = new Scanner(System.in);
            String choice = inp.nextLine();

            switch (choice) {

                case "1":

                    return "DIESEL";

                case "2":

                    return "PETROL";

                case "3":

                    return "NATURAL GAS";

                case "4":

                    return "HYBRID";

                case "5":

                    return "ELECTRIC";

                default:
                    System.out.print("Incorrect entry, try again: ");
                    break;

            }

        }

    }

    //input and valudation of vehicle consumption levels:
    private double consumpEval() {

        double consumption;
        System.out.print("Enter vehicle fuel consumption in liters per 100km between 4.0 and 30.0: ");

        while (true) {

            consumption = dblInputVal();

            if (consumption >= 4.0 && consumption <= 30.0) {
                break;
            }
            System.out.print("Incorrect consumption values, try again: ");

        }
        return consumption;

    }

    //input and validation of vehicle price per day:
    private double priceEval() {

        double price;
        System.out.print("Enter vehicle rental price per day between 25.99 and 129.99: ");

        while (true) {

            price = dblInputVal();

            if (price >= 25.99 && price <= 129.99) {
                break;
            }
            System.out.print("Incorrect price range, try again: ");

        }
        return price;

    }

    //takes input for plate and validates it:
    private String plateInputVal() {

        String plateNum;
        Scanner input = new Scanner(System.in);

        while (!input.hasNext("[A-Za-z]{2}[0-9]{2}[A-Za-z]{3}")) {//looks for 2 letters->2 numbers-> 3 letters    
            System.out.print("Wrong character. Try again:");
            input.nextLine();

        }
        plateNum = input.nextLine();
        plateNum = plateNum.replaceAll("\\s", "");//removes empty spaces. 
        plateNum = plateNum.toUpperCase();

        return plateNum;

    }

    //takes input for string values and validates
    private String strInputVal() {

        String stringValue;
        Scanner in = new Scanner(System.in);
        while (!in.hasNext("[A-Za-z- ]+")) {//allows for letters, empty space and a dash. 
            System.out.print("Wrong character. Try again:");
            in.nextLine();
        }
        stringValue = in.nextLine();
        stringValue = stringValue.toUpperCase();

        return stringValue;
    }

    //takes input for integer values and validates them:
    private int intInputVal() {

        int number = 0;
        Scanner input = new Scanner(System.in);

        while (!input.hasNext("[0-9]*")) {//2 letters->2 numbers-> 3 letters    
            System.out.print("Wrong character. Try again:");
            input.nextLine();
        }
        try {
            number = input.nextInt();
        } catch (InputMismatchException e) {
            System.out.print("Enter integer, please: ");

        }
        return number;

    }

    //takes input for double values and validates them:
    private double dblInputVal() {
        double number = 0;
        Scanner input = new Scanner(System.in);

        while (!input.hasNext("[0-9.]*")) {
            System.out.print("Wrong character. Try again:");
            input.nextLine();
        }
        try {
            number = input.nextDouble();
        } catch (InputMismatchException e) {
            System.out.print("Enter a floating point number, please: ");

        }
        return number;

    }

    private int checkUpdate() {
        String plate;
        int toUpdate;
        Loop:
        while (true) {

            plate = plateInputVal();
            for (Vehicle item : vehList) {
                if (item.getVehPlateNum().equals(plate)) {
                    toUpdate = vehList.indexOf(item);

                    break Loop;
                }
            }
            System.out.println("No Such Plate Number in Our Records, Try again.\n");
        }
        return toUpdate;
    }

    public static void main(String[] args) throws FileNotFoundException {

        System.out.println("Wesminster Rental Management System\n");

        WestminsterRentalVehicleManager newList = new WestminsterRentalVehicleManager();

        newList.loadList();
        newList.menu();

    }

}
