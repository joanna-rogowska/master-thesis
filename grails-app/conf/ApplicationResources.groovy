modules = {
    application {
        resource url:'js/application.js'
    }

    bootstrap {
        resource url:'css/bootstrap/bootstrap.min.css',attrs:[type:'css']
        dependsOn 'jquery'
    }

}