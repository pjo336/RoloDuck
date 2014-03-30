<style>
div.errorblock {
color: #ff0000;
background-color: #ffEEEE;
border: 3px solid #ff0000;
padding: 8px;
margin: 16px;
}
</style>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${not empty exceptions}">
    <div class="errorblock">
        ${exceptions.message}
    </div>
</c:if>
