package com.corp.myxof.utils;

import java.util.Random;

public class Test {

	public static void main(String[] args) {
		Random random = new Random(System.currentTimeMillis());
		System.out.println(Integer.toHexString(random.nextInt(0xFFFFFF)));

	}

}
