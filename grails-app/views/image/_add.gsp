<div class="image-add">
    <div class="message error"><g:message code="${flash.message}"/></div>
    <g:uploadForm controller="image" action="save">
        <input type="file" name="fileName" id="fileName"/>
        <input type="submit" id="addImage"/>
    </g:uploadForm>
</div>