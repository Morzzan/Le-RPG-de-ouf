package fr.utbm.csanchez.utils;

import java.util.Scanner;

public class InterfaceVitef {
	public VectPerso askMove(String choice) {
		String direction;
		VectPerso move = null;
		boolean Magain;
		boolean Aagain;
		Scanner sc = new Scanner(System.in);
		do {
			direction = sc.nextLine();
			Magain = false;
			Aagain = false;
			direction = direction.toUpperCase();
			switch (direction) {
			case "U":
				move = new VectPerso(0, 1);
				break;
			case "D":
				move = new VectPerso(0, -1);
				break;
			case "R":
				move = new VectPerso(1, 0);
				break;
			case "L":
				move = new VectPerso(-1, 0);
				break;
			default:
				Magain = true;
				switch (direction) {
				case "1":
					move = new VectPerso(0, 2);
					break;
				case "2":
					move = new VectPerso(1, 2);
					break;
				case "3":
					move = new VectPerso(2, 2);
					break;
				case "4":
					move = new VectPerso(3, 2);
					break;
				case "5":
					move = new VectPerso(4, 2);
					break;
				case "6":
					move = new VectPerso(5, 2);
					break;
				default:
					Aagain = true;
				}
			}
		} while ((Magain == true && choice == "move") || Aagain == true);
		return move;
	}

}
