package de.uno.android.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import biz.source_code.base64Coder.Base64Coder;

public class objectSerializer {

	public static String serialize(Serializable o) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(baos);
			oos.writeObject(o);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new String(Base64Coder.encode(baos.toByteArray()));
	}

	public static Object deserialize(String s) {
		byte[] data = Base64Coder.decode(s);
		ObjectInputStream ois;
		Object o = null;
		try {
			ois = new ObjectInputStream(new ByteArrayInputStream(data));
			try {
				o = ois.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			ois.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return o;
	}
}
