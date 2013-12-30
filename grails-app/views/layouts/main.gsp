<html>
    <head>
        <title><g:layoutTitle default="ASIOM" /></title>
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap/bootstrap.min.css')}" type="text/css">
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'custom.css')}" type="text/css">
        <g:layoutHead />
    </head>
    <body>
        <g:render template="/layouts/header"/>
        <div id="content-wrapper" class="bs-header">
            <g:layoutBody />
            <div id="footer-push"></div>
        </div>
        <g:render template="/layouts/footer"/>
    </body>
</html>