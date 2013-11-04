<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	
	<xsl:output method="html" />
	
	<xsl:template match="/">
		<xsl:for-each select="//category">
			<xsl:call-template name="category" />
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="category">
		<table id="products">
			<tr>
				<td id="category">
					<a href="href">
						<xsl:attribute name="href">transformcategory.do?categoryName=<xsl:value-of
							select="@name" /></xsl:attribute>
						<xsl:value-of select="@name" />
						(
						<xsl:value-of select="count(subcategory//product)" />
						)
					</a>
				</td>
			</tr>
		</table>
	</xsl:template>
</xsl:stylesheet>
