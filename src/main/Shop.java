package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import model.Product;
import model.Sale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.spi.LoginModule;
import model.Client;
import model.Employee;
import model.Logable;

/*
Terminada actividad ra7
corregir errores scanners en int
 */
public class Shop {

    //private double cash = 100.00;
    private ArrayList<Product> inventory = new ArrayList<Product>();
    private int numberProducts;
    public ArrayList<Sale> sales = new ArrayList<Sale>();

    final static double TAX_RATE = 1.04;

    public Shop() {

    }

    public static void main(String[] args) {
        Shop shop = new Shop();

        try {
            shop.loadInventory();
        } catch (IOException ex) {
            Logger.getLogger(Shop.class.getName()).log(Level.SEVERE, null, ex);
        }

        Scanner scanner = new Scanner(System.in);
        int opcion = 0;
        boolean exit = false;

        int contador = 0;
        do {
            contador++;
            if (contador > 1) {
                System.out.println("Login incorrecto \n");
            }
        } while (initSession() == false);
        System.out.println("Login correcto.");

        do {
            System.out.println("\n");
            System.out.println("===========================");
            System.out.println("Menu principal miTienda.com");
            System.out.println("===========================");
            System.out.println("1) Contar caja");
            System.out.println("2) Añadir producto");
            System.out.println("3) Añadir stock");
            System.out.println("4) Marcar producto proxima caducidad");
            System.out.println("5) Ver inventario");
            System.out.println("6) Venta");
            System.out.println("7) Ver ventas");
            System.out.println("9) Eliminar producto");
            System.out.println("10) Salir programa");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    shop.showCash();
                    break;

                case 2:
                    shop.addProduct();
                    break;

                case 3:
                    shop.addStock();
                    break;

                case 4:
                    shop.setExpired();
                    break;

                case 5:
                    shop.showInventory();
                    break;

                case 6:
                    shop.sale();
                    break;

                case 7:
                    shop.showSales();
                    break;

                case 9:
                    shop.eliminarProducto();
                    break;

                case 10:
                    exit = true;
                    break;
            }
        } while (!exit);
    }

    /**
     * load initial inventory to shop
     */
    public void loadInventory() throws IOException {

        String userDir = System.getProperty("user.dir");
        String separator = File.separator;

        String filePath = userDir + separator + "files";

        File files = new File(filePath);

        if (!files.isDirectory()) {

            files.mkdir();

            String invPath = filePath + separator + "inputInventory.txt";

            File inventory = new File(invPath);

            inventory.createNewFile();

        }

        inventory.add(new Product("Manzana", 10.00, true, 10));
        inventory.add(new Product("Pera", 20.00, true, 20));
        inventory.add(new Product("Hamburguesa", 30.00, true, 30));
        inventory.add(new Product("Fresa", 5.00, true, 20));

    }

    /**
     * show current total cash
     */
    private void showCash() {
        System.out.println("Dinero actual: ");
    }

    /**
     * add a new product to inventory getting data from console
     */
    public void addProduct() {
        if (isInventoryFull()) {
            System.out.println("No se pueden añadir más productos");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Nombre: ");
        String name = scanner.nextLine();

        Product existingProduct = findProduct(name);
        if (existingProduct == null) {
            System.out.print("Precio mayorista: ");
            double wholesalerPrice = scanner.nextDouble();
            System.out.print("Stock: ");
            int stock = scanner.nextInt();

            addProduct(new Product(name, wholesalerPrice, true, stock));
        } else {
            System.out.println("Este producto ya existe");
        }
    }

    /**
     * add stock for a specific product
     */
    public void addStock() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Seleccione un nombre de producto: ");
        String name = scanner.next();
        Product product = findProduct(name);

        if (product != null) {
            // ask for stock
            System.out.print("Seleccione la cantidad a añadir: ");
            int stock = scanner.nextInt();
            // update stock product
            inventory.get(inventory.indexOf(name)).setStock(stock);
            System.out.println("El stock del producto " + name + " ha sido actualizado a " + inventory.get(inventory.indexOf(name)).getStock());

        } else {
            System.out.println("No se ha encontrado el producto con nombre " + name);
        }
    }

    /**
     * set a product as expired
     */
    private void setExpired() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Seleccione un nombre de producto: ");
        String name = scanner.nextLine();

        Product product = findProduct(name);

        if (product != null) {

            inventory.get(inventory.indexOf(name)).expire();
            System.out.println("El precio del producto " + name + " ha sido actualizado a " + inventory.get(inventory.indexOf(name)).getWholesalerPrice());

        }
    }

    /**
     * show all inventory
     */
    public void showInventory() {
        System.out.println("Contenido actual de la tienda:");

        for (Product product : inventory) {
            if (product != null) {
                System.out.println(product.getName() + ". Stock: " + product.getStock() + " Precio: " + product.getWholesalerPrice());
            }
        }
    }

    /**
     * make a sale of products to a client
     */
    public void sale() {
        Client c = new Client();
        int indice = sales.size();
        // ask for client name
        Scanner sc = new Scanner(System.in);
        String client;

        System.out.println("Realizar venta, escribir nombre cliente");

        client = sc.nextLine();

        c.setNombre(client);
        // sale product until input name is not 0
        double totalAmount = 0.0;

        //Product[] shoppingCart = new Product[10];
        ArrayList<Product> shoppingCart = new ArrayList<Product>();
        String name = "";

        do {

            System.out.println("Introduce el nombre del producto, escribir 0 para terminar:");
            name = sc.nextLine();
            if (name.equals("0")) {
                break;
            } else {
                Product product = findProduct(name);
                boolean productAvailable = false;

                if (product != null && product.isAvailable()) {
                    productAvailable = true;
                    totalAmount += product.getWholesalerPrice();
                    product.setStock(product.getStock() - 1);
                    // if no more stock, set as not available to sale
                    if (product.getStock() == 0) {
                        product.setAvailable(false);
                    }

                    shoppingCart.add(product);

                    System.out.println("Producto añadido con éxito");
                }

                if (!productAvailable) {
                    System.out.println("Producto no encontrado o sin stock");
                }
            }
        } while (!name.equals("0"));

        // show cost total
        totalAmount = totalAmount * TAX_RATE;

        if (c.pay(Client.MEMBER_ID, Client.BALANCE, totalAmount) == true) {
            indice++;
            sales.add(new Sale(c.getNombre(), shoppingCart, totalAmount, indice));

            System.out.println("Venta realizada con éxito, total: " + totalAmount);
            System.out.println("Saldo actual: " + c.getBalance());
        } else {
            indice++;
            sales.add(new Sale(c.getNombre(), shoppingCart, totalAmount, indice));
            System.out.println("Saldo insuficiente, debes " + c.getBalance());

        }
    }

    /**
     * show all sales
     */
    private void showSales() {
        System.out.println("Lista de ventas:");
        for (Sale sale : sales) {

            if (sale != null) {
                System.out.println(sale.toString());
            }

        }

        System.out.println("Quieres guardar las ventas de hoy en un archivo? (S/N)");
        Scanner sc = new Scanner(System.in);

        if (sc.nextLine().equalsIgnoreCase("s")) {

            String userDir = System.getProperty("user.dir");
            String separator = File.separator;

            String salesDir = userDir + separator + "files" + separator + "sales_" + LocalDate.now() + ".txt";

            File salesF = new File(salesDir);
            if (!salesF.isFile()) {
                try {
                    salesF.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(Shop.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            try {
                FileReader fr = new FileReader(salesF);
                
                BufferedReader br = new BufferedReader(fr);
                
                
                
                //String existingString = br.readLine();
                
                
                
                ArrayList <String> lines = new ArrayList();
                
                String allSales = "";
                
                String existingString;
                
                while ((existingString = br.readLine()) != null) {
                lines.add(existingString);
                }
                
                
                
                fr.close();
                br.close();
                
                FileWriter fw = new FileWriter(salesF);

                BufferedWriter bw = new BufferedWriter(fw);
                
                
                for (String line : lines) {
                    for (Sale sale : sales) {
                        if (line.equals(sale.toStringFileFormat())) {
                        } else {
                            bw.write(line);
                            bw.newLine();
                            
                        }
                        }
                            
                    }
                
                for (Sale sale : sales){
                    
                    bw.write(sale.toStringFileFormat());
                    bw.newLine();
                    }
                
                /*
                for (int i = 0; i < sales.size(); i++) {
                    allSales = "\n" +sales.get(i).toStringFileFormat();
                }

                bw.write(allSales);
                */
                
                bw.flush();
                bw.close();

            } catch (IOException ex) {
                Logger.getLogger(Shop.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    /**
     * add a product to inventory
     *
     * @param product
     */
    public void addProduct(Product product) {

        inventory.add(product);

        if (isInventoryFull()) {
            System.out.println("No se pueden añadir más productos, se ha alcanzado el máximo de " + inventory.size());
        }
    }


    /*
        inventory[numberProducts] = product;
        numberProducts++;
    }

    /**
     * check if inventory is full or not
     *
     * @return true if inventory is full
     */
    public boolean isInventoryFull() {
        if (numberProducts == 10) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * find product by name
     *
     * @param name
     * @return product found by name
     */
    public Product findProduct(String name) {

        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getName().equalsIgnoreCase(name)) {
                return inventory.get(i);
            }
        }
        return null;
    }

    public void eliminarProducto() {
        String name;

        System.out.println("Que productos que quieras eliminar? (0 para salir)");
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("Introduce el producto");
            name = sc.nextLine();

            Product product = findProduct(name);
            if (product != null) {
                inventory.remove(product);
                System.out.println("El producto se ha eliminado\n");
            } else if (name.equals("0")) {
                System.out.println("Saliendo...");
            } else {
                System.out.println("El producto no existe\n");
            }

        } while (!name.equals("0"));
    }

    public static boolean initSession() {

        Scanner sc = new Scanner(System.in);

        System.out.println("What is your id?");
        int id = sc.nextInt();
        Scanner sc1 = new Scanner(System.in);
        System.out.println("What is your password?");
        String password = sc1.nextLine();

        Employee employeeLogin = new Employee();

        return employeeLogin.login(id, password);

    }

}
