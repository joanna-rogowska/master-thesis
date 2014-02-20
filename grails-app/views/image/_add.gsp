<div class="image-add">
    <div class="message error"><g:message code="${flash.message}"/></div>
    <g:uploadForm controller="image" class="uploadFile-form">
        <input type="file" name="fileName" id="fileName"/>
        <g:actionSubmitImage id="addImage" action="save" value="add"
                             src="${resource(dir: 'images/icons', file: 'add-icon.png')}"/>
        <g:actionSubmitImage id="searchImage" action="searchWithImage" value="search"
                             src="${resource(dir: 'images/icons', file: 'search-icon.png')}"/>
    </g:uploadForm>
</div>