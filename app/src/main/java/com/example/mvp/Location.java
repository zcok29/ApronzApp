/**
 * Location Class
 *
 * This class serves as an object for storing location data that is retrieved
 * from the database.
 */

package com.example.mvp;

public class Location {

        public String contact;
        public String name;
        public String address;

        public Location(String name, String contact, String address){
                this.name = name;
                this.contact = contact;
                this.address = address;
        }

}
