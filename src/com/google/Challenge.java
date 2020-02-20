package com.google;

import java.io.*;
import java.util.*;

public class Challenge {
	public static void main(String[] args) throws IOException {
		char[] arr = new char[] {'a','b','c','d','e','f'};
		for (char sub: arr)
			runChallenge(sub + ".txt");
	}

	private static void runChallenge(String filename) throws IOException {
		File input = new File(String.format("src/inputs/%s", filename));
		BufferedReader br = new BufferedReader(new FileReader(input));
		FileWriter fileWriter = new FileWriter(String.format("src/outputs/%s", filename), false);
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

		libraries.sort(Comparator.comparingInt(Library::getSignUpLength).thenComparingInt(Library::getShippedPerDay));

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

		List<Integer> idsUsed = new ArrayList<>();
		int currentDay = 0;
		for (int i = 0; i < librariesSignedUp.size(); i++) {
			int currentDayTemp = currentDay;
			int booksSentDay = 0;
			List<Book> booksToSend = new ArrayList<>();
			Library library = librariesSignedUp.get(i);
			int signUpLength = library.getSignUpLength();
			currentDay += signUpLength;
			currentDayTemp += signUpLength;
			for (Book book: library.getBooksInLibrary()) {
				if (!idsUsed.contains(book.getId()) && currentDayTemp < daysToScan) {
					booksSentDay++;
					if (booksSentDay == library.getShippedPerDay()) {
						booksSentDay = 0;
						currentDayTemp++;
					}
					idsUsed.add(book.getId());
					booksToSend.add(book);
				}
			}

			if (booksToSend.size() == 0)
				continue;

			bw.write(String.format("%s %s", library.getId(), booksToSend.size()));
			bw.newLine();

			StringBuilder builder = new StringBuilder();
			for (Book book: booksToSend)
				builder.append(book.getId()).append(" ");
			bw.write(builder.toString().trim());
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
