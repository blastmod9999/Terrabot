package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(value = {"x", "y", "scanned"})
public abstract class Entities {
    private String type;
    private String name;
    private double mass;
    @JsonIgnore
    private boolean scanned = false;
    private ArrayList<Sections> sections;

    //pt remove after plant
    private int x;
    private int y;

    public boolean isScanned() {
        return scanned;
    }

    public void setScanned(final boolean scanned) {
        this.scanned = scanned;
    }

    /**
     * Javadoc for method getX.
     */
    public int getX() {
        return x;
    }

    /**
     * Javadoc for method setX.
     */
    public void setX(final int x) {
        this.x = x;
    }

    /**
     * Javadoc for method getY.
     */
    public int getY() {
        return y;
    }

    /**
     * Javadoc for method setY.
     */
    public void setY(final int y) {
        this.y = y;
    }

    /**
     * Javadoc for method getType.
     */
    public String getType() {
        return type;
    }

    /**
     * Javadoc for method setType.
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * Javadoc for method getName.
     */
    public String getName() {
        return name;
    }

    /**
     * Javadoc for method setName.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Javadoc for method getMass.
     */
    public double getMass() {
        return mass;
    }

    /**
     * Javadoc for method setMass.
     */
    public void setMass(final double mass) {
        this.mass = mass;
    }

    /**
     * Javadoc for method getSections.
     */
    public ArrayList<Sections> getSections() {
        return sections;
    }

    /**
     * Javadoc for method setSections.
     */
    public void setSections(final ArrayList<Sections> sections) {
        this.sections = sections;
    }
}
