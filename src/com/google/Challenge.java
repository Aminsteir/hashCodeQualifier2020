package com.google;

import java.io.*;
import java.util.*;

public class Challenge {
	public static void main(String[] args) throws IOException {
		File file = new File("src/inputs/a_example.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		int[] firstLine = getNumbersFromLine(br);

		final int numBooks = firstLine[0];
		final int numLibraries = firstLine[1];
		final int daysToScan = firstLine[2];

		int[] scoresByBook = getNumbersFromLine(br);

		LinkedList<Library> libraries = new LinkedList<>();

		for (int i = 0; i < numLibraries; i++) {
			int[] libraryDetails = getNumbersFromLine(br);
			int numBooksInLibrary = libraryDetails[0];
			int signUpLength = libraryDetails[1];
			int shippedPerDay = libraryDetails[2];

			int[] booksInLibrary = getNumbersFromLine(br);
			LinkedList<Book> books = new LinkedList<>();
			for (int id: booksInLibrary)
				books.add(new Book(id, scoresByBook[id]));

			libraries.add(new Library(books, signUpLength, numBooksInLibrary, shippedPerDay));
		}

		libraries.sort(Comparator.comparingInt(Library::getSignUpLength).thenComparingInt(Library::getShippedPerDay));

		System.out.println(libraries.toString());
	}

	private static int[] getNumbersFromLine(BufferedReader br) throws IOException {
		String line = br.readLine();
		String[] values = line.split("\\s");
		int [] arr = new int [values.length];
		for(int i=0; i<values.length; i++)
			arr[i] = Integer.parseInt(values[i]);
		return arr;
	}
}
