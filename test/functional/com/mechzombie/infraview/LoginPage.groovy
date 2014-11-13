/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mechzombie.infraview

import org.codehaus.groovy.grails.plugins.webdriver.WebDriverPage
import org.codehaus.groovy.grails.plugins.webdriver.ButtonElement
/**
 *
 * @author David
 */
class LoginPage extends WebDriverPage {

    static expectedTitle = "Login"
    static expectedURL = ~"/login/.*"

    String username
    String password
    String message

    ButtonElement<LoginPage> loginButton

    static elements = {
        message(By.xpath("//div[@class='messages']"))
    }
	
}

