import java.util.ArrayList;
import java.util.List;

public class Customer {
    private final String phoneNumber;
    private String name;
    private final List<String> addresses;
    private final List<Order> ordersHistory;

    public Customer(String phoneNumber, String name) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.ordersHistory = new ArrayList<>();
        this.addresses = new ArrayList<>();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAddresses() {
        return addresses;
    }

    public List<Order> getOrdersHistory() {
        return ordersHistory;
    }

    public void addOrderToHistory(Order order){
        this.ordersHistory.add(order);
    }

    public void showOrdersHistory(){
        if(ordersHistory.isEmpty()){
            System.out.println("""
                    ------------------------
                    No previous orders exist
                    ------------------------""");
        }else {
            for(Order order : this.getOrdersHistory()){
                order.displayOrder();
            }
        }
    }

    public void addAddress(String address){
        this.addresses.add(address);
    }

    public void removeAddress(int index){
        this.addresses.remove(index);
    }

    public void editAddress(int index, String newAddress){
        this.addresses.set(index,newAddress);

    }

    public void showAddresses() {
        int i = 1;
        System.out.println("""
                ---------
                Addresses
                ---------""");

        if (!this.addresses.isEmpty()){
            for(String address : this.getAddresses()){
                System.out.println("Address [" + i + "]: " + address);
                i++;
            }
        }else {
            System.out.println("There are no existing addresses");
        }

        System.out.println("---------------------------------");
    }

    public void manageAddresses(){

        this.showAddresses();

        System.out.println("""
                [1] Add new address
                [2] Edit or remove address
                [3] Cancel""");

        switch(Main.scannerInt.nextInt()){
            case 1:
                System.out.println("Enter the new address:");
                addAddress(Main.scannerLine.nextLine());
                manageAddresses();
                break;
            case 2:
                if(addresses.isEmpty()){
                    System.out.println("No addresses exist");
                }else{
                    System.out.println("""
                [1] Edit
                [2] Remove""");
                    int editSelection = Main.scannerInt.nextInt();
                    System.out.println("Choose the address");
                    int addressSelection = Main.scannerInt.nextInt();
                    if (editSelection == 1) {
                        System.out.println("Enter new address:");
                        editAddress(addressSelection-1,Main.scannerLine.nextLine());
                    } else if(editSelection == 2) {
                        if(addresses.isEmpty()){
                            System.out.println("No addresses exist");
                        }else {
                            removeAddress(addressSelection-1);
                        }
                    }
                }
                manageAddresses();
                break;
            case 3:
                return;
            default:
                System.out.println("Wrong Selection | Back to Welcome Page...");
        }
    }
}
