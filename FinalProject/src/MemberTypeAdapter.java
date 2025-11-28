import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class MemberTypeAdapter extends TypeAdapter<Person>{
	private final Gson defaultGson = new Gson();
    
	@Override
	public void write(JsonWriter out, Person value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        defaultGson.toJson(value, value.getClass(), out);   
	}
	
	@Override
	public Person read(JsonReader reader) throws IOException {
        JsonElement tree = JsonParser.parseReader(reader);
        
        if (tree.isJsonNull()) {
            return null;
        }

        JsonObject jsonObject = tree.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        Class<? extends Person> classType;
        
        if (type.equals("Student")) {classType = Student.class;}
        else if (type.equals("External_Member")) {classType = External_Member.class;}
        else {throw new JsonParseException("Unknown member type: " + type);}
        
		return defaultGson.fromJson(jsonObject, classType);
		}
   }
		
	


