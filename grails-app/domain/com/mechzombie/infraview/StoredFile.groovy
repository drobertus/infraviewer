package com.mechzombie.infraview

class StoredFile {

    String genId
    String fileName
    String fileType
    String storagePath
    Date uploadDate
    String notes  
    long fileSize
    
    int height
    int width        
    
    static constraints = {
        notes nullable: true, blank: true
        genId unique: true, nullable: false, blank: false
    }
    
    def getImageId() {
        this.genId
    }
}
