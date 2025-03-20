/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sginegocio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.mycompany.sginegocio","mx.itson.sgi.data_access"})
@EnableAutoConfiguration
public class SGINegocio {
	public static void main(String[] args) {
	        SpringApplication.run(SGINegocio.class, args);
    }
}
