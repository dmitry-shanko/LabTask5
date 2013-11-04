package com.epam.xmlapp.xsl.exception;

public class TransformationException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 200100273894987398L;

	public TransformationException(String s, Throwable e)
	{
		super(s, e);
	}
	
	public TransformationException(String s)
	{
		super(s);
	}
	
	public TransformationException(Throwable e)
	{
		super(e);
	}
	
	public TransformationException()
	{
		super();
	}
}
