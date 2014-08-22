package com.mechzombie.infraview


import com.mechzombie.infraview.service.*
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ReportController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def reportService
    
    def index(Integer max) {
        
        def theEnterprise = session['activeEnterprise']
        def entRef = Enterprise.get(theEnterprise.ident())
        params.max = Math.min(max ?: 10, 100)
        def reports = Report.findAllByEnterprise(theEnterprise, params)
        respond reports, model:[reportInstanceCount: reports.size(), enterprise: entRef]
        
        //params.max = Math.min(max ?: 10, 100)
        //respond Report.list(params), model:[reportInstanceCount: Report.count()]
    }

    def show(Report reportInstance) {
        respond reportInstance
    }

    def download() {
        
        def rpt = Report.get(params.id)
        println("report path- ${rpt.pathToReportFile}")
                
        def theFileToShow = rpt.pathToReportFile
        def theFile = new File(theFileToShow)
        response.setHeader "Content-disposition", "attachment";
        response.setHeader("Content-Type", "text/xml") 
        response.setHeader("Content-disposition", "attachment;filename=${theFile.name}")
        response.outputStream << theFile.newInputStream()

    }
    
    @Transactional
    def create() {
        println("creating new report")
        
        def ent = session['activeEnterprise']
        def freshEnt = Enterprise.get(ent.ident())
        
        println("enterprise=${freshEnt}")
        //def ac = ent.assetClasses
        println("assetClasses=${freshEnt.assetClasses}")
        def rpt = new Report(params)
        rpt.enterprise = freshEnt
        respond rpt
        //respond new Report(params)
    }

    @Transactional
    def save(Report reportInstance) {
       println "save params = ${params}"
        if (reportInstance == null) {
            notFound()
            return
        }

        //TODO: better error handling if this is null
        def ent = Enterprise.load(Integer.parseInt(params['enterprise.id']))
        reportInstance.enterprise = ent
        if (reportInstance.hasErrors()) {         
            println("Errors on save=${reportInstance.errors}")
            respond reportInstance.errors, view:'create'
            return
        }

        reportInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.created.message', args: [message(code: 'reportInstance.label', default: 'Report'), reportInstance.id])
                redirect reportInstance
            }
            '*' { respond reportInstance, [status: CREATED] }
        }
    }

    def edit(Report reportInstance) {
        respond reportInstance
    }

    @Transactional
    def update(Report reportInstance) {
        if (reportInstance == null) {
            notFound()
            return
        }

        if (reportInstance.hasErrors()) {
            respond reportInstance.errors, view:'edit'
            return
        }

        reportInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Report.label', default: 'Report'), reportInstance.id])
                redirect reportInstance
            }
            '*'{ respond reportInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Report reportInstance) {

        if (reportInstance == null) {
            notFound()
            return
        }

        reportInstance.delete flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Report.label', default: 'Report'), reportInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'reportInstance.label', default: 'Report'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
    
    // TODO: this should rarely be invoked if the 
    // trigger is a timer
    def runReport() {
        
        def rpt = Report.get(params.id)
        println("calling to Service for ${rpt.id}")
        
        String content = g.render(template: "templates/reportHeaderTemplate" , model:[report: rpt])
        println "Template result= ${content}"
        
        rpt.pathToReportFile = reportService.runReport(rpt)        
        rpt.save(flush: true)
        return "Report complete"
    }
}
