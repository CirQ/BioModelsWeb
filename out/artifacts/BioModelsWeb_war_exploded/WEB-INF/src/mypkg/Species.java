package mypkg;

/**
 * Created by cirq on 2017-04-12.
 */
public class Species {
    private String id = "";
    private double initial_amount = 0.0;
    private boolean has_only_substance_units = false;
    private String name = "";
    private String metaid = "";
    private String compartment = "";

    public String getId() {
        return id;
    }
    public double getInitial_amount() {
        return initial_amount;
    }
    public boolean isHas_only_substance_units() {
        return has_only_substance_units;
    }
    public String getName() {
        return name;
    }
    public String getMetaid() {
        return metaid;
    }
    public String getCompartment() {
        return compartment;
    }

    public Species(String id, double initial_amount, boolean has_only_substance_units, String name, String metaid, String compartment) {
        this.id = id;
        this.initial_amount = initial_amount;
        this.has_only_substance_units = has_only_substance_units;
        this.name = name;
        this.metaid = metaid;
        this.compartment = compartment;
    }
}
