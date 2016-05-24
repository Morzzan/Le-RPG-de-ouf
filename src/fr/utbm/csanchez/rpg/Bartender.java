package fr.utbm.csanchez.rpg;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bartender {
	private List<Item> itemList;
	private BufferedReader br;

	public Bartender() throws IOException {
		String currentString = null;
		br = new BufferedReader(new FileReader("Items"));
		itemList = new ArrayList<>();
		while ((currentString = br.readLine()) != null) {

		}

	}

}
