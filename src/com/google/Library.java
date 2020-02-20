package com.google;

import java.util.LinkedList;

public class Library {
	private LinkedList<Book> booksInLibrary;
	private int id;
	private int signUpLength;
	private int numBooksInLibrary;
	private int shippedPerDay;

	public Library(LinkedList<Book> booksInLibrary, int id, int signUpLength, int numBooksInLibrary, int shippedPerDay) {
		this.booksInLibrary = booksInLibrary;
		this.signUpLength = signUpLength;
		this.numBooksInLibrary = numBooksInLibrary;
		this.shippedPerDay = shippedPerDay;
		this.id = id;
	}

	public LinkedList<Book> getBooksInLibrary() {
		return booksInLibrary;
	}

	public void setBooksInLibrary(LinkedList<Book> booksInLibrary) {
		this.booksInLibrary = booksInLibrary;
	}

	public int getSignUpLength() {
		return signUpLength;
	}

	public void setSignUpLength(int signUpLength) {
		this.signUpLength = signUpLength;
	}

	public int getNumBooksInLibrary() {
		return numBooksInLibrary;
	}

	public void setNumBooksInLibrary(int numBooksInLibrary) {
		this.numBooksInLibrary = numBooksInLibrary;
	}

	public int getShippedPerDay() {
		return shippedPerDay;
	}

	public void setShippedPerDay(int shippedPerDay) {
		this.shippedPerDay = shippedPerDay;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Library{" +
						"\nbooksInLibrary=" + booksInLibrary +
						"\n, signUpLength=" + signUpLength +
						"\n, numBooksInLibrary=" + numBooksInLibrary +
						"\n, shippedPerDay=" + shippedPerDay +
						"\n, id=" + id +
						"\n}";
	}
}
