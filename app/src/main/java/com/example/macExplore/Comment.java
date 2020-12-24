/**
 * Comment Class
 *
 * This class serves as an object for storing comment data that is retrieved
 * from the database.
 */

package com.example.macExplore;

public class Comment{

        public String content;
        public long timestamp;
        public String user;
        public int totalVotes;

        public Comment(String content, long timestamp, String user, int totalVotes){
                this.content = content;
                this.timestamp = timestamp;
                this.user = user;
                this.totalVotes = totalVotes;
        }

        public String getContent(){
                return content;
        }

        public long getTimestamp(){
                return timestamp;
        }

        public void incrementVotes(){
                this.totalVotes++;
        }

        public void decrementVotes(){
                this.totalVotes--;
        }
}
