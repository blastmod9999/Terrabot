package entities;

import main.*;
import java.util.ArrayList;

public abstract class Entities {
    public String type;
    public String name;
    public double mass;
    public ArrayList<Sections> sections;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public ArrayList<Sections> getSections() {
        return sections;
    }

    public void setSections(ArrayList<Sections> sections) {
        this.sections = sections;
    }
}
