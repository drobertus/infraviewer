package com.mechzombie.infraview

class StoredFileSet {
    
    SortedSet images
    
    static hasMany = [images: StoredFile]
    
    static constraints = {
        images nullable:true, blank: true
    }
    
    def getImageCount() {
        if(images) {
            return images.size()
        }
        return 0
    }

}
