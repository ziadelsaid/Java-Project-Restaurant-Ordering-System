import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Objects;

public class Restaurant {

    private final List<Customer> customers;
    Menu menu = new Menu();
    Order currentOrder;
    List<Promotion> promotions;
    int itemSelectionID;
    int quantitySelection;

    public Restaurant() {
        this.customers = new ArrayList<>();
        this.promotions = new ArrayList<>();
        menu.constructMenu();
        addPromotions();
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void addCustomer(Customer customer){
        this.customers.add(customer);
    }

    public Customer getCustomer(String phoneNumber) {
        for (Customer customer : this.getCustomers()) {
            if (Objects.equals(customer.getPhoneNumber(), phoneNumber)) {
                return customer;
            }
        }
        return null;
    }

    public void addPromotions(){
        promotions.add(new Promotion("Bremer50",50));
    }

    public boolean signIn(){
        System.out.println("""
                ---------------------
                [*] Close the program
                ---------------------
                Welcome to Bremer Restaurant
                ----------------------------
                Enter your phone number...""");

        boolean validNumber;
        do {
            String phoneNumber = Main.scannerLine.nextLine();

            if (Objects.equals(phoneNumber,"*")) {
                return false;
            } else if (!phoneNumber.matches("^01[012]\\d{8}$")) {
                System.out.println("You should enter valid phone number to Sign in");
                validNumber = false;
            } else {
                validNumber = true;
                boolean customerExists = false;

                for(Customer customer : this.customers){
                    if(customer.getPhoneNumber().equalsIgnoreCase(phoneNumber)){
                        customerExists = true;
                        break;
                    }
                }
                if(!customerExists){
                    System.out.println("Enter your name:");
                    String customerName = Main.scannerLine.nextLine();
                    addCustomer(new Customer(phoneNumber,customerName));
                }

                welcomePage(getCustomer(phoneNumber));
            }
        } while (!validNumber);

        return true;
    }

    public void welcomePage(Customer customer){
        boolean signOut = false;
        while (!signOut) {
            System.out.println("Welcome, " + customer.getName() + "\n" + """
                -------------------
                [1] Start ordering
                [2] Edit Profile
                [3] Show order history
                [4] Sign out""");

            int selection = Main.scannerInt.nextInt();

            switch(selection){
                case 1:
                    createOrder(customer);
                    break;
                case 2:
                    editProfile(customer);
                    break;
                case 3:
                    customer.showOrdersHistory();
                    System.out.println("Press any key to go back");
                    Main.scannerLine.nextLine();
                    break;
                case 4:
                    signOut = true;
                    break;
                default:
                    System.out.println("Wrong Selection");
            }
        }
    }

    public void createOrder(Customer customer) {

        currentOrder = new Order(customer.getOrdersHistory().size()+1);
        boolean createOrder = true;
        String searchItem = "";

        while (createOrder) {

            if(searchItem.isEmpty()){
                menu.showMenu();
            }else {
                int i = 1;
                boolean isExist = false;
                for (MenuItem menuItem : menu.getItems()){
                    if (menuItem.name().toLowerCase().contains(searchItem)){
                        System.out.println("\n["+ i +"] " + menuItem.name() + " | " + menuItem.price() + " EGP."
                                + "\n" + menuItem.description());
                        isExist = true;
                    }
                    i++;
                }
                if (!isExist){
                    menu.showMenu();
                    System.out.println("""
                            ------------------------------------
                            No search results matching""" + " '" + searchItem + "'");
                }
                searchItem = "";
            }

            currentOrder.displayOrderSummary();

            if(currentOrder.getItems().isEmpty()) {
                System.out.println("""
                [1] Add an item to order
                [2] Search for an item
                [*] Cancel""");
                switch (Main.scannerLine.nextLine()) {
                    case "1":
                        addItem();
                        break;
                    case "2":
                        System.out.println("Enter the item name");
                        searchItem = Main.scannerLine.nextLine();
                        break;

                    case "*":
                        createOrder = false;
                        break;

                    default:
                        break;
                }
            } else {
                System.out.println("""
                [1] Add an item to order
                [2] Search for an item
                [3] Remove item
                [4] Edit item quantity
                [5] Proceed to payment
                [6] Remove all items
                [*] cancel""");

                switch (Main.scannerLine.nextLine()) {
                    case "1":
                        addItem();
                        break;
                    case "2":
                        System.out.println("Enter the item name");
                        searchItem = Main.scannerLine.nextLine();
                        break;

                    case "3":
                        itemSelectionID = validateItemSelection("Order");
                        currentOrder.removeItem(currentOrder.getItems().get(itemSelectionID-1));
                        break;

                    case "4":
                        itemSelectionID = validateItemSelection("Order");
                        System.out.println("Enter desired quantity:");
                        try {
                            quantitySelection = Main.scannerInt.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Error: Invalid input.");
                        }
                        currentOrder.editQuantity(currentOrder.getItems().get(itemSelectionID-1), quantitySelection);
                        break;

                    case "5":
                        createOrder = proceedOrder(customer);
                        break;

                    case "6":
                        currentOrder.clearOrder();
                        break;

                    case "*":
                        createOrder = false;
                        break;

                    default:
                        break;
                }
            }

        }

    }

    public boolean proceedOrder (Customer customer) {

        String userInput;
        boolean validInput = false;

        do {
            System.out.println("""
                    Order type,
                    [1] Delivery
                    [2] Reservation
                    [*] Cancel""");

            userInput = Main.scannerLine.nextLine();

            switch (userInput) {
                case "1":
                    currentOrder.setOrderType("Delivery");
                    validInput = true;
                    break;
                case "2":
                    currentOrder.setOrderType("Reservation");
                    validInput = true;
                    break;
                case "*":
                    return true;
                default:
                    System.out.println("Enter valid input!");
            }

        } while (!validInput);

        if (Objects.equals(currentOrder.getOrderType(),"Delivery")) {
            int deliverySelection;

            do {
                customer.showAddresses();
                System.out.println("""
                                    [1] Select address
                                    [2] Manage addresses""");
                deliverySelection = Main.scannerInt.nextInt();

                if(deliverySelection == 1) {
                        if (customer.getAddresses().isEmpty()){
                            System.out.println("No addresses exits, please add a new address");
                            deliverySelection = 0;
                        }else {
                            System.out.println("Enter Address ID:");

                            boolean addressSelectionExists;
                            do {
                                int addressSelectionID = Main.scannerInt.nextInt();
                                if (addressSelectionID > customer.getAddresses().size() || addressSelectionID <= 0) {
                                    System.out.println("ID does not exist. Enter Address ID Again:");
                                    addressSelectionExists = false;
                                } else {
                                    currentOrder.setOrderAddress(customer.getAddresses().get(addressSelectionID-1));
                                    addressSelectionExists = true;
                                }
                            } while (!addressSelectionExists);

                        }
                } else {
                        customer.manageAddresses();
                }

            } while (deliverySelection != 1);
        }

        validInput = false;

        do {
            System.out.println("""
                    Order type,
                    [1] Cash
                    [2] VISA
                    [*] Cancel""");

            userInput = Main.scannerLine.nextLine();

            switch (userInput) {
                case "1":
                    currentOrder.setPaymentMethod("Cash");
                    validInput = true;
                    break;
                case "2":
                    currentOrder.setPaymentMethod("VISA");
                    validInput = true;
                    break;
                case "*":
                    return true;
                default:
                    System.out.println("Enter valid input");
            }

        } while (!validInput);

        currentOrder.displayOrder();

        System.out.println("Do you have a promo code?");

        boolean proceedPromo;
        do {
            System.out.println("Enter your promo code or press \"Enter\" to proceed");
            userInput = Main.scannerLine.nextLine();

            if (!userInput.isEmpty()) {
                proceedPromo = validatePromotion(currentOrder, userInput);
            } else {
                proceedPromo = true;
            }

        } while (!proceedPromo);

        currentOrder.displayOrder();

        do {
            System.out.println("""
                Do you want to confirm this order?
                [1] Yes
                [2] No""");

            userInput = Main.scannerLine.nextLine();

            switch (userInput) {
                case "1":
                    customer.addOrderToHistory(currentOrder);
                    System.out.println("Order Confirmed | Press any key to continue");
                    Main.scannerLine.nextLine();
                    return false;
                case "2":
                    return true;
                default:
                    System.out.println("Enter valid input");
            }
        } while (true);
    }

    public void editProfile (Customer customer) {
        System.out.println("""
                --------------------
                [1] Edit Name
                [2] Manage Addresses
                --------------------""");

        boolean validSelection = false;
        do {
            switch (Main.scannerInt.nextInt()) {
                case 1:
                    System.out.println("Enter new name:");
                    customer.setName(Main.scannerLine.nextLine());
                    validSelection = true;
                    break;
                case 2:
                    customer.manageAddresses();
                    validSelection = true;
                    break;
                default:
                    System.out.println("Enter valid input");
            }
        } while (!validSelection);
    }

    public boolean validatePromotion(Order order, String promoCode){
        boolean isExist = false;
        for (Promotion promotion : this.promotions){
            if (promotion.promoCode().equals(promoCode)){
                // Total order should be above 100 EGP.
                if(order.getTotalPrice() > 100){
                    order.setPromoCodeDiscount(promotion.discountValue());
                }else {
                    System.out.println("Your order should be above 100 EGP");
                }
                isExist = true;
            }
        }
        if (!isExist){
            System.out.println("Invalid promo code");
            return false;
        }
        return true;
    }

    public int validateItemSelection (String listName) {
        int size;
        if (Objects.equals(listName, "Menu")) {
            size = menu.getItems().size();
        } else {
            size = currentOrder.getItems().size();
        }

        System.out.println("Enter Item ID:");
        int itemSelectionID;
        boolean itemSelectionExists;
        do {
            itemSelectionID = Main.scannerInt.nextInt();
            if (itemSelectionID > size || itemSelectionID <= 0) {
                System.out.println("ID does not exist. Enter Item ID Again:");
                itemSelectionExists = false;
            } else {
                itemSelectionExists = true;
            }
        } while (!itemSelectionExists);
        return itemSelectionID;
    }

    public void addItem(){
        itemSelectionID = validateItemSelection("Menu");
        System.out.println("Enter desired quantity:");
        quantitySelection = Main.scannerInt.nextInt();
        currentOrder.addOrderItem(menu.getItems().get(itemSelectionID-1), quantitySelection);
    }
}
