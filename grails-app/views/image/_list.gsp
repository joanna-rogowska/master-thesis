<g:if test='${imageList}'>
    <div id="imagesTable">
        <ol>
            <li>
                <div id="results">
                    <g:each in='${imageList}' >
                        <div id="result_${it.id}" class="result-image">
                            <div class="image-result">
                                <img src="${createLink(action: 'displayImage', params: [id: it.id])}"/>
                            </div>
                        </div>
                    </g:each>
                </div>
            </li>
        </ol>
    </div>
</g:if>
<g:else>
    <div class="alert alert-info">
        <g:message code="image.list.empty"/>
    </div>
</g:else>