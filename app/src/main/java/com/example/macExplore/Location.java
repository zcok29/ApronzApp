/**
 * Location Class
 *
 * This class serves as an object for storing location data that is retrieved
 * from the database.
 */

package com.example.macExplore;

public class Location {

        public String contact;
        public String name;
        public String address;
        public String documentID;

        public Location(String name, String contact, String address, String doucmentID){
                this.name = name;
                this.contact = contact;
                this.address = address;
                this.documentID = doucmentID;
        }

        public String getDocumentID(){
                return documentID;
        }

}
