package org.poo.banking.seller;

public class Seller {
    private String name;
    private String productsCategory;

    public Seller(String name, String productsCategory) {
        this.name = name;
        this.productsCategory = productsCategory;
    }

    public String getName() {
        return name;
    }

    public String getProductsCategory() {
        return productsCategory;
    }
}
