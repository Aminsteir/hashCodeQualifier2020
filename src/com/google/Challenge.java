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

		List<Integer> idsSigned = new ArrayList<>();
		for (int i = 0; i < libraries.size(); i++) {
			List<Book> books = libraries.get(i).getBooksInLibrary();
			for (int j = 0; j < books.size(); j++) {
				if (idsSigned.contains(books.get(j).getId())) {
					books.remove(j);
					j--;
				} else {
					idsSigned.add(books.get(j).getId());
				}
			}
			if (books.size() == 0) {
				libraries.remove(i);
				i--;
			}
		}

		//TODO: MAKE BETTER COMPARATOR
		libraries.sort(Comparator.comparingInt(Library::getScoreFromBooks).reversed());

		int count = 0;
		int currentDay = 0;
		for (Library value: libraries) {
			int signUpLength = value.getSignUpLength();
			if (currentDay + signUpLength > daysToScan) {
				break;
			} else {
				count++;
				currentDay += signUpLength;
			}
		}

		bw.write(String.valueOf(count));
		bw.newLine();

		// TODO: MAKE THE BOOKSTOSEND BE A SUBLIST OF THE BOOKS SO WE DON'T USE ANOTHER FOR-LOOP
		currentDay = 0;
		for (Library value : libraries) {
			int booksSentDay = 0;
			List<Book> booksToSend = new ArrayList<>();
			int signUpLength = value.getSignUpLength();
			if (currentDay + signUpLength > daysToScan)
				break;
			currentDay += signUpLength;
			int currentDayTemp = currentDay;
			for (Book book : value.getBooksInLibrary()) {
				if (currentDayTemp < daysToScan) {
					booksSentDay++;
					if (booksSentDay == value.getShippedPerDay()) {
						booksSentDay = 0;
						currentDayTemp++;
					}
					booksToSend.add(book);
				}
			}

			bw.write(String.format("%s %s", value.getId(), booksToSend.size()));
			bw.newLine();

			StringBuilder builder = new StringBuilder();
			for (Book book : booksToSend)
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
