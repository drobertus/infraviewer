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
import groovy.util.logging.Log

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue
/**
 *
 * @author David
 */
@Log
class LoginTest {
    @Rule
    public WebDriverHelper webdriver = new WebDriverHelper()

    private LoginPage loginPage
    
    @Before
    public void openLoginPage() {
        println 'opening login page'
        LoginPage.expectedTitle = "Login"
        LoginPage.expectedURL = ~"/login/.*"
        loginPage = webdriver.open('/', LoginPage.class)
    }

    @Test
    public void testEmptyFieldsLogin() {
        println 'trying to click button'
        
        loginPage.submit.clickStay()
        assertEquals("Sorry, we were not able to find a user with that username and password.", loginPage.message)
    }

    @Test
    public void testUnSuccessfulLogin() {
        println 'unsuccessful login'
        loginPage.username = 'test'
        loginPage.password = 'wrong'
        loginPage.submit.clickStay()
        assertEquals("Sorry, we were not able to find a user with that username and password.", loginPage.message)
    }

    @Test
    public void testSuccessfulSULogin() {
        println 'successful login'
        
        loginPage.username = 'su@infraview.com'
        loginPage.password = 'pass'
        def btn = loginPage.submit
        LoginPage.expectedURL = ~"/sysadmin/index.*"
        LoginPage.expectedTitle = "InfraView - The Civic Infrastructure Manager"
        
        btn.click()
        def currPage = webdriver.currentPage.getCurrentURL()
        log.info('admin login url= ' + webdriver.currentPage.getCurrentURL())
        assertTrue(currPage.contains('/sysadmin/index'))
                
    }
    
    @Test
    public void testSuccessfulAdminLogin() {
        println 'successful login'
        
        loginPage.username = 'admin@longmont.gov'
        loginPage.password = 'pass'
        def btn = loginPage.submit
        LoginPage.expectedURL = ~"/.*"
        LoginPage.expectedTitle = "InfraViewer"
        
        btn.click()
        def currPage = webdriver.currentPage.getCurrentURL()
        log.info('admin login url= ' + currPage)
       // assertTrue(currPage.contains('/home/adminOnly'))
        
    }
    
    @Test
    public void testSuccessfulUserLogin() {
        println 'successful login'
        //LoginPage.expectedURL = ~"/home/index.*"
        
        loginPage.username = 'user@longmont.gov'
                loginPage.password = 'pass'
        def btn = loginPage.submit
        LoginPage.expectedURL = ~"/.*"
        LoginPage.expectedTitle = "InfraViewer"
        
        btn.click()
        def currPage = webdriver.currentPage.getCurrentURL()
        log.info('admin login url= ' +currPage)
        //assertTrue(currPage.contains('/home/index'))
        
    }
    
    
}

