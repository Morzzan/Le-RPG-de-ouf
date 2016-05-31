package fr.utbm.csanchez.utils;

import java.util.Random;
import java.util.Scanner;

public class VectPerso {
	@Override
	public String toString() {
		return "Vector [x=" + x + ", y=" + y + "]";
	}

	private int x;

	public int getX() {
		return x;
	}

	public void randVect(int max, int min) {
		Random rand = new Random();
		int n = rand.nextInt(max - min) + min;
		this.x = n;
		int m = rand.nextInt(max - min) + min;
		this.y = m;
	}

	public void randVectInRoom(VectPerso size, VectPerso bottomLeft) {
		Random rand = new Random();
		int n = rand.nextInt(size.getX() - 1) + bottomLeft.getX();
		this.x = n;
		int m = rand.nextInt(size.getY() - 1) + bottomLeft.getY();
		this.y = m;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	private int y;

	public VectPerso() {
	}

	public VectPerso(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public VectPerso(VectPerso v) {
		this.x = v.x;
		this.y = v.y;
	}

	public VectPerso add(VectPerso v) {
		VectPerso n = new VectPerso();
		n.x = x + v.x;
		n.y = y + v.y;
		return n;
	}

	public VectPerso minus(VectPerso v) {
		VectPerso n = new VectPerso();
		n.x = x - v.x;
		n.y = y - v.y;
		return n;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		VectPerso[] table = new VectPerso[3];

		for (int i = 0; i < 2; i++) {
			table[i] = new VectPerso();
			System.out.println("Entrez la coordonnée en x du vecteur v" + (i + 1));
			table[i].x = sc.nextInt();
			System.out.println("Entrez la coordonnée en y du vecteur v" + (i + 1));
			table[i].y = sc.nextInt();
		}
		table[2] = table[1].add(table[0]);
		for (int i = 0; i < 3; i++) {
			System.out.println("Vector V" + i + 1 + " x = " + table[i].x + " y = " + table[i].y);
		}
	}

	public int dot(VectPerso v) {
		return x * v.x + y * v.y;
	}

	public boolean isPerpendicular(VectPerso v) {
		return (dot(v) == 0);
	}

	public double length() {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}

	public void print() {
		System.out.println("x = " + x + "y = " + y + " length = " + length());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VectPerso other = (VectPerso) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}
