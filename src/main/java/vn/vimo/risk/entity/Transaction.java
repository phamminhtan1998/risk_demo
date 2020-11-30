package vn.vimo.risk.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@ToString

public class Transaction {

        @JsonProperty("id")
        private int id ;
        @JsonProperty("title")
        private String title ;
        @JsonProperty("time")
        private Date time ;
        @JsonProperty("username")
        private String username;
        @JsonProperty("type")
        private String type ;
        @JsonProperty("authentication")
        private boolean isAuthentication;

        public Transaction() {
        }

        public Transaction(int id, String title, Date time, String username, String type, boolean isAuthentication) {
                this.id = id;
                this.title = title;
                this.time = time;
                this.username = username;
                this.type = type;
                this.isAuthentication = isAuthentication;
        }

        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public String getTitle() {
                return title;
        }

        public void setTitle(String title) {
                this.title = title;
        }

        public Date getTime() {
                return time;
        }

        public void setTime(Date time) {
                this.time = time;
        }

        public String getUsername() {
                return username;
        }

        public void setUsername(String username) {
                this.username = username;
        }

        public String getType() {
                return type;
        }

        public void setType(String type) {
                this.type = type;
        }

        public boolean isAuthentication() {
                return isAuthentication;
        }

        public void setAuthentication(boolean authentication) {
                isAuthentication = authentication;
        }
}
