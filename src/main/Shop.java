package main;

import java.util.ArrayList;
import model.Product;
import model.Sale;
import java.util.Scanner;


public class Shop {

    private double cash = 100.00;
    private ArrayList<Product> inventory = new ArrayList<Product>();
    private int numberProducts;
    public ArrayList <Sale> sales = new ArrayList<Sale>();
    

    final static double TAX_RATE = 1.04;

    public Shop() {
        
        
    }

    public static void main(String[] args) {
        Shop shop = new Shop();

        shop.loadInventory();

        Scanner scanner = new Scanner(System.in);
        int opcion = 0;
        boolean exit = false;

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
    public void loadInventory() {
        inventory.add(new Product("Manzana", 10.00, true, 10));
        inventory.add(new Product("Pera", 20.00, true, 20));
        inventory.add(new Product("Hamburguesa", 30.00, true, 30));
        inventory.add(new Product("Fresa", 5.00, true, 20));
    }

    /**
     * show current total cash
     */
    private void showCash() {
        System.out.println("Dinero actual: " + cash);
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
        // ask for client name
        Scanner sc = new Scanner(System.in);
        System.out.println("Realizar venta, escribir nombre cliente");
        String client = sc.nextLine();

        // sale product until input name is not 0
        double totalAmount = 0.0;
        String name = "";
        //Product[] shoppingCart = new Product[10];
        ArrayList<Product> shoppingCart = new ArrayList<Product>();
        while (!name.equals("0")) {
            System.out.println("Introduce el nombre del producto, escribir 0 para terminar:");
            name = sc.nextLine();

            if (name.equals("0")) {
                break;
            }
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

        // show cost total
        totalAmount = totalAmount * TAX_RATE;
        cash += totalAmount;
        
        sales.add(new Sale(client, shoppingCart, totalAmount));
        
        System.out.println("Venta realizada con éxito, total: " + totalAmount);
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
            return;
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
            if (inventory.get(i).getName().equalsIgnoreCase(name)){
            return inventory.get(i);
            }
        }
        return null;
        }
        
    
    public void eliminarProducto(){
        String name;
        
        System.out.println("Que productos que quieras eliminar? (0 para salir)");
        Scanner sc = new Scanner(System.in);
        do{
        System.out.println("Introduce el producto");
        name = sc.nextLine();
        
        Product product = findProduct(name);
        if (product !=null){
        inventory.remove(product);
            System.out.println("El producto se ha eliminado\n");
        }
        else if (name.equals("0")){
                    System.out.println("Saliendo...");
                }
        else{
            System.out.println("El producto no existe\n");
        }
        
        }while (!name.equals("0"));
    }
    
}
