
        package BookstoreManagement;

        import java.util.Date;


        import java.sql.*;
        public class Modelmember {

            private Integer user_id;
            private String username;
            private byte[] password;
            private String email;
            private Date birthday;
            private String gender;
            private String image;
            private String role;
            private Timestamp created_at;
            private String cardnumber;

        public Integer getUser_id() {
            return user_id;
        }

        public String getUsername() {
            return username;
        }

        public byte[] getPassword() {
            return password;
        }

        public String getEmail() {
            return email;
        }

        public Date getBirthday() {
            return birthday;
        }

        public String getGender() {
            return gender;
        }

        public String getImage() {
            return image;
        }

        public String getRole() {
            return role;
        }

        public Timestamp getCreated_at() {
            return created_at;
        }
        public String getCardnumber(){
            return cardnumber;
        }

        public Modelmember(Integer user_id, String username, byte[] password, String email, Date birthday, String gender, String image, String role, Timestamp created_at, String cardnumber) {
            this.user_id = user_id;
            this.username = username;
            this.password = password;
            this.email = email;
            this.birthday = birthday;
            this.gender = gender;
            this.image = image;
            this.role = role;
            this.created_at = created_at;
            this.cardnumber = cardnumber;
        }




        }