package com.mayank.starwars;

import org.json.JSONException;
import org.json.JSONObject;

public class Character{
    String Name, createdOn, Height, Mass;
    Character(JSONObject jsonObject) throws JSONException {
        Name = jsonObject.getString("name");
        Height = jsonObject.getString("height");
        Mass = jsonObject.getString("mass");
        createdOn = jsonObject.getString("created");
    }
}
