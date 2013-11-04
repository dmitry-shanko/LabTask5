<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:validator="xalan://com.epam.xmlapp.xsl.ProductValidator">

	<xsl:output method="xml" />
	<xsl:param name="categoryName" />
	<xsl:param name="subcategoryName" />

	<xsl:param name="productName" />
	<xsl:param name="provider" />
	<xsl:param name="model" />
	<xsl:param name="color" />
	<xsl:param name="date" />
	<xsl:param name="price" />
	<xsl:param name="notInStock" />


	<xsl:template match="@*|node()" name="saveProduct">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()" />
		</xsl:copy>
	</xsl:template>


	<xsl:template match="//subcategory[@name=$subcategoryName]">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()" />
			<xsl:element name="product">
				<xsl:attribute name="name">
                 <xsl:value-of select="$productName" />
             </xsl:attribute>
				<xsl:element name="provider">
					<xsl:value-of select="$provider" />
				</xsl:element>
				<xsl:element name="model">
					<xsl:value-of select="$model" />
				</xsl:element>
				<xsl:element name="color">
					<xsl:value-of select="$color" />
				</xsl:element>
				<xsl:element name="date">
					<xsl:value-of select="$date" />
				</xsl:element>
				<xsl:choose>
					<xsl:when test="$price = true()">
						<xsl:element name="price">
							<xsl:value-of select="validator:formatPrice($validator, $price)" />
						</xsl:element>
					</xsl:when>
					<xsl:otherwise>
						<xsl:element name="notInStock"></xsl:element>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:element>
		</xsl:copy>
	</xsl:template>

</xsl:stylesheet>