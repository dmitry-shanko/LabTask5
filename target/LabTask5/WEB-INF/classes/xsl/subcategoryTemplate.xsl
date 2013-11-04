<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >

	<xsl:output method="html" />
	<xsl:param name="categoryName" />

	<xsl:template match="/">
		<input type="button" value="Back" onclick="location='transform.do'" />
		<table id="products">
			<tr>
				<td id="category">
					<xsl:value-of select="$categoryName" />
				</td>
			</tr>
			<xsl:for-each select="//category[@name=$categoryName]//subcategory">
				<xsl:call-template name="subcategory" />
			</xsl:for-each>
		</table>
	</xsl:template>

	<xsl:template name="subcategory">
		<tr>
			<td id="subcategory">
				<a href="href">
					<xsl:attribute name="href">transformsubcategory.do?categoryName=<xsl:value-of
						select="$categoryName" />&amp;subcategoryName=<xsl:value-of
						select="@name" /></xsl:attribute>
					<xsl:value-of select="@name" />
					(
					<xsl:value-of select="count(self::node()//product)" />
					)
				</a>
			</td>
		</tr>
	</xsl:template>
</xsl:stylesheet>