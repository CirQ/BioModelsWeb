package mypkg;

/**
 * Created by cirq on 2017-04-12.
 */
public class Species {
    private String sid = "";
    private double initial_amount = 0.0;
    private String name = "";
    private String compartment = "";

    public String getSid() {
        return sid;
    }

    public double getInitial_amount() {
        return initial_amount;
    }

    public String getName() {
        return name;
    }

    public String getCompartment() {
        return compartment;
    }


    public Species(String sid, double initial_amount, String name, String compartment) {
        this.sid = sid;
        this.initial_amount = initial_amount;
        this.name = name;
        this.compartment = compartment;
    }
}
