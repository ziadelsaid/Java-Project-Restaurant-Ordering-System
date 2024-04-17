import java.util.ArrayList;
import java.util.List;

class Menu {
    private final List<MenuItem> items;
    public Menu() {
        items = new ArrayList<>();
    }
    public void addItem(MenuItem item) {
        items.add(item);
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public void constructMenu(){
        this.addItem(new MenuItem("BR fries","Crunchy bites drenched in buffalo sauce" +
                "and drizzled with ranch sauce.",89,"Appetizer"));
        this.addItem(new MenuItem("Texas fries","French fries topped with chili con" +
                "carne, cheese sauce, and jalapenos.",79,"Appetizer"));
        this.addItem(new MenuItem("Buffalo wings","Fried chicken wings drenched in" +
                "our special Buffalo sauce and drizzled with ranch sauce",89,"Appetizer"));
        this.addItem(new MenuItem("Tandoori Chicken","Yellow Indian rice topped with" +
                "325 gm chicken tandoori, served with Pico de Gallo and mayonnaise.",175,"Main Dish"));
        this.addItem(new MenuItem("Beef Stroganoff","Yellow rice topped with beef" +
                "stroganoff cooked in pepper sauce, peppers, and mushrooms.",279,"Main Dish"));
        this.addItem(new MenuItem("Sweet & Sour Chicken","Fried chicken cubes, cooked" +
                "with sweet and sour sauce, green and red peppers, and onions, served with egg noodles, a chicken" +
                "roll, and sweet chili sauce",199,"Main Dish"));
        this.addItem(new MenuItem("Molten Sticks","Five sticks of molten chocolate" +
                "rolled in fresh dough, cinnamon, and brown sugar.",123,"Dessert"));
        this.addItem(new MenuItem("PainPerdu","French toast stuffed with chocolate" +
                "sauce, dipped in vanilla sauce, and topped with vanilla ice cream and nuts.",118,"Dessert"));
        this.addItem(new MenuItem("Nutella Cookie Dough","A cookie dough topped with" +
                " vanilla ice cream and chocolate sauce.",118,"Dessert"));
    }
    public void showMenu(){
        int i = 1;
        System.out.println("""
                
                --------------------------------
                BREMER Restaurant | Dining Menu
                --------------------------------""");
        System.out.println("""
                Appetizers
                ----------""");
        for (MenuItem item : this.getItems()){
            if (item.category().equals("Appetizer")){
                System.out.println("["+ i +"] " + item.name() + " | " + item.price() + " EGP."
                        + "\n" + item.description());
                i++;
            }
        }
        System.out.println("""
                -----------
                Main Dishes
                -----------""");
        for (MenuItem item : this.getItems()){
            if (item.category().equals("Main Dish")){
                System.out.println("["+ i +"] " + item.name() + " | " + item.price() + " EGP."
                        + "\n" + item.description());
                i++;
            }
        }
        System.out.println("""
                -------
                Dessert
                -------""");
        for (MenuItem item : this.getItems()){
            if (item.category().equals("Dessert")){
                System.out.println("["+ i +"] " + item.name() + " | " + item.price() + " EGP."
                        + "\n" + item.description());
                i++;
            }
        }
    }
}