<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:validator="xalan://com.epam.xmlapp.xsl.ProductValidator" version="1.0">

	<xsl:import href="addProductTemplate.xsl" />
	<xsl:import href="saveProductTemplate.xsl" />

	<xsl:param name="validator" />

	<xsl:template match="/">
		<xsl:variable name="nameValid"
			select="validator:isCorrectName($validator, $productName)" />
		<xsl:variable name="modelValid"
			select="validator:isCorrectModel($validator, $model)" />
		<xsl:variable name="colorValid"
			select="validator:isCorrectColor($validator, $color)" />
		<xsl:variable name="dateValid"
			select="validator:isCorrectDate($validator, $date)" />
		<xsl:variable name="providerValid"
			select="validator:isCorrectProvider($validator, $provider)" />
		<xsl:variable name="priceValid">
			<xsl:choose>
				<xsl:when test="$notInStock != 'on'">
					<xsl:value-of select="validator:isCorrectPrice($validator, $price)" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="true()" />
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="priceFormat">
			<xsl:choose>
				<xsl:when test="$priceValid = 'false'">
					<xsl:value-of select="''" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:choose>
						<xsl:when test="$notInStock != 'on'">
							<xsl:value-of select="validator:formatPrice($validator, $price)" />
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="''" />
						</xsl:otherwise>
					</xsl:choose>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:choose>
			<xsl:when
				test="$nameValid = false or $modelValid = false or $colorValid = false or $dateValid = false or $providerValid = false or $priceValid = 'false'">
				<xsl:value-of select="validator:setProductValid($validator, false())" />
				<xsl:call-template name="addForm">
					<xsl:with-param name="nameValid" select="$nameValid" />
					<xsl:with-param name="modelValid" select="$modelValid" />
					<xsl:with-param name="providerValid" select="$providerValid" />
					<xsl:with-param name="colorValid" select="$colorValid" />
					<xsl:with-param name="dateValid" select="$dateValid" />
					<xsl:with-param name="priceValid" select="$priceValid" />
					<xsl:with-param name="price" select="$priceFormat" />
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="validator:setProductValid($validator, true())" />
				<xsl:call-template name="saveProduct" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>