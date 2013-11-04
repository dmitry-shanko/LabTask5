<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="html" />
	<xsl:param name="categoryName" />
	<xsl:param name="subcategoryName" />

	<xsl:param name="productName" />
	<xsl:param name="provider" />
	<xsl:param name="model" />
	<xsl:param name="date" />
	<xsl:param name="color" />
	<xsl:param name="price" />
	<xsl:param name="notInStock" />

	<xsl:template match="/">
		<xsl:call-template name="addForm" />
	</xsl:template>

	<xsl:template name="addForm">
		<xsl:param name="nameValid" select="true()" />
		<xsl:param name="modelValid" select="true()" />
		<xsl:param name="providerValid" select="true()" />
		<xsl:param name="colorValid" select="true()" />
		<xsl:param name="dateValid" select="true()" />
		<!--  И тут тоже НИ В КОЕМ СЛУЧАЕ НЕ УБИРАТЬ ''!!!! -->
		<xsl:param name="priceValid" select="'true'" />
		<xsl:param name="price" select="''"/>
		<form action="transformaddproduct.do" method="POST">
			<input type="hidden" name="categoryName" value="{$categoryName}" />
			<input type="hidden" name="subcategoryName" value="{$subcategoryName}" />

			<table width="400" border="0">
				<tr>
					<td>Name:</td>
					<td>
						<input type="text" name="productName" value="{$productName}" />
					</td>
					<xsl:if test="$nameValid = false()">
						<td class="error">Name is not valid. It should contains letters and
							numbers.</td>
					</xsl:if>
				</tr>
				<tr>
					<td>Provider:</td>
					<td>
						<input name="provider" type="text" value="{$provider}" />
					</td>
					<xsl:if test="$providerValid = false()">
						<td class="error">Proider is not valid. It should contains letters and
							numbers.</td>
					</xsl:if>
				</tr>
				<tr>
					<td>Model:</td>
					<td>
						<input name="model" type="text" value="{$model}" />
					</td>
					<xsl:if test="$modelValid = false()">
						<td class="error">Model is not valid. It should contains letters and
							numbers.</td>
					</xsl:if>
				</tr>
				<tr>
					<td>Color:</td>
					<td>
						<input name="color" type="text" value="{$color}" />
					</td>
					<xsl:if test="$colorValid = false()">
						<td class="error">Color is not valid. It should contains letters and
							numbers.</td>
					</xsl:if>
				</tr>
				<tr>
					<td>Date:</td>
					<td>
						<input name="date" type="text" value="{$date}" />
					</td>
					<xsl:if test="$dateValid = false()">
						<td class="error">Date is not valid. It should be formatted as
							dd-MM-yyyy.</td>
					</xsl:if>
				</tr>
				<tr>
					<td>Price:</td>

					<td>
						<xsl:choose>
							<xsl:when test="$notInStock">
								<input type="text" name="price" disabled="disabled" id="price" />
							</xsl:when>
							<xsl:otherwise>
								<input type="text" name="price" value="{$price}" id="price" />
							</xsl:otherwise>
						</xsl:choose>
					</td><!-- Здесь возникает БАГ!!! НИ В КОЕМ СЛУЧАЕ НЕ УБИРАТЬ '' у false, 
						т.к. он задается через <xsl:value-of select=.... и задается получается как 
						СТРОКА! -->
					<xsl:if test="$priceValid = 'false'">
						<td class="error">Price is not valid. It should contains only digits.</td>
					</xsl:if>
				</tr>
				<tr>
					<td>Not in stock:</td>
					<td class="error">
						<xsl:choose>
							<xsl:when test="$notInStock">
								<input type="checkbox" name="notInStock" checked="true"
									id="notInStock" onclick="checkStock()" />
							</xsl:when>
							<xsl:otherwise>
								<input type="checkbox" name="notInStock" id="notInStock"
									onclick="checkStock()" />
							</xsl:otherwise>
						</xsl:choose>
					</td>
				</tr>
				<tr>
					<td>
						<input type="button" value="Cancel"
							onclick="location.replace('transformsubcategory.do?categoryName={$categoryName}&amp;subcategoryName={$subcategoryName}')" />
					</td>
					<td>
						<button type="submit">SAVE
						</button>
					</td>
				</tr>
			</table>
		</form>
	</xsl:template>
</xsl:stylesheet>