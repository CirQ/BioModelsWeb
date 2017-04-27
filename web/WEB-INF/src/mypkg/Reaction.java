package mypkg;

import java.io.Serializable;

/**
 * Created by cirq on 2017-04-26.
 */
public class Reaction implements Serializable{
    String rid = "";
    String name = "";
    String reactants = "";
    String products = "";

    public String getRid() {
        return rid;
    }

    public String getName() {
        return name;
    }

    public String getReactants() {
        String[] reactant = reactants.split("@");
        String react = "";
        for(String s: reactant)
            react += (s + " ");
        return react.trim();
    }

    public String getProducts() {
        String[] product = products.split("@");
        String prod = "";
        for(String s: product)
            prod += (s + " ");
        return prod.trim();
    }

    public void setReactants(String reactants) {
        this.reactants = reactants;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public Reaction(String rid, String name, String reactants, String products) {
        this.rid = rid;
        this.name = name;
        this.reactants = reactants;
        this.products = products;
    }

    public Reaction() {}
}
