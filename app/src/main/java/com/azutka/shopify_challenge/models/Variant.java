package com.azutka.shopify_challenge.models;

public class Variant {

    private String id;
    private String product_id;
    private String title;
    private String price;
    private boolean taxable;
    private String grams;
    private String weight;
    private String weight_unit;
    private int inventory_quantity;
    private boolean requires_shipping;

    public Variant() {
    }

    public Variant(String id, String product_id, String title, String price, boolean taxable, String grams, String weight, String weight_unit, int inventory_quantity, boolean requires_shipping) {
        this.id = id;
        this.product_id = product_id;
        this.title = title;
        this.price = price;
        this.taxable = taxable;
        this.grams = grams;
        this.weight = weight;
        this.weight_unit = weight_unit;
        this.inventory_quantity = inventory_quantity;
        this.requires_shipping = requires_shipping;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isTaxable() {
        return taxable;
    }

    public void setTaxable(boolean taxable) {
        this.taxable = taxable;
    }

    public String getGrams() {
        return grams;
    }

    public void setGrams(String grams) {
        this.grams = grams;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getWeight_unit() {
        return weight_unit;
    }

    public void setWeight_unit(String weight_unit) {
        this.weight_unit = weight_unit;
    }

    public int getInventory_quantity() {
        return inventory_quantity;
    }

    public void setInventory_quantity(int inventory_quantity) {
        this.inventory_quantity = inventory_quantity;
    }

    public boolean isRequires_shipping() {
        return requires_shipping;
    }

    public void setRequires_shipping(boolean requires_shipping) {
        this.requires_shipping = requires_shipping;
    }
}
