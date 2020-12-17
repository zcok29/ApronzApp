/**
 * Comment Class
 *
 * This class serves as an object for storing comment data that is retrieved
 * from the database.
 */

package com.example.macExplore;

public class Comment{

        public String content;
        public String timestamp;
        public String user;

        public Comment(String content, String timestamp, String user){
                this.content = content;
                this.timestamp = timestamp;
                this.user = user;
        }

        public String getContent(){
                return content;
        }

        public String getTimestamp(){
                return timestamp;
        }

        public String getUser(){return user;}

}
