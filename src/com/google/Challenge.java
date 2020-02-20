package com.google;

import java.io.*;
import java.util.*;

public class Challenge {
	public static void main(String[] args) throws IOException {
		File input = new File("src/inputs/f_libraries_of_the_world.txt");
		BufferedReader br = new BufferedReader(new FileReader(input));
		FileWriter fileWriter = new FileWriter("src/outputs/f.txt", false);
		BufferedWriter bw = new BufferedWriter(fileWriter);
		int[] firstLine = getNumbersFromLine(br);

		final int numLibraries = firstLine[1];
		final int daysToScan = firstLine[2];

		int[] scoresByBook = getNumbersFromLine(br);

		List<Library> libraries = new ArrayList<>();

		for (int i = 0; i < numLibraries; i++) {
			int[] libraryDetails = getNumbersFromLine(br);
			int numBooksInLibrary = libraryDetails[0];
			int signUpLength = libraryDetails[1];
			int shippedPerDay = libraryDetails[2];

			int[] booksInLibrary = getNumbersFromLine(br);
			List<Book> books = new ArrayList<>();
			for (int id: booksInLibrary)
				books.add(new Book(id, scoresByBook[id]));
			books.sort(Comparator.comparingInt(Book::getScore).reversed());

			libraries.add(new Library(books, i, signUpLength, numBooksInLibrary, shippedPerDay));
		}

		libraries.sort(Comparator.comparingInt(Library::getSignUpLength).reversed());

		List<Library> librariesSignedUp = new ArrayList<>();
		int time = daysToScan;

		for (Library library: libraries) {
			if (library.getSignUpLength() < time) {
				librariesSignedUp.add(library);
				time -= library.getSignUpLength();
			} else {
				break;
			}
		}

		bw.write(String.valueOf(librariesSignedUp.size()));
		bw.newLine();

		List<Book> booksUsed = new ArrayList<>();
		int currentDay = 0;
		int booksSentDay = 0;

		for (int i = librariesSignedUp.size() - 1; i >= 0; i--) {
			List<Book> booksToSend = new ArrayList<>();
			Library library = librariesSignedUp.get(i);
			currentDay += library.getSignUpLength();
			for (Book book: library.getBooksInLibrary()) {
				if (!booksUsed.contains(book) && currentDay < daysToScan) {
					booksSentDay++;
					if (booksSentDay == library.getShippedPerDay()) {
						booksSentDay = 0;
						currentDay++;
					}
					booksUsed.add(book);
					booksToSend.add(book);
				}
			}
			if (booksToSend.size() == 0)
				continue;

			bw.write(String.format("%s %s", library.getId(), booksToSend.size()));
			bw.newLine();

			for (Book book: booksToSend)
				bw.write(book.getId() + " ");
			bw.newLine();
		}

		bw.flush();
		bw.close();
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
