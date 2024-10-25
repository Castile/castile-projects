package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws FileNotFoundException {
        System.out.println( "Hello World!" );

        FileInputStream inputStream = new FileInputStream("/Users/castile/Documents/SoftWareConfiguration");

    }
}
