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
        public float avgRating;

        public Location(String name, String contact, String address, String documentID, float avgRating){
                this.name = name;
                this.contact = contact;
                this.address = address;
                this.documentID = documentID;
                this.avgRating = avgRating;
        }

        public String getDocumentID(){
                return documentID;
        }

        public void updateRating() {

        }
}
