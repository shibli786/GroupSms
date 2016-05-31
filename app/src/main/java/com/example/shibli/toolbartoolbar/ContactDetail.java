package com.example.shibli.toolbartoolbar;

/**
 * Created by shibli on 4/26/2016.
 */
public class ContactDetail implements  Comparable {

    public ContactDetail(String name, String number2, String number1) {
        this.name = name;
        this.number2 = number2;
        this.number1 = number1;
    }

    public String getName() {
        return name;
    }

    public String getNumber1() {
        return number1;
    }

    public String getNumber2() {
        return number2;
    }

    String name,number1,number2;


    @Override
    public int compareTo(Object another) {
        ContactDetail d=(ContactDetail)another;
        return this.name.compareTo(d.name);

    }


    @Override
    public boolean equals(Object o) {
        if (o instanceof ContactDetail) {
            if (name.equals(((ContactDetail) o).name) && number1.equals(((ContactDetail) o).number1)) {

                return true;
            }
        }
        return false;
    }
}



