package com.mechzombie.infraview.service.files

import com.mechzombie.infraview.filedata.EnterpriseFolder
import com.mechzombie.infraview.filedata.FileRef
import grails.transaction.Transactional
import javax.imageio.ImageReader
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream
import java.awt.image.BufferedImage;

@Transactional
class FileService {
    
    def grailsApplication
    static def entTempSubFolderName = "tmp"    
    def enterpriseFolders = [:]    
    /**
    * Each enterprise can have a "temporary" Logo and an active one
    * 
    */
    def uploadEnterpriseLogo(byte[] input, String tmpfileName, long entId) {
        EnterpriseFolder folder = getFolderForEnterprise(entId)
        
        //TODO: decouple file format.  applynecessary metadata and return
        //to client (type, and a scaled height/width proportion)
        if(folder.logo) {
            folder.logo.delete()
        }
        def logoFileName = "logo.jpg"
        def logo = new File(folder.mainFolder, logoFileName)
        def saved = logo.newOutputStream()       
        
        saved   << input
        saved.close()
        folder.logo = logo
    }
    
    
    def getCurrentEnterpriseLogo(long entId) {
        
        EnterpriseFolder folder = getFolderForEnterprise(entId)

        if (folder.logo) {
         return folder.logo.bytes
        }
        return null
    }
    

    
    EnterpriseFolder getFolderForEnterprise(long entId) {
        
        def folderPath = grailsApplication.config.infraview.files.root_folder    
        
        EnterpriseFolder entFolder = enterpriseFolders[entId]
        
        if(!entFolder) {
            entFolder = new EnterpriseFolder()
            entFolder.enterpriseId = entId
            File folder = new File(folderPath + "/${entId}/")
            if (!folder.exists()) {
                folder.mkdirs()        
            }
            entFolder.mainFolder = folder
            File tmpEntFolder = new File(folder, entTempSubFolderName) 
            if (!tmpEntFolder.exists()) {
                tmpEntFolder.mkdirs()        
            }   
            entFolder.tmpFolder = tmpEntFolder
            
            // set the logo as this is frequently returned
            if(!entFolder.logo) {
                File theLogo = new File(entFolder.mainFolder, "logo.jpg")
                if (theLogo.exists()) {
                    entFolder.logo = theLogo
                }
            }
            enterpriseFolders.put(entId, entFolder)
        }
        return entFolder
    }
}
