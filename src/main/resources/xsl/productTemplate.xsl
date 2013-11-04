<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >

	<xsl:output method="html" />
	<xsl:param name="categoryName" />
	<xsl:param name="subcategoryName" />

	<xsl:template match="/">
		<input type="button" value="Back"
			onclick="location='transformcategory.do?categoryName={$categoryName}'" />
		<table id="products">
			<tr>
				<td id="category">
					<xsl:value-of select="$categoryName" />
				</td>
			</tr>
			<tr>
				<td id="subcategory">
					<xsl:value-of select="$subcategoryName" />
				</td>
			</tr>
			<xsl:for-each
				select="//category[@name=$categoryName]//subcategory[@name=$subcategoryName]">
				<xsl:call-template name="subcategory" />
			</xsl:for-each>
		</table>
		<input type="button" value="ADD" onclick="location='transformaddpage.do?categoryName={$categoryName}&amp;subcategoryName={$subcategoryName}'"/>
	</xsl:template>

	<xsl:template name="subcategory">
		<tr>
			<xsl:for-each select="self::node()//product">
				<xsl:call-template name="product" />
			</xsl:for-each>
		</tr>
	</xsl:template>

	<xsl:template name="product">
		<tr>
			<td class="product">
				<xsl:value-of select="@name" />
			</td>
			<td class="product">
				<xsl:value-of select="self::node()//provider" />
			</td>
			<td class="product">
				<xsl:value-of select="self::node()//model" />
			</td>
			<td class="product">
				<xsl:value-of select="self::node()//color" />
			</td>
			<td class="product">
				<xsl:value-of select="self::node()//date" />
			</td>
			<td class="product">
				<xsl:choose>
					<xsl:when test="child::node()=price">
						<xsl:value-of select="self::node()//price" />
					</xsl:when>
					<xsl:when test="child::node()=notInStock">
						Not in stock
					</xsl:when>
				</xsl:choose>
			</td>
		</tr>
	</xsl:template>
</xsl:stylesheet>

