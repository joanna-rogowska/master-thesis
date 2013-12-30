<div id="imagesTable">
    <table class="table table-striped table-bordered ">
        <thead>
            <th><g:message code="image.table.id"/></th>
            <th><g:message code="image.table.name"/></th>
            <th><g:message code="image.table.image"/></th>
            <th><g:message code="image.table.descriptors"/></th>
            <th><g:message code="image.table.actions"/></th>
        </thead>
        <tbody>
        <g:if test='${imageList}'>
            <g:each in='${imageList}'>
                <tr>
                    <td>${it.id}</td>
                    <td>${it.name}</td>
                    <td>
                        <img src="${createLink(action: 'displayImage', params: [id: it.id])}"/>
                    </td>
                    <td>
                        <ul>
                            <li>CLD: ${it.descriptors.valuesAsList}</li>
                            <li>EHD:</li>
                            <li>HTD:</li>
                        </ul>
                    </td>
                    <td>
                        <g:remoteLink update="imagesTable" controller="image" action="delete" id="${it.id}">
                            <g:message code="image.actions.delete"/>
                        </g:remoteLink>
                    </td>
                </tr>
            </g:each>
        </g:if><g:else>
            <tr>
                <td colspan="5">
                    <g:message code="image.list.empty"/>
                </td>
            </tr>
        </g:else>
        </tbody>
    </table>
</div>