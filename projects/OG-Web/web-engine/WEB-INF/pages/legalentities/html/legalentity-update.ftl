<#escape x as x?html>
<@page title="Update - ${legalEntityDoc.name}" jquery=true aceXmlEditor=true>


<#-- SECTION Update legal entity -->
<@section title="Update legal entity">
  <@form method="PUT" action="${uris.legalEntity()}" id="updateForm">
  <p>
    <#if err_nameMissing??><div class="err">The name must be entered</div></#if>
    <@rowin label="Name"><input type="text" size="30" maxlength="80" name="name" value="${legalEntityDoc.name}" /></@rowin>
    <@rowin>
      <div id="ace-xml-editor"></div>
    </@rowin>
    <input type="hidden" name="legalEntityXML" id="legalEntity-xml"/>
    <@rowin><input type="submit" value="Update" /></@rowin>
    
    <#noescape><@xmlEditorScript formId="updateForm" inputId="legalEntity-xml" xmlValue="${legalEntityXML}"></@xmlEditorScript></#noescape>
  </p>
  </@form>
</@section>


<#-- SECTION Links -->
<@section title="Links">
  <p>
    <a href="${uris.legalEntities()}">LegalEntity home</a><br />
    <a href="${homeUris.home()}">Home</a><br />
  </p>
</@section>
<#-- END -->
</@page>
</#escape>
