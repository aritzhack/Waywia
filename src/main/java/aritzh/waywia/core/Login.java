/*
 * Copyright (c) 2013 Aritzh (Aritz Lopez)
 *
 * This game is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This game is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this
 * game. If not, see http://www.gnu.org/licenses/.
 */

package aritzh.waywia.core;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

/**
 * Used to check if login info is correct.
 * Use <a href="http://waywia.hostzi.com/login/register.php">this page</a> to register (please, do not abuse)
 *
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Login {

	/**
	 * Checks whether the password corresponds to the given user
	 *
	 * @param username The username
	 * @param password The password
	 * @return {@code true} if correct user-pass pair. If not registered or incorrect, {@code false}
	 */
	public static boolean isCorrect(String username, String password) {
		try {
			URL url = new URL("http://waywia.hostzi.com/login/index.php");

			String params = "user=" + username;

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setConnectTimeout(20000);
			connection.setReadTimeout(20000);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("charset", "utf-8");
			connection.setRequestProperty("Content-Length", "" + Integer.toString(params.getBytes().length));
			connection.setUseCaches(false);

			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.write(params.getBytes("UTF-8"));
			wr.flush();
			wr.close();

			BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String s = r.readLine();
			r.close();
			connection.disconnect();
			return getHash(username, password).equals(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	private static String getHash(String username, String pass) {
		try {
			String toEncode = username.toLowerCase() + ":" + pass;
			MessageDigest dig = MessageDigest.getInstance("SHA-1");
			dig.reset();
			dig.update(toEncode.getBytes("UTF-8"));
			return byteToHex(dig.digest());
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}
}
