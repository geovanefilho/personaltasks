package com.bskyb.internettv.parental_control_exception;

public class Solution {

	private static final int DIAMOND_SIZE = 4;

	public void main(String[] args) {

		printDiamonds(DIAMOND_SIZE, 1);

	}

	public void printDiamonds(int size, int line) {
		if (line <= size) {
			if (line <= (size + 1) / 2) {
				int spacesNeeded = 0;
				if ((size % 2) == 0) {
					spacesNeeded = size / 2;
					size--;
				} else {
					spacesNeeded = (size + 1) / 2;
				}
				
				String diamondSpace = "";
				String diamonds = "";
				for (int spaces = 0; spaces < spacesNeeded; spaces++) {
					diamondSpace += " ";
				}
				for (int diamondsQnt = 0; diamondsQnt < line; diamondsQnt++) {
					diamonds += "o";
				}
				System.out.println(diamondSpace + diamonds);
				printDiamonds(size, line + 1);
			} else if ((line > (size + 1) / 2) && line <= size) {
				int spacesNeeded = 0;
				if (line < size) {
					spacesNeeded = size;
					size--;
				} else {
					spacesNeeded = (size + 1) / 2;
				}
				
				String diamondSpace = "";
				String diamonds = "";
				for (int spaces = 0; spaces < spacesNeeded; spaces++) {
					diamondSpace += " ";
				}
				for (int diamondsQnt = size; diamondsQnt <= line; diamondsQnt++) {
					diamonds += "o";
				}
				System.out.println(diamondSpace + diamonds);
				printDiamonds(size, line+1);
			}
		}
	}
}