/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mechzombie.infraview

import org.codehaus.groovy.grails.plugins.webdriver.WebDriverHelper
import org.junit.Rule
import org.junit.Before
import org.junit.Test
/**
 *
 * @author David
 */
class LoginTest {
    @Rule
    public WebDriverHelper webdriver = new WebDriverHelper()

    private LoginPage loginPage

    @Before
    public void openLoginPage() {
        loginPage = webdriver.open('/', LoginPage.class)
    }

    @Test
    public void testEmptyFieldsLogin() {
        loginPage.loginButton.clickStay()
        assertEquals("Incorrect username or password", loginPage.error)
    }

    @Test
    public void testUnSuccessfulLogin() {
        loginPage.username = 'test'
        loginPage.password = 'wrong'
        loginPage.loginButton.clickStay()
        assertEquals("Incorrect username or password", loginPage.error)
    }

    @Test
    public void testSuccessfulLogin() {
        loginPage.username = 'test'
        loginPage.password = 'password'
        def homePage = loginPage.loginButton.click()
        assertEquals("Login Successful", homePage.message)
    }
}

