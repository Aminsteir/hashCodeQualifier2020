package com.google;

import java.util.List;

public class Library {
	private List<Book> booksInLibrary;
	private int signUpLength;
	private int shippingLength;

	public Library(List<Book> booksInLibrary, int signUpLength, int shippingLength) {
		this.booksInLibrary = booksInLibrary;
		this.signUpLength = signUpLength;
		this.shippingLength = shippingLength;
	}

	public List<Book> getBooksInLibrary() {
		return booksInLibrary;
	}

	public void setBooksInLibrary(List<Book> booksInLibrary) {
		this.booksInLibrary = booksInLibrary;
	}

	public int getSignUpLength() {
		return signUpLength;
	}

	public void setSignUpLength(int signUpLength) {
		this.signUpLength = signUpLength;
	}

	public int getShippingLength() {
		return shippingLength;
	}

	public void setShippingLength(int shippingLength) {
		this.shippingLength = shippingLength;
	}
}
