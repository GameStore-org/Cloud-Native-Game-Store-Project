package com.trilogyed.adminapi.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordUtility {

    public static void main(String[] args) {
        //Created a new instance of the pasword encoder
        PasswordEncoder enc = new BCryptPasswordEncoder();

        // declared an object that will hold our password
        String password = "password";

        //Object is passed into the encode method and printed in the console
        String encodedPassword = enc.encode(password);

        System.out.println(encodedPassword);





        //Created a new instance of the pasword encoder
        PasswordEncoder enc1 = new BCryptPasswordEncoder();

        // declared an object that will hold our password
        String password1 = "password1";

        //Object is passed into the encode method and printed in the console
        String encodedPassword1 = enc.encode(password1);

        System.out.println(encodedPassword1);




        //Created a new instance of the pasword encoder
        PasswordEncoder enc2 = new BCryptPasswordEncoder();

        // declared an object that will hold our password
        String password2 = "password2";

        //Object is passed into the encode method and printed in the console
        String encodedPassword2 = enc.encode(password2);

        System.out.println(encodedPassword2);


        //Created a new instance of the pasword encoder
        PasswordEncoder enc3 = new BCryptPasswordEncoder();

        // declared an object that will hold our password
        String password3 = "password3";

        //Object is passed into the encode method and printed in the console
        String encodedPassword3 = enc.encode(password3);

        System.out.println(encodedPassword3);
    }
}
