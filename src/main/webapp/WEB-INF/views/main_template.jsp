<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles" %>
<html>
  <head>
    <title>Wypozyczalnia filmow</title>
    <link href="<s:url value="/resources" />/css/rental.css" 
          rel="stylesheet" 
          type="text/css" />
  </head>

  <body>
    <div id="container">
      <a href="<s:url value="/home" />"><img 
          src="<s:url value="/resources" />/images/video.png" 
          border="0"/></a>
          
           <div id="leftSide">
        <t:insertAttribute name="leftSide" /> <!--<co id="co_tile_side" />-->
      </div>
      <div id="top">
        <t:insertAttribute name="top" /> <!--<co id="co_tile_top" />-->
      </div>
      
      
      <div id="rightSide">
        <t:insertAttribute name="rightSide" /> <!--<co id="co_tile_side" />-->
      </div>
     
      <div id="content">
        <t:insertAttribute name="content" /> <!--<co id="co_tile_content" />-->
      </div>
    </div>
  </body>
</html>
