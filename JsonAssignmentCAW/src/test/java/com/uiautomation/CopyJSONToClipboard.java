package com.uiautomation;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;

public class CopyJSONToClipboard {

	public static void main(String[] args) {
		try {
            String content = new String(Files.readAllBytes(Paths.get("json\\test.json")));
            JSONArray originalArray = new JSONArray(content);

            JSONArray copiedArray = new JSONArray(originalArray.toString());
            System.out.println("Copied JSON Array: " + copiedArray.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
